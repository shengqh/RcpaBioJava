package cn.ac.rcpa.bio.proteomics.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.comparison.IdentifiedPeptideComparator;
import cn.ac.rcpa.bio.utils.IsoelectricPointCalculator;
import cn.ac.rcpa.filter.IFilter;

public class PeptideUtils {
	private PeptideUtils() {
	}

	private static Pattern pureSeqPattern;

	private static Pattern getPureSeqPattern() {
		if (pureSeqPattern == null) {
			pureSeqPattern = Pattern.compile("\\S\\.(\\S+)\\.\\S*");
		}
		return pureSeqPattern;
	}

	public static String removeModification(String sequence) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < sequence.length(); i++) {
			char c = sequence.charAt(i);
			if ('-' == c || '.' == c || (c >= 'A' && c <= 'Z')) {
				result.append(c);
			}
		}

		return result.toString();
	}

	public static String getMatchPeptideSequence(String sequence) {
		Matcher matcher = getPureSeqPattern().matcher(sequence);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return sequence;
		}
	}

	public static String getPurePeptideSequence(String sequence) {
		String tmpPureSequence = getMatchPeptideSequence(sequence);

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tmpPureSequence.length(); i++) {
			if (tmpPureSequence.charAt(i) >= 'A' && tmpPureSequence.charAt(i) <= 'Z') {
				result.append(tmpPureSequence.charAt(i));
			}
		}

		return result.toString();
	}

	/**
	 * Get peptide sequence whose modification site corresponding to assigned
	 * modifiedAminoacids will be set as '*' and other modification site will be
	 * removed
	 * 
	 * @param sequence
	 *          String
	 * @param modifiedAminoacids
	 *          String
	 * @return String
	 */
	public static String getSpecialModifiedPeptideSequence(String sequence,
			String modifiedAminoacids) {
		final String matchSequence = getMatchPeptideSequence(sequence);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < matchSequence.length(); i++) {
			if (Character.isLetter(matchSequence.charAt(i))) {
				sb.append(matchSequence.charAt(i));
			} else if (sb.length() > 0
					&& modifiedAminoacids.indexOf(sb.charAt(sb.length() - 1)) != -1) {
				sb.append('*');
			}
		}
		return sb.toString();
	}

	public static double getPeptidePI(String sequence) {
		String pureSeq = getPurePeptideSequence(sequence);
		return IsoelectricPointCalculator.getPI(pureSeq);
	}

	public static boolean isModified(String sequence) {
		final String matchSequence = getMatchPeptideSequence(sequence);
		for (int j = 0; j < matchSequence.length(); j++) {
			final char aa = matchSequence.charAt(j);
			if ((aa >= 'A' && aa <= 'Z') || (aa >= 'a' && aa <= 'z')) {
				continue;
			}

			return true;
		}

		return false;
	}

	public static <E extends IIdentifiedPeptide, F extends IIdentifiedPeptideHit<E>> Map<String, List<E>> getScanPeptideMap(
			List<F> peptides) {
		Map<String, List<E>> result = new HashMap<String, List<E>>();

		for (F peptide : peptides) {
			String experimentFilename = peptide.getPeakListInfo().getExperiment()
					+ "." + peptide.getPeakListInfo().getFirstScan() + "."
					+ peptide.getPeakListInfo().getLastScan() + "."
					+ peptide.getPeakListInfo().getCharge();
			result.put(experimentFilename, new ArrayList<E>(peptide.getPeptides()));
		}

		return result;
	}

	/**
	 * ��ΪIdentifiedPeptide�б��У����ж��scan������ͬһ���ĶΡ� �ú�����ֻ����ͬһ���Ķζ�Ӧ��һ��scan���Ա�ͳ�ơ����ȥ��
	 * һ��scan��Ӧ�Ķ���Ķβ�����getUnduplicatedPeptides��ʽ��
	 * ��Ϊ���ܻ���scan_A��Ӧ�Ķ�X,Y��scan_Bֻ��ӦY�����X��Y����
	 * ֻ��Q��K�Ĳ����νscan_Bֻ��ӦY����ʵ��X�������ڶ�λ��������
	 * DeltaCnʱ�����ǵ��ǵ�һ�͵�����DeltaCn��X�����ԡ�����unique
	 * �ĶΣ�Ӧ��ֻ����Y��������getUnduplicatedPeptides����scan_A ��ӦX��scan_B��ӦY����������unique
	 * peptide�ˡ�
	 * 
	 * ������ǲ��÷�ʽ�ǣ��Ȱ�����scan��Ӧ��peptide����һ��Ȼ�����
	 * �Ķ����ж�scan����map�����ȱ���һ��scan��Ӧ���peptide�Ľ������
	 * ��������X��Y�����Ӧ��scan_A�ϣ�������scan_B�ϣ���ʹscan_B������ scan_Aǰ�档���ÿ��scanֻ����һ��peptide��
	 * 
	 * @param peptides
	 *          List
	 * @return List
	 */
	public static <E extends IIdentifiedPeptide, F extends IIdentifiedPeptideHit<E>> List<E> getUniquePeptides(
			List<F> peptides) {
		final Map<String, List<E>> map = getScanPeptideMap(peptides);
		final Map<String, List<E>> sequencePeptideMap = new HashMap<String, List<E>>();
		for (List<E> pepList : map.values()) {
			if (pepList.size() == 1
					&& sequencePeptideMap.containsKey(pepList.get(0).getSequence())) {
				continue;
			}

			for (E peptide : pepList) {
				sequencePeptideMap.put(peptide.getSequence(), pepList);
			}
		}

		ArrayList<E> result = new ArrayList<E>();
		final Set<List<E>> pepSet = new HashSet<List<E>>(sequencePeptideMap
				.values());
		for (List<E> pepList : pepSet) {
			result.add(pepList.get(0));
		}

		Collections.sort(result, new IdentifiedPeptideComparator<E>());
		return result;
	}

	public static <E extends IIdentifiedPeptide, F extends IIdentifiedPeptideHit<E>> List<E> getUnduplicatedPeptides(
			List<F> pephits) {
		ArrayList<E> result = new ArrayList<E>();
		for (F pephit : pephits) {
			result.add(pephit.getPeptide(0));
		}
		return result;
	}

	public static <E extends IIdentifiedPeptideHit> List<E> getSubset(
			List<E> source, IFilter<IIdentifiedPeptideHit> filter) {
		List<E> result = new ArrayList<E>();
		for (E hit : source) {
			if (filter.accept(hit)) {
				result.add(hit);
			}
		}
		return result;
	}

	public static <E extends IIdentifiedPeptideHit> Set<String> getFilenames(
			List<E> pephits) {
		Set<String> result = new HashSet<String>();

		for (E peptide : pephits) {
			result.add(peptide.getPeptide(0).getPeakListInfo().getLongFilename());
		}

		return result;
	}

	public static boolean isModified(String matchPeptide, int index) {
		if (index < 0 || index >= matchPeptide.length()) {
			throw new IllegalArgumentException("Index " + index
					+ " out of bound, peptide = " + matchPeptide);
		}

		return Character.isLetter(matchPeptide.charAt(index))
				&& (index < matchPeptide.length() - 1)
				&& !Character.isLetter(matchPeptide.charAt(index + 1));
	}

	public static void merge(String[] oldFilenames, String resultFilename)
			throws Exception {
		PrintWriter pw = new PrintWriter(resultFilename);
		try {
			boolean bFirst = true;
			for (String filename : oldFilenames) {
				BufferedReader br = new BufferedReader(new FileReader(filename));

				String line = br.readLine();
				if (bFirst) {
					pw.println(line);
					bFirst = false;
				}

				while ((line = br.readLine()) != null) {
					if (line.length() == 0) {
						break;
					}

					pw.println(line);
				}
			}
		} finally {
			pw.close();
		}
	}

}
