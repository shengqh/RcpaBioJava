package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import org.biojava.bio.proteomics.Protease;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedPeptideHit;

public class BuildSummaryPeptideHit extends
    AbstractIdentifiedPeptideHit<BuildSummaryPeptide> implements
    Comparable<BuildSummaryPeptideHit> {
	private Protease protease; 

  public BuildSummaryPeptideHit() {
  }

  public int compareTo(BuildSummaryPeptideHit arg0) {
    return super.compareTo(arg0);
  }

	public Protease getProtease() {
		return protease;
	}

	public void setProtease(Protease protease) {
		this.protease = protease;
	}
}
