package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;

public class IdentifiedPeptideHitMWComparator
    implements Comparator<IIdentifiedPeptideHit> {
  private static IdentifiedPeptideHitMWComparator instance;

  public static IdentifiedPeptideHitMWComparator getInstance() {
    if (instance == null) {
      instance = new IdentifiedPeptideHitMWComparator();
    }
    return instance;
  }

  private IdentifiedPeptideHitMWComparator() {
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(IIdentifiedPeptideHit peptide1,
                     IIdentifiedPeptideHit peptide2) {
    final double pi1 = peptide1.getPeptide(0).getTheoreticalSingleChargeMass();
    final double pi2 = peptide2.getPeptide(0).getTheoreticalSingleChargeMass();

    return Double.compare(pi1, pi2);
  }
}
