package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class PeptideSequenceAminoacidClassification extends AbstractAminoacidClassification<String>{
  public PeptideSequenceAminoacidClassification(char aminoacid) {
    super(aminoacid);
  }

  @Override
  protected Integer doGetClassification(String peptideSequence) {
    String seq = PeptideUtils.getPurePeptideSequence(peptideSequence);

    int result = 0;
    for(int i = 0;i < seq.length();i++){
      if (seq.charAt(i) == aminoacid){
        result++;
      }
    }
    return result;
  }
}
