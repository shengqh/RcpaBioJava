package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.io.PrintWriter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.utils.RcpaObjectUtils;

public class BuildSummaryResultProteinOnlyWriter implements
		IIdentifiedResultWriter {
	private static BuildSummaryResultProteinOnlyWriter instance = null;

	public static BuildSummaryResultProteinOnlyWriter getInstance() {
		if (instance == null) {
			instance = new BuildSummaryResultProteinOnlyWriter();
		}
		return instance;
	}

	private BuildSummaryResultProteinOnlyWriter() {
	}

	public void write(String filename, IIdentifiedResult ir) throws IOException {
		RcpaObjectUtils.assertInstanceOf(ir, BuildSummaryResult.class);

		PrintWriter writer = new PrintWriter(filename);
		try {
			write(writer, ir);
		} finally {
			writer.close();
		}
	}

	private void writeGroup(PrintWriter pw, BuildSummaryProteinGroup group)
			throws IOException {
		for (int i = 0; i < group.getProteinCount(); i++) {
			BuildSummaryProtein prohit = (BuildSummaryProtein) group.getProtein(i);
			pw.println("$" + prohit.getGroup() + "-" + (i + 1) + "\t"
					+ prohit.getReference() + "\t" + prohit.getPeptideCount() + "\t"
					+ prohit.getUniquePeptides().length + "\t" + prohit.getCoverage()
					+ "%" + "\t" + prohit.getMW() + "\t" + prohit.getPI());

		}
	}

	/**
	 * write
	 * 
	 * @param writer
	 *          Writer
	 * @param identifiedResult
	 *          IIdentifiedResult
	 */
	public void write(PrintWriter pw, IIdentifiedResult ir) throws IOException {
		RcpaObjectUtils.assertInstanceOf(ir, BuildSummaryResult.class);

		pw.println("\tReference\tPepCount\tUniquePepCount\tCoverPercent\tMW\tPI");
		for (int i = 0; i < ir.getProteinGroupCount(); i++) {
			writeGroup(pw, (BuildSummaryProteinGroup) ir.getProteinGroup(i));
		}

		pw.println();
	}
}
