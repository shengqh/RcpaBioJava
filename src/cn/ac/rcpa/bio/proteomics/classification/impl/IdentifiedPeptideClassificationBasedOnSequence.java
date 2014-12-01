package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.classification.IClassification;

public class IdentifiedPeptideClassificationBasedOnSequence
    implements IClassification<IIdentifiedPeptide> {
  protected IClassification<String> seqClassification;
  public IdentifiedPeptideClassificationBasedOnSequence(IClassification<
      String> seqClassification) {
    this.seqClassification = seqClassification;
  }

  public String getPrinciple() {
    return seqClassification.getPrinciple();
  }

  public String getClassification(IIdentifiedPeptide obj) {
    return seqClassification.getClassification(obj.getSequence());
  }

  public String[] getClassifications() {
    return seqClassification.getClassifications();
  }
}
