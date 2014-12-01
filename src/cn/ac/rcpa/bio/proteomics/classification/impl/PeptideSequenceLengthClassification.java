package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class PeptideSequenceLengthClassification
    extends AbstractDiscreteClassification<String, Integer> {
  private static PeptideSequenceLengthClassification instance;

  public static PeptideSequenceLengthClassification getInstance() {
    if (instance == null) {
      instance = new PeptideSequenceLengthClassification();
    }
    return instance;
  }

  private PeptideSequenceLengthClassification() {
  }

  @Override
  protected Integer doGetClassification(String obj) {
    return PeptideUtils.getPurePeptideSequence(obj).length();
  }

  public String getPrinciple() {
    return "PeptideLength";
  }
}
