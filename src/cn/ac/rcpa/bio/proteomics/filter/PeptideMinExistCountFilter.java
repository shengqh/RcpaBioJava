package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.CountMap;
import cn.ac.rcpa.filter.IFilter;

public class PeptideMinExistCountFilter implements IFilter<CountMap<String>> {
  private int minCount;
  private String type;

  public PeptideMinExistCountFilter(int minCount, int loopMaxCount) {
    this.minCount = minCount;
    this.type = Integer.toString(minCount);
    final String loopMaxCountStr = Integer.toString(loopMaxCount);
    for(int i = this.type.length();i < loopMaxCountStr.length();i++){
      this.type = "0" + this.type;
    }
    this.type = "MinExist" + this.type;
  }

  public boolean accept(CountMap<String> countMap) {
    int icount = 0;
    for(Integer count:countMap.values()){
      if (count != null && count > 0){
        icount ++;
        if (icount >= minCount){
          return true;
        }
      }
    }

    return false;
  }

  public String getType() {
    return this.type;
  }

}
