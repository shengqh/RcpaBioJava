package cn.ac.rcpa.bio.sequest.impl;

import java.io.IOException;
import java.io.PrintWriter;

import cn.ac.rcpa.bio.sequest.EnzymeLimitType;
import cn.ac.rcpa.bio.sequest.SequestEnzyme;
import cn.ac.rcpa.bio.sequest.SequestParams;

public class SequestParams32Writer extends AbstractSequestParamsWriter {
	@Override
	protected void writeEnzymeInfoLine(PrintWriter bw, SequestParams sp)
			throws IOException {
		SequestEnzyme enzyme = sp.getEnzyme(sp.getEnzyme_number());

		StringBuilder sb = new StringBuilder();
		sb.append("enzyme_info = ");
		sb.append(enzyme.getEnzymeName());
		if (sp.getEnzymeLimitType() == EnzymeLimitType.Full) {
			sb.append(" 1");
		} else if (sp.getEnzymeLimitType() == EnzymeLimitType.Partially) {
			sb.append(" 2");
		} else if (sp.getEnzymeLimitType() == EnzymeLimitType.NTerminal) {
			sb.append(" 3");
		} else {
			sb.append(" 4 ");
		}
		sb.append(" " + enzyme.getOffset());
		sb.append(" " + enzyme.getCleaveAt() + " " + enzyme.getUncleaveAt());
		bw.println(sb.toString());
	}

	@Override
	protected void writeEnzymeDefinition(PrintWriter bw, SequestParams sp)
			throws IOException {
	}

}
