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

public class IdentifiedProteinGroupFilterByPeptide implements
    IFilter<IIdentifiedProteinGroup> {
  private Map<String, Set<IIdentifiedProteinGroup>> scanGroupMap;

  private IFilter<IIdentifiedProteinGroup> groupFilter;

  public IdentifiedProteinGroupFilterByPeptide(
      Map<String, Set<IIdentifiedProteinGroup>> scanGroupMap,
      IFilter<IIdentifiedProteinGroup> groupFilter) {
    this.scanGroupMap = scanGroupMap;
    this.groupFilter = groupFilter;
  }

  @SuppressWarnings("unchecked")
  public boolean accept(IIdentifiedProteinGroup e) {
    final List<? extends IIdentifiedPeptideHit> peptides = e.getPeptideHits();
    for (IIdentifiedPeptideHit peptide : peptides) {
      Set<IIdentifiedProteinGroup> groupSet = scanGroupMap.get(peptide.getPeakListInfo().getLongFilename());
      if (groupSet == null){
        throw new IllegalStateException(peptide.getPeakListInfo().getLongFilename() + " has not corresponding protein group.");
      }

      for (IIdentifiedProteinGroup group : groupSet) {
        if (e != group && !groupFilter.accept(group)) {
          return false;
        }
      }
    }

    return true;
  }

  public String getType() {
    return "PepPercent";
  }

}
