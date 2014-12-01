package cn.ac.rcpa.bio.proteomics.results.mascot;

import cn.ac.rcpa.filter.IFilter;

public class MascotPeptideScoreFilter implements IFilter<MascotPeptide> {
	private double minScore;

	public MascotPeptideScoreFilter(double minScore) {
		this.minScore = minScore;
	}

	public boolean accept(MascotPeptide e) {
		return e.getScore() >= minScore;
	}

	public String getType() {
		return "MascotPeptideScoreFilter";
	}

}
