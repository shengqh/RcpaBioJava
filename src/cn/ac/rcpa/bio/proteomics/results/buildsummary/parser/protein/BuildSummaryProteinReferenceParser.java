/*
 * Created on 2005-12-30
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;

public class BuildSummaryProteinReferenceParser implements
    IParser<BuildSummaryProtein> {

  public String getTitle() {
    return "Reference";
  }

  public String getValue(BuildSummaryProtein obj) {
    return obj.getReference();
  }

  public String getDescription() {
    return "Protein reference";
  }

}
