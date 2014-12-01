package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

public class IdentifiedPeptideMWComparator
    implements Comparator<IIdentifiedPeptide> {
  private static IdentifiedPeptideMWComparator instance;

  public static IdentifiedPeptideMWComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedPeptideMWComparator();
    }
    return instance;
  }

  private IdentifiedPeptideMWComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(IIdentifiedPeptide peptide1, IIdentifiedPeptide peptide2) {
    final double pi1 = peptide1.getTheoreticalSingleChargeMass();
    final double pi2 = peptide2.getTheoreticalSingleChargeMass();

    return Double.compare(pi1, pi2);
  }
}
