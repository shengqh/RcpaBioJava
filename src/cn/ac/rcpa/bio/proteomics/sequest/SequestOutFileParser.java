package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.FollowCandidatePeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.utils.INextLine;
import cn.ac.rcpa.utils.NextLineFromStream;
import cn.ac.rcpa.utils.NextLineFromStrings;

public class SequestOutFileParser implements ISequestOutFileParser {
	private double maxDeltaCn;

	public SequestOutFileParser(double maxDeltaCn) {
		this.maxDeltaCn = maxDeltaCn;
	}

	private static Pattern pattern = Pattern
			.compile("^\\s+\\d+\\.\\s+(\\d+)\\s*/.+?\\s+(?:\\d+\\s+){0,1}\\d+\\.\\d+\\s+(\\d\\.\\d+)\\s+(\\d\\.\\d+)\\s+.+\\s+(\\S+)\\s*$");

	public BuildSummaryPeptideHit readFirstSequestQueryFromFile(
			String sequestOutFilename, boolean raiseDuplicatedReferenceAbsentException)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(sequestOutFilename));
		try {
			return readFirstSequestQueryFromStream(br,
					raiseDuplicatedReferenceAbsentException);
		} finally {
			br.close();
		}
	}

	public BuildSummaryPeptideHit readFirstSequestQueryFromArray(String[] lines,
			boolean raiseDuplicatedReferenceAbsentException) {
		try {
			return readFirstSequestQueryFromLines(new NextLineFromStrings(lines),
					raiseDuplicatedReferenceAbsentException);
		} catch (IOException ex) {
			return null;
		}
	}

	public BuildSummaryPeptideHit readFirstSequestQueryFromStream(
			BufferedReader mFile, boolean raiseDuplicatedReferenceAbsentException)
			throws IOException {
		return readFirstSequestQueryFromLines(new NextLineFromStream(mFile),
				raiseDuplicatedReferenceAbsentException);
	}

	public BuildSummaryPeptideHit readFirstSequestQueryFromLines(INextLine lines,
			boolean raiseDuplicatedReferenceAbsentException) throws IOException {
		BuildSummaryPeptideHit result = new BuildSummaryPeptideHit();

		String line;
		String lastPureSequence = "";
		boolean bFirst = true;
		while ((line = lines.nextLine()) != null) {
			BuildSummaryPeptide next = parsePeptide(line);
			if (next != null) {
				String pureSequence = PeptideUtils.getPurePeptideSequence(next
						.getSequence());
				if (bFirst) {
					result.addPeptide(next);
					lastPureSequence = pureSequence;
					bFirst = false;
					continue;
				}

				result.getPeptide(0).setDeltacn(next.getDeltacn());
				if (pureSequence.equals(lastPureSequence)) {
					if (next.getDeltacn() < maxDeltaCn) {
						result.getFollowCandidates().add(
								new FollowCandidatePeptide(next.getSequence(), next.getXcorr(),
										next.getDeltacn()));
					} else {
						break;
					}
				}
			}
		}

		return result;
	}

	public static BuildSummaryPeptide parsePeptide(String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			double deltacn = Double.parseDouble(matcher.group(2));
			double xcorr = Double.parseDouble(matcher.group(3));
			String sequence = matcher.group(4);
			BuildSummaryPeptide result = new BuildSummaryPeptide();
			result.setSequence(sequence);
			result.setXcorr(xcorr);
			result.setDeltacn(deltacn);
			return result;
		}

		return null;
	}

}
