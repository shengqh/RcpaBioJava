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

public class BuildSummaryProteinGroupIndexParser implements
    IParser<BuildSummaryProtein> {

  public String getTitle() {
    return "GroupIndex";
  }

  public String getValue(BuildSummaryProtein obj) {
    return Integer.toString(obj.getGroup());
  }

  public String getDescription() {
    return "Group index";
  }

}
