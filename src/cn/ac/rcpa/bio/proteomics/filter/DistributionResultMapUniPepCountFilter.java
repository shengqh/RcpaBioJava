package cn.ac.rcpa.bio.proteomics.filter;

import java.util.Collection;

import cn.ac.rcpa.bio.proteomics.DistributionResultMap;
import cn.ac.rcpa.filter.IFilter;

public class DistributionResultMapUniPepCountFilter implements IFilter<DistributionResultMap>{
  private int iMinCount;
  private Collection<String> classifiedNames;

  public DistributionResultMapUniPepCountFilter(Collection<String> classifiedNames, int iMinCount) {
    this.classifiedNames = classifiedNames;
    this.iMinCount = iMinCount;
  }

  public boolean accept(DistributionResultMap e) {
    for (String classified:classifiedNames){
      if (e.getExperimentalUniqueCount(classified) >= iMinCount){
        return true;
      }
    }
    return false;
  }

  public String getType() {
    return "UniquePeptideCount";
  }
}
