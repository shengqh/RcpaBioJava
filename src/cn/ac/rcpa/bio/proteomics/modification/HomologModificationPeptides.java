package cn.ac.rcpa.bio.proteomics.modification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomologModificationPeptides {
  private ArrayList<DiffStateModificationPeptides> pephits;
  private SequenceModificationSitePair sequence;

  public HomologModificationPeptides(DiffStateModificationPeptides peptide) {
    super();
    this.pephits = new ArrayList<DiffStateModificationPeptides>();
    this.pephits.add(peptide);
    this.sequence = new SequenceModificationSitePair(peptide.getSequence());
  }

  public void add(DiffStateModificationPeptides peptide){
    pephits.add(peptide);
    sequence.mergeWithSurePeptide(peptide.getSequence());
  }

  public SequenceModificationSitePair getSequence() {
    return sequence;
  }

  public List<DiffStateModificationPeptides> getModificationPeptidesList() {
    return Collections.unmodifiableList(pephits);
  }

  public int getPeptideHitCount(){
    int result = 0;

    for(DiffStateModificationPeptides pep:pephits){
      result += pep.getPeptideHitCount();
    }

    return result;
  }
}
