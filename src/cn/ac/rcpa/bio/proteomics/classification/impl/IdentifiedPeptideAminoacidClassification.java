package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;

public class IdentifiedPeptideAminoacidClassification extends AbstractAminoacidClassification<IIdentifiedPeptideHit>{
  private PeptideSequenceAminoacidClassification seqClassification;
  public IdentifiedPeptideAminoacidClassification(char aminoacid) {
    super(aminoacid);
    seqClassification = new PeptideSequenceAminoacidClassification(aminoacid);
  }

  @Override
  protected Integer doGetClassification(IIdentifiedPeptideHit obj) {
    int result = 0;
    for(int i = 0;i < obj.getPeptideCount();i++){
      int curCount = seqClassification.doGetClassification (obj.getPeptide(i).getSequence());
      if (curCount > result){
        result = curCount;
      }
    }
    return result;
  }
}
