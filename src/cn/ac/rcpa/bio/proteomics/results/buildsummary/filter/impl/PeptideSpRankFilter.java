package cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;

public class PeptideSpRankFilter
     extends AbstractPeptideFilter {
  private double spRank;

  public PeptideSpRankFilter(double spRank) {
    this.spRank = spRank;
  }

  @Override
  protected boolean acceptSequestPeptideHit(BuildSummaryPeptide pephit) {
    return pephit.getSpRank() <= spRank;
  }

  public String getType() {
    return "SpRank";
  }
}
