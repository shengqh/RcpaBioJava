package cn.ac.rcpa.bio.proteomics.modification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class DiffStateModificationPeptides {
  private ArrayList<SameModificationPeptides> pephits;

  private SequenceModificationSitePair sequence;

  public DiffStateModificationPeptides(SameModificationPeptides peptide) {
    super();
    this.pephits = new ArrayList<SameModificationPeptides>();
    this.pephits.add(peptide);
    this.sequence = new SequenceModificationSitePair(peptide.getSequence());
  }
  
  public List<BuildSummaryPeptideHit> getPeptideHits() {
  	ArrayList<BuildSummaryPeptideHit> result = new ArrayList<BuildSummaryPeptideHit>();
  	for(SameModificationPeptides peps:pephits){
  		result.addAll(peps.getPeptideHits());
  	}
  	return result;
  }

  public void add(SameModificationPeptides peptide) {
    pephits.add(peptide);
    sequence.mergeWithSurePeptide(peptide.getSequence());
  }

  public SequenceModificationSitePair getSequence() {
    return sequence;
  }

  public List<SameModificationPeptides> getSameModificationPeptidesList() {
    return Collections.unmodifiableList(pephits);
  }

  public String getPureSequence() {
    return pephits.get(0).getPureSequence();
  }

  public int getPeptideHitCount() {
    int result = 0;

    for (SameModificationPeptides pep : pephits) {
      result += pep.getPeptideHitCount();
    }

    return result;
  }
}
