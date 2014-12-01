package cn.ac.rcpa.bio.proteomics.filter;

import java.util.Collection;

import cn.ac.rcpa.bio.proteomics.DistributionResultMap;
import cn.ac.rcpa.filter.IFilter;

public class DistributionResultMapPepCountFilter
    implements IFilter<DistributionResultMap> {
  private int iMinCount;
  private Collection<String> classifiedNames;

  public DistributionResultMapPepCountFilter(Collection<String> classifiedNames,
                                             int iMinCount) {
    this.classifiedNames = classifiedNames;
    this.iMinCount = iMinCount;
  }

  public boolean accept(DistributionResultMap e) {
    for (String classified : classifiedNames) {
      if (e.getExperimentalCount(classified) >= iMinCount) {
        return true;
      }
    }
    return false;
  }

  public String getType() {
    return "PeptideCount";
  }
}
