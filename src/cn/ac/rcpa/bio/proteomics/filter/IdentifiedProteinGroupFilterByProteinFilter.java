/*
 * Created on 2005-5-18
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

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedProteinGroupFilterByProteinFilter implements IFilter<IIdentifiedProteinGroup>{
  private IFilter<IIdentifiedProtein> proteinFilter;
  private boolean filterChildren;

  public IdentifiedProteinGroupFilterByProteinFilter(IFilter<IIdentifiedProtein> proteinFilter, boolean filterChildren){
    this.proteinFilter = proteinFilter;
    this.filterChildren = filterChildren;
  }
  
  @SuppressWarnings("unchecked")
  public boolean accept(IIdentifiedProteinGroup e) {
    if (e.getProteinCount() == 0){
    return false;
    }
    
    final List<? extends IIdentifiedProtein> proteins = e.getProteins();
    for(IIdentifiedProtein protein:proteins){
      if (proteinFilter.accept(protein)){
        return true;
      }
    }
    
    if (filterChildren){
      List<IIdentifiedProteinGroup> children = e.getChildren();
      for(IIdentifiedProteinGroup child:children){
        if (accept(child)){
          return true;
        }
      }
    }
    
    return false;
  }

  public String getType() {
    return proteinFilter.getType();
  }

}
