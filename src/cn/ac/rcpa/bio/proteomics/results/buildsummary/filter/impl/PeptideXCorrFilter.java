package cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;

public class PeptideXCorrFilter
    extends AbstractPeptideFilter {
  private double[] xcorr;

  public PeptideXCorrFilter(double[] xcorr) {
    this.xcorr = xcorr;
  }

  @Override
  protected boolean acceptSequestPeptideHit(BuildSummaryPeptide pephit) {
    if (pephit.getCharge() == 0 || pephit.getCharge() > xcorr.length) {
      throw new IllegalArgumentException("Parameter pephit invalid : charge = " +
                                         pephit.getCharge() +
                                         " is not in [1.." + xcorr.length + "]");
    }
    return pephit.getXcorr() >= xcorr[pephit.getCharge() - 1];
  }

  public String getType() {
    return "XCorr";
  }
}
