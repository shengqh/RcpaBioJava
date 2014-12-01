package cn.ac.rcpa.bio.proteomics.io.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultWriter;
import cn.ac.rcpa.utils.RcpaStringUtils;

public class FastaResultWriter implements IIdentifiedResultWriter {
	private static FastaResultWriter instance = null;

	private FastaResultWriter() {
	}

	public static FastaResultWriter getInstance() {
		if (instance == null) {
			instance = new FastaResultWriter();
		}
		return instance;
	}

	public void write(String filename, IIdentifiedResult ir) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(filename));
		write(pw, ir);
		pw.close();
	}

	public void write(PrintWriter writer, IIdentifiedResult ir)
			throws IOException {
		for (int i = 0; i < ir.getProteinGroupCount(); i++) {
			for (int j = 0; j < ir.getProteinGroup(i).getProteinCount(); j++) {
				writer
						.println(">" + ir.getProteinGroup(i).getProtein(j).getReference());
				if (ir.getProteinGroup(i).getProtein(j).getSequence() == null) {
					throw new IllegalArgumentException("Missing protein sequence of "
							+ ir.getProteinGroup(i).getProtein(j).getProteinName());
				}
				writer.println(RcpaStringUtils.warpString(ir.getProteinGroup(i)
						.getProtein(j).getSequence(), 70));
			}
		}
	}
}
