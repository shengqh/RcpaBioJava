package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.classification.IClassification;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideClassificationFilter implements
    IFilter<IIdentifiedPeptide> {
  private String identity;

  private IClassification<IIdentifiedPeptide> sphc;

  public IdentifiedPeptideClassificationFilter(String identity,
      IClassification<IIdentifiedPeptide> sphc) {
    this.identity = identity;
    this.sphc = sphc;
  }

  public boolean accept(IIdentifiedPeptide pephit) {
    return sphc.getClassification(pephit).equals(identity);
  }

  public String getType() {
    return identity;
  }
}
