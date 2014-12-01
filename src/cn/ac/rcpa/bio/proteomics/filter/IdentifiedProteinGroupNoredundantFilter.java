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

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedProteinGroupNoredundantFilter
    implements IFilter<IIdentifiedProteinGroup> {

  public boolean accept(IIdentifiedProteinGroup e) {
    return e.getParentCount() == 0;
  }

  public String getType() {
    return "Noredundant";
  }

}
