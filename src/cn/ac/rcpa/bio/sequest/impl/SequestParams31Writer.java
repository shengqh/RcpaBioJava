package cn.ac.rcpa.bio.sequest.impl;

import java.io.IOException;
import java.io.PrintWriter;

import cn.ac.rcpa.bio.sequest.SequestEnzyme;
import cn.ac.rcpa.bio.sequest.SequestParams;

public class SequestParams31Writer extends AbstractSequestParamsWriter {
	@Override
	protected void writeEnzymeInfoLine(PrintWriter bw, SequestParams sp)
			throws IOException {
		bw.println("enzyme_number = " + sp.getEnzyme_number());
	}

	@Override
	protected void writeEnzymeDefinition(PrintWriter bw, SequestParams sp)
			throws IOException {
		bw.println("[SEQUEST_ENZYME_INFO]");
		for (int i = 0; i < sp.getEnzymeCount(); i++) {
			SequestEnzyme enzyme = sp.getEnzyme(i);
			bw.println(i + ".\t" + enzyme.getEnzymeName() + "\t\t\t\t"
					+ enzyme.getOffset() + "\t" + enzyme.getCleaveAt() + "\t\t"
					+ enzyme.getUncleaveAt());
		}
	}
}
