package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.IOException;

public class OutsFileIterator extends AbstractMergedFileIterator {
	public OutsFileIterator(BufferedReader br) throws IOException {
		super(br, true);
	}

	@Override
	protected boolean isNextFileStart(String line) {
		return line.endsWith(".out");
	}

	@Override
	public String getFormatName() {
		return "MergedOutFile";
	}
}
