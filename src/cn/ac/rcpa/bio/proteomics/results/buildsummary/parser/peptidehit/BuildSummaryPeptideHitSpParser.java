/*
 * Created on 2005-12-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class BuildSummaryPeptideHitSpParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "Sp";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return Double.toString(obj.getPeptide(0).getSp());
  }

  public String getDescription() {
    return "SEQUEST sp value";
  }

}
