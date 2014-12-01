package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.FollowCandidatePeptideList;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedPeptideHitReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.sequest.SequestParseException;
import cn.ac.rcpa.utils.RegexValueType;

public class BuildSummaryPeptideHitReader implements
		IIdentifiedPeptideHitReader<BuildSummaryPeptideHit> {
	public BuildSummaryPeptideHitReader() {
	}

	private final static String peptidePatternStr = 
			"\\t*\"{0,1}(.+?)\"{0,1}\\t" + // file
			// and
			// scan
			"(.+?)" + "\\t" + // sequence
			RegexValueType.GET_DOUBLE + "\\t" + // experiment MH
			RegexValueType.GET_DOUBLE + "\\t" + // diff between experiment and
			// theoretical MH
			RegexValueType.GET_INT + "\\t" + // charge
			RegexValueType.GET_INT + "\\t" + // rank
			RegexValueType.GET_DOUBLE + "\\t" + // XCorr
			RegexValueType.GET_DOUBLE + "\\t" + // DeltaCn
			RegexValueType.GET_DOUBLE + "\\t" + // sp
			RegexValueType.GET_INT + "\\t" + // spRank
			RegexValueType.GET_UNBLANK + "\\t" + // ions
			"(.+?)(?=$|\t)(.*)"; // references

	private static Pattern peptidePattern = null;

	public static Pattern getPeptidePattern() {
		if (peptidePattern == null) {
			peptidePattern = Pattern.compile(peptidePatternStr);
		}
		return peptidePattern;
	}

	public List<BuildSummaryPeptideHit> read(String filename) throws Exception {
		List<BuildSummaryPeptideHit> result = new ArrayList<BuildSummaryPeptideHit>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		System.out.print("\nReading " + filename + " ... ");
		// skip the first line
		br.readLine();

		String line;
		while ((line = br.readLine()) != null) {
			if (line.trim().length() == 0) {
				break;
			}

			BuildSummaryPeptideHit phit = parse(line);
			result.add(phit);
		}

		String enzymeFile = filename + ".enzyme";
		if (new File(enzymeFile).exists()) {
			BuildSummaryPeptideEnzymeFile.fill(enzymeFile, result);
		}

		System.out.println("Finished, total " + result.size() + " PeptideHit");

		return result;
	}

	/**
	 * parse
	 * 
	 * @param line
	 *          String
	 * @return SequestPeptideHit[]
	 */
	public static BuildSummaryPeptideHit parse(String line)
			throws SequestParseException {
		BuildSummaryPeptideHit result = new BuildSummaryPeptideHit();

		Matcher matcher = getPeptidePattern().matcher(line);
		if (!matcher.find()) {
			throw new SequestParseException(line
					+ " is not a valid peptide information line !");
		}

		final String[] peptideSequences = matcher.group(2).split("\\s!\\s");
		final String[] proteinReferences = matcher.group(12).split("\\s!\\s");

		if (peptideSequences.length != proteinReferences.length) {
			throw new RuntimeException(line
					+ " is not a valid peptide information line\npeptide "
					+ Arrays.asList(peptideSequences) + "\nprotein "
					+ Arrays.asList(proteinReferences));
		}

		for (int i = 0; i < peptideSequences.length; i++) {
			BuildSummaryPeptide pephit = new BuildSummaryPeptide();
			pephit.setFilename(matcher.group(1));
			pephit.getPeakListInfo().setExtension("out");
			pephit.setSequence(peptideSequences[i]);
			pephit.setTheoreticalSingleChargeMass(Double
					.parseDouble(matcher.group(3)));
			pephit.setExperimentalSingleChargeMass(pephit
					.getTheoreticalSingleChargeMass()
					- Double.parseDouble(matcher.group(4)));
			pephit.setCharge(Integer.parseInt(matcher.group(5)));
			pephit.setRank(Integer.parseInt(matcher.group(6)));
			pephit.setXcorr(Double.parseDouble(matcher.group(7)));
			pephit.setDeltacn(Double.parseDouble(matcher.group(8)));
			pephit.setSp(Double.parseDouble(matcher.group(9)));
			pephit.setSpRank(Integer.parseInt(matcher.group(10)));

			final String[] ioncounts = matcher.group(11).split("\\|");
			pephit.setMatchCount(Integer.parseInt(ioncounts[0]));
			pephit.setTheoreticalCount(Integer.parseInt(ioncounts[1]));

			final String[] proteins = proteinReferences[i].split("/");
			for (int j = 0; j < proteins.length; j++) {
				pephit.addProteinName(proteins[j]);
			}
			result.addPeptide(pephit);
		}

		String other = matcher.group(13).trim();
		if (other.length() > 0) {
			FollowCandidatePeptideList candidates = FollowCandidatePeptideList
					.valueOf(other);
			for (int i = 0; i < result.getPeptideCount(); i++) {
				result.getPeptide(i).setFollowCandidates(candidates);
			}
		}

		return result;
	}
}
