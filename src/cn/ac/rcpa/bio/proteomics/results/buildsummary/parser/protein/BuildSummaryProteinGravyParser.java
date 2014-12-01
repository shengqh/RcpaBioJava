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

import java.text.DecimalFormat;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.utils.GRAVYCalculator;

public class BuildSummaryProteinGravyParser implements
    IParser<BuildSummaryProtein> {
  private DecimalFormat df = new DecimalFormat("0.0000");

  public String getTitle() {
    return "Gravy";
  }

  public String getValue(BuildSummaryProtein obj) {
    return df.format(GRAVYCalculator.getGRAVY(obj.getSequence()));
  }

  public String getDescription() {
    return "Gravy";
  }

}
