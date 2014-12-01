package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.io.IIdentifiedPeptideHitWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

abstract public class AbstractBuildSummaryPeptideHitWriter implements
		IIdentifiedPeptideHitWriter<BuildSummaryPeptideHit> {
	public AbstractBuildSummaryPeptideHitWriter() {
	}

	public void write(String filename,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException {
		PrintWriter writer = new PrintWriter(filename);
		try {
			writeHeader(writer, peptideHits);
			writer.println();
			writeItems(writer, peptideHits);
			writeFooter(writer, peptideHits);
			writer.println();
		} finally {
			writer.close();
		}
		
		String enzymeFilename = filename + ".enzyme";
		BuildSummaryPeptideEnzymeFile.write(enzymeFilename, peptideHits);
	}

	protected List<BuildSummaryPeptideHit> getBuildSummaryPeptideHits(
			List<BuildSummaryPeptideHit> peptideHits) {
		List<BuildSummaryPeptideHit> result = new ArrayList<BuildSummaryPeptideHit>();
		for (BuildSummaryPeptideHit hit : peptideHits) {
			result.add((BuildSummaryPeptideHit) hit);
		}
		return result;
	}

	abstract protected void writeHeader(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException;

	abstract protected void writeItems(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException;

	abstract protected void writeFooter(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException;
}
