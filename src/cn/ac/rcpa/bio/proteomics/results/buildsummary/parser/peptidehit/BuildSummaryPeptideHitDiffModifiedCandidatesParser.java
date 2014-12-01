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

public class BuildSummaryPeptideHitDiffModifiedCandidatesParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "DIFF_MODIFIED_CANDIDATE";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    return obj.getFollowCandidates().toString();
  }

  public String getDescription() {
    return "Peptide candidates with same sequence but different modification site";
  }

}
