package cn.ac.rcpa.bio.proteomics.classification.impl;

public class IdentifiedPeptideLengthClassification
    extends IdentifiedPeptideClassificationBasedOnSequence {
  private static IdentifiedPeptideLengthClassification instance;

  public static IdentifiedPeptideLengthClassification getInstance() {
    if (instance == null) {
      instance = new IdentifiedPeptideLengthClassification();
    }
    return instance;
  }

  private IdentifiedPeptideLengthClassification() {
    super(PeptideSequenceLengthClassification.getInstance());
  }
}
