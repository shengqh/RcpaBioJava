package cn.ac.rcpa.bio.proteomics.results.mascot;

import cn.ac.rcpa.filter.IFilter;

public class MascotPeptideRankFilter implements IFilter<MascotPeptide> {
	private int maxRank;

	public MascotPeptideRankFilter(int maxRank) {
		this.maxRank = maxRank;
	}

	public boolean accept(MascotPeptide e) {
		return e.getRank() <= maxRank;
	}

	public String getType() {
		return "MascotPeptideRankFilter";
	}

}
