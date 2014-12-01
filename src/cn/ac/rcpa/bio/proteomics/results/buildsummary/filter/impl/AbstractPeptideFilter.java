package cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.RcpaObjectUtils;

abstract public class AbstractPeptideFilter implements IFilter<IIdentifiedPeptide>{
  public AbstractPeptideFilter() {
  }

  public boolean accept(IIdentifiedPeptide pephit) {
    RcpaObjectUtils.assertInstanceOf(pephit, BuildSummaryPeptide.class);

    return acceptSequestPeptideHit((BuildSummaryPeptide)pephit);
  }

  protected abstract boolean acceptSequestPeptideHit(BuildSummaryPeptide pephit);
}
