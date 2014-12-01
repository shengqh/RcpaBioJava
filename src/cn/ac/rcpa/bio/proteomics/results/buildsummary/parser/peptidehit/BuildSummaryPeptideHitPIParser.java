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
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.IsoelectricPointCalculator;

public class BuildSummaryPeptideHitPIParser implements
    IParser<BuildSummaryPeptideHit> {
  private static DecimalFormat df2 = new DecimalFormat("0.00");

  public String getTitle() {
    return "PI";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return df2.format(IsoelectricPointCalculator.getPI(PeptideUtils
        .getPurePeptideSequence(obj.getPeptide(0).getSequence())));
  }

  public String getDescription() {
    return "Peptide PI";
  }

}
