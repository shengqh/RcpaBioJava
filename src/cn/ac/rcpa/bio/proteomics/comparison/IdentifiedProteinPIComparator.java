package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;

public class IdentifiedProteinPIComparator
    implements Comparator {
  private static IdentifiedProteinPIComparator instance;

  public static IdentifiedProteinPIComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedProteinPIComparator();
    }
    return instance;
  }

  private IdentifiedProteinPIComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(Object o1, Object o2) {
    final IIdentifiedProtein prohit1 = (IIdentifiedProtein) o1;
    final IIdentifiedProtein prohit2 = (IIdentifiedProtein) o2;

    final double pi1 = prohit1.getPI();
    final double pi2 = prohit2.getPI();

    if (pi1 < pi2) {
      return -1;
    }
    else if (pi1 > pi2) {
      return 1;
    }
    else {
      return 0;
    }
  }
}
