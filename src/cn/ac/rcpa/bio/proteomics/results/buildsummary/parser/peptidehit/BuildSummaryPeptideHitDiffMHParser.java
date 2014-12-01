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

public class BuildSummaryPeptideHitDiffMHParser implements
    IParser<BuildSummaryPeptideHit> {
  private static DecimalFormat df5 = new DecimalFormat("#0.00000");

  public String getTitle() {
    return "Diff(MH+)";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return df5.format(obj.getPeptide(0).getDiffToExperimentMass());
  }

  public String getDescription() {
    return "MH+(Theoretical) - MH+(Experimental)";
  }

}
