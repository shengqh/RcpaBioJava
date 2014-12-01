package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.IOException;

public class DtasFileIterator extends AbstractMergedFileIterator {
	public DtasFileIterator(BufferedReader br) throws IOException {
		super(br, false);
	}

	@Override
	protected boolean isNextFileStart(String line) {
		return line.endsWith(".dta");
	}

	@Override
	public String getFormatName() {
		return "MergedDtaFile";
	}
}
