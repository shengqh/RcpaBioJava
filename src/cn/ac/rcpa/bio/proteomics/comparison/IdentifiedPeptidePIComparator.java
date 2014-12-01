package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

public class IdentifiedPeptidePIComparator
    implements Comparator<IIdentifiedPeptide> {
  private static IdentifiedPeptidePIComparator instance;

  public static IdentifiedPeptidePIComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedPeptidePIComparator();
    }
    return instance;
  }

  private IdentifiedPeptidePIComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(IIdentifiedPeptide peptide1, IIdentifiedPeptide peptide2) {
    final double pi1 = peptide1.getPI();
    final double pi2 = peptide2.getPI();

    return Double.compare(pi1, pi2);
  }
}
