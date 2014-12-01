package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;

public class IdentifiedProteinMWComparator
    implements Comparator {
  private static IdentifiedProteinMWComparator instance;

  public static IdentifiedProteinMWComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedProteinMWComparator();
    }
    return instance;
  }

  private IdentifiedProteinMWComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(Object o1, Object o2) {
    final IIdentifiedProtein prohit1 = (IIdentifiedProtein) o1;
    final IIdentifiedProtein prohit2 = (IIdentifiedProtein) o2;

    final double pi1 = prohit1.getMW();
    final double pi2 = prohit2.getMW();

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
