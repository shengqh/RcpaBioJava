package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;

public class IdentifiedProteinGroupPeptideCountComparator
    implements Comparator<IIdentifiedProteinGroup> {
  private static IdentifiedProteinGroupPeptideCountComparator instance;

  public static IdentifiedProteinGroupPeptideCountComparator getInstance(){
    if (instance == null){
      instance = new IdentifiedProteinGroupPeptideCountComparator();
    }
    return instance;
  }

  private IdentifiedProteinGroupPeptideCountComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(IIdentifiedProteinGroup arg0, IIdentifiedProteinGroup arg1) {
    return arg1.getPeptideHitCount() - arg0.getPeptideHitCount();
  }

}
