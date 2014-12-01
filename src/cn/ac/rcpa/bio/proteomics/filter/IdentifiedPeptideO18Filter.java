package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideO18Filter implements
    IFilter<IIdentifiedPeptide>{
  public IdentifiedPeptideO18Filter() {
  }

  public boolean accept(IIdentifiedPeptide pephit) {
    return O18PeptideUtils.isO18Peptide(PeptideUtils.getMatchPeptideSequence(pephit.getSequence()));
  }

  public String getType() {
    return "O18";
  }
}
