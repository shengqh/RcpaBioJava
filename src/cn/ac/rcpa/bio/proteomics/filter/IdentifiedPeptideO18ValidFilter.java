package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideO18ValidFilter implements
    IFilter<IIdentifiedPeptide>{
  public IdentifiedPeptideO18ValidFilter() {
  }

  public boolean accept(IIdentifiedPeptide pephit) {
    return O18PeptideUtils.isO18ValidPeptide(PeptideUtils.getMatchPeptideSequence(pephit.getSequence()));
  }

  public String getType() {
    return "O18Valid";
  }
}
