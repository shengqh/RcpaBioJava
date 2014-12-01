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

import java.text.DecimalFormat;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class BuildSummaryPeptideHitXCorrParser implements IParser<BuildSummaryPeptideHit>{
  private static DecimalFormat df4 = new DecimalFormat("#0.0000");

  public String getTitle() {
    return "XCorr";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return df4.format(obj.getPeptide(0).getXcorr());
  }

  public String getDescription() {
    return "SEQUEST XCorr value";
  }

}
