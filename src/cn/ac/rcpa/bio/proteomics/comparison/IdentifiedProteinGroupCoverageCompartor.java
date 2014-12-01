package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;

public class IdentifiedProteinGroupCoverageCompartor
    implements Comparator<IIdentifiedProteinGroup>{
  private static IdentifiedProteinGroupCoverageCompartor instance;

  public static IdentifiedProteinGroupCoverageCompartor getInstance(){
    if (instance == null){
      instance = new IdentifiedProteinGroupCoverageCompartor();
    }
    return instance;
  }

  private IdentifiedProteinGroupCoverageCompartor(){
  }

  private double getMaxCoverage(IIdentifiedProteinGroup group){
    double result = 0.0;
    for(int i = 0;i < group.getProteinCount();i++){
      if (group.getProtein(i).getCoverage() > result){
        result = group.getProtein(i).getCoverage();
      }
    }
    return result;
  }

  public int compare(IIdentifiedProteinGroup group1, IIdentifiedProteinGroup group2) {

    double maxCoverage1 = getMaxCoverage(group1);
    double maxCoverage2 = getMaxCoverage(group2);

    return (maxCoverage1 == maxCoverage2) ? 0 : (maxCoverage1 < maxCoverage2 ? 1 : -1);
  }
}
