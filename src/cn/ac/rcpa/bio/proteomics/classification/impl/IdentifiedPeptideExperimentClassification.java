package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

public class IdentifiedPeptideExperimentClassification
    extends AbstractDiscreteClassification<IIdentifiedPeptide, String> {
  final String principle;
  
  public IdentifiedPeptideExperimentClassification(String principle) {
    this.principle = principle;
  }

  @Override
  protected String doGetClassification(IIdentifiedPeptide obj) {
    return obj.getPeakListInfo().getExperiment();
  }

  public String getPrinciple() {
    return principle;
  }
}
