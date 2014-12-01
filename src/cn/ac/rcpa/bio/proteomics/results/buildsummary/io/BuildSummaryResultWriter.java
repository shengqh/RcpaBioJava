package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultWriter;
import cn.ac.rcpa.bio.proteomics.io.impl.FastaResultWriter;
import cn.ac.rcpa.bio.proteomics.io.impl.IdentifiedResultStatisticWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;

public class BuildSummaryResultWriter implements
		IIdentifiedResultWriter<BuildSummaryResult> {
	private static BuildSummaryResultWriter instance = null;

	public static BuildSummaryResultWriter getInstance() {
		if (instance == null) {
			instance = new BuildSummaryResultWriter();
		}
		return instance;
	}

	private BuildSummaryResultWriter() {
	}

	private BuildSummaryPeptideHitWriter pephitWriter;

	public void write(String filename, BuildSummaryResult ir) throws IOException {
		PrintWriter writer = new PrintWriter(filename);
		try {
			write(writer, ir);
		} finally {
			writer.close();
		}

		if (ir.getProteinGroupCount() > 0
				&& ir.getProteinGroup(0).getProteinCount() > 0
				&& ir.getProteinGroup(0).getProtein(0).getSequence() != null) {
			writeFastaFile(filename, (BuildSummaryResult) ir);
		}
	}

	private void writeFastaFile(String filename, BuildSummaryResult sequestResult)
			throws IOException {
		FastaResultWriter.getInstance().write(filename + ".fasta", sequestResult);
	}

	private void writeStatistic(PrintWriter pw, BuildSummaryResult sequestResult)
			throws IOException {
		IdentifiedResultStatisticWriter.getInstance().write(pw, sequestResult);
	}

	private void writeGroup(PrintWriter pw, BuildSummaryProteinGroup group)
			throws IOException {
		for (int i = 0; i < group.getProteinCount(); i++) {
			BuildSummaryProtein prohit = group.getProtein(i);
			pw.println("$" + prohit.getGroup() + "-" + (i + 1) + "\t"
					+ prohit.getReference() + "\t" + prohit.getPeptideCount() + "\t"
					+ prohit.getUniquePeptides().length + "\t" + prohit.getCoverage()
					+ "%" + "\t" + prohit.getMW() + "\t" + prohit.getPI());

		}

		pephitWriter.writeItems(pw, group.getPeptideHits());
	}

	/**
	 * write
	 * 
	 * @param writer
	 *          Writer
	 * @param identifiedResult
	 *          IIdentifiedResult
	 */
	public void write(PrintWriter pw, BuildSummaryResult ir) throws IOException {
		pw.println("\tReference\tPepCount\tUniquePepCount\tCoverPercent\tMW\tPI");

		List<BuildSummaryPeptideHit> peptideHits = ir.getPeptideHits();

		pephitWriter = new BuildSummaryPeptideHitWriter();
		pephitWriter.writeHeader(pw, peptideHits);
		pw.println();
		
		for (int i = 0; i < ir.getProteinGroupCount(); i++) {
			writeGroup(pw, ir.getProteinGroup(i));
		}

		pw.println();
		writeStatistic(pw, ir);
	}
}
