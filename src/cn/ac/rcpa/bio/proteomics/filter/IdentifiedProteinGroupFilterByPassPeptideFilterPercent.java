/*
 * Created on 2005-5-19
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.filter;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedProteinGroupFilterByPassPeptideFilterPercent implements
    IFilter<IIdentifiedProteinGroup> {
  private Map<String, Set<IIdentifiedProteinGroup>> scanGroupMap;

  private IFilter<IIdentifiedProteinGroup> groupFilter;

  private double percentLimit;

  private boolean isMininumLimit;

  public IdentifiedProteinGroupFilterByPassPeptideFilterPercent(
      Map<String, Set<IIdentifiedProteinGroup>> scanGroupMap,
      IFilter<IIdentifiedProteinGroup> groupFilter,
      double percentLimit,
      boolean isMininumLimit) {
    this.scanGroupMap = scanGroupMap;
    this.groupFilter = groupFilter;
    this.percentLimit = percentLimit;
    this.isMininumLimit = isMininumLimit;
  }

  @SuppressWarnings("unchecked")
  public boolean accept(IIdentifiedProteinGroup e) {
    List<? extends IIdentifiedPeptideHit> peptides = e.getPeptideHits();
    int passFilterCount = 0;

    for (IIdentifiedPeptideHit peptide : peptides) {
      Set<IIdentifiedProteinGroup> groupSet = scanGroupMap.get(peptide.getPeakListInfo().getLongFilename());
      if (groupSet == null){
        throw new IllegalStateException(peptide.getPeakListInfo().getLongFilename() + " has not corresponding protein group.");
      }

      for (IIdentifiedProteinGroup group : groupSet) {
        if (e != group && groupFilter.accept(group)) {
          passFilterCount++;
          break;
        }
      }
    }

    double percent = (double) passFilterCount / peptides.size();

    return isMininumLimit ? percent >= percentLimit : percent <= percentLimit;
  }

  public String getType() {
    return "PepPercent";
  }

}
