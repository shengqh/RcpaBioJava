package cn.ac.rcpa.bio.proteomics.results.mascot;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedProtein;

public class MascotProtein extends AbstractIdentifiedProtein<MascotPeptide> {
	private int score;

	public int getScore() {
		if (0 == score && this.peptides.size() > 0) {
			for (MascotPeptide mpep : this.peptides) {
				score += mpep.getScore();
			}
		}
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
