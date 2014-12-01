package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;

public class IdentifiedPeptideHitPIComparator
    implements Comparator<IIdentifiedPeptideHit> {
  private static IdentifiedPeptideHitPIComparator instance;

  public static IdentifiedPeptideHitPIComparator getInstance() {
    if (instance == null) {
      instance = new IdentifiedPeptideHitPIComparator();
    }
    return instance;
  }

  private IdentifiedPeptideHitPIComparator() {
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(IIdentifiedPeptideHit peptide1, IIdentifiedPeptideHit peptide2) {
    final double pi1 = peptide1.getPeptide(0).getPI();
    final double pi2 = peptide2.getPeptide(0).getPI();

    return Double.compare(pi1, pi2);
  }
}
