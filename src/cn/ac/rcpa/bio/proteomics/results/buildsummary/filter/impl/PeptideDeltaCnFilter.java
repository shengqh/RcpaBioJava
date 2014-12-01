package cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;

public class PeptideDeltaCnFilter
    extends AbstractPeptideFilter {
  private double deltacn;

  public PeptideDeltaCnFilter(double deltacn) {
    this.deltacn = deltacn;
  }

  @Override
  protected boolean acceptSequestPeptideHit(BuildSummaryPeptide pephit) {
    return pephit.getDeltacn() >= deltacn;
  }

  public String getType() {
    return "Deltacn";
  }
}
