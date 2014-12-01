package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;

public class IdentifiedProteinNameComparator
    implements Comparator {
  public IdentifiedProteinNameComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(Object o1, Object o2) {
    final IIdentifiedProtein prohit1 = (IIdentifiedProtein) o1;
    final IIdentifiedProtein prohit2 = (IIdentifiedProtein) o2;

    return prohit1.getProteinName().compareTo(prohit2.getProteinName());
  }
}
