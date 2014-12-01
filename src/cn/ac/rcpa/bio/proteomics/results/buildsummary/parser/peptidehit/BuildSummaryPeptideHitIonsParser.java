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

public class BuildSummaryPeptideHitIonsParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "Ions";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return obj.getPeptide(0).getMatchCount() + "|"
        + obj.getPeptide(0).getTheoreticalCount();
  }

  public String getDescription() {
    return "Match ion count | theoretical ion count";
  }

}
