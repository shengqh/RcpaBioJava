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
	 * 因为IdentifiedPeptide列表中，会有多个scan鉴定到同一个肽段。 该函数是只保留同一个肽段对应的一个scan，以便统计。这里，去除
	 * 一个scan对应的多个肽段不能用getUnduplicatedPeptides方式。
	 * 因为可能会有scan_A对应肽段X,Y，scan_B只对应Y，这里，X和Y往往
	 * 只是Q和K的差别。所谓scan_B只对应Y，事实上X是排名第二位，但计算
	 * DeltaCn时，考虑的是第一和第三的DeltaCn，X被忽略。对于unique
	 * 肽段，应该只保留Y。若按照getUnduplicatedPeptides，则scan_A 对应X，scan_B对应Y，就是两个unique
	 * peptide了。
	 * 
	 * 这里，我们采用方式是，先把所有scan对应的peptide聚在一起。然后根据
	 * 肽段序列对scan进行map，优先保留一个scan对应多个peptide的结果。这
	 * 样，序列X和Y都会对应到scan_A上，而不是scan_B上，即使scan_B出现在 scan_A前面。最后，每个scan只保留一个peptide。
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
