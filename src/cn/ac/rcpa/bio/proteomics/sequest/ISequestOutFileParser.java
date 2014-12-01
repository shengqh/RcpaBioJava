package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.IOException;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public interface ISequestOutFileParser {
	BuildSummaryPeptideHit readFirstSequestQueryFromFile(
			String sequestOutFilename, boolean raiseDuplicatedReferenceAbsentException)
			throws IOException;

	BuildSummaryPeptideHit readFirstSequestQueryFromStream(BufferedReader mFile,
			boolean raiseDuplicatedReferenceAbsentException) throws IOException;

	BuildSummaryPeptideHit readFirstSequestQueryFromArray(String[] lines,
			boolean raiseDuplicatedReferenceAbsentException);
}
