package cn.ac.rcpa.bio.proteomics;

import java.util.List;

public interface IIdentifiedPeptideHit<E extends IIdentifiedPeptide> {
  public List<E> getPeptides();

  public void addPeptide(E peptide);

  public void removePeptide(E peptide);

  public void removePeptide(int index);

  public int getPeptideCount();

  public E getPeptide(int index);

  public void clearPeptides();

  public IPeakListInfo getPeakListInfo();

  public FollowCandidatePeptideList getFollowCandidates();
  
  public List<String> getPeptideSequences();
}
