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

public class BuildSummaryPeptideHitRSpParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "RSp";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return Integer.toString(obj.getPeptide(0).getSpRank());
  }

  public String getDescription() {
    return "SEQUEST rank based on Sp";
  }

}
