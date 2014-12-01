package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;

public class IdentifiedProteinGroupComparator implements Comparator<IIdentifiedProteinGroup>{
  private static IdentifiedProteinGroupComparator instance;

  public static IdentifiedProteinGroupComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedProteinGroupComparator();
    }
    return instance;
  }

  private IdentifiedProteinGroupComparator(){
  }

  public int compare(IIdentifiedProteinGroup group1, IIdentifiedProteinGroup group2) {
    if (group1.getProteinCount() == 0 || group2.getProteinCount() == 0){
      return group2.getProteinCount() - group1.getProteinCount();
    }

    final int unipepCount1 = group1.getProtein(0).getUniquePeptides().length;
    final int unipepCount2 = group2.getProtein(0).getUniquePeptides().length;
    if (unipepCount1 != unipepCount2){
      return unipepCount2 - unipepCount1;
    }

    return group2.getProtein(0).getPeptideCount() - group1.getProtein(0).getPeptideCount();
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }
}
