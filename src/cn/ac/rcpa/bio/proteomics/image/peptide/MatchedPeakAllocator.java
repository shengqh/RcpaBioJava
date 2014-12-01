package cn.ac.rcpa.bio.proteomics.image.peptide;

import cn.ac.rcpa.bio.utils.IAllocator;

public class MatchedPeakAllocator implements IAllocator<MatchedPeak>{

	@Override
	public MatchedPeak allocate() {
		return new MatchedPeak();
	}

}
