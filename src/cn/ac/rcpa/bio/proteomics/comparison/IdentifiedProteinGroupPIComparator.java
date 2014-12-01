package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;

public class IdentifiedProteinGroupPIComparator implements Comparator{
  private static IdentifiedProteinGroupPIComparator instance;

  public static IdentifiedProteinGroupPIComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedProteinGroupPIComparator();
    }
    return instance;
  }

  private IdentifiedProteinGroupPIComparator(){
  }

  public int compare(Object o1, Object o2) {
    final IIdentifiedProteinGroup group1 = (IIdentifiedProteinGroup)o1;
    final IIdentifiedProteinGroup group2 = (IIdentifiedProteinGroup)o2;

    if (group1.getProteinCount() == 0 || group2.getProteinCount() == 0){
      return group2.getProteinCount() - group1.getProteinCount();
    }

    return IdentifiedProteinPIComparator.getInstance().compare(group1.getProtein(0),
        group2.getProtein(0));
  }
}
