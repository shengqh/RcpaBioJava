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
import cn.ac.rcpa.bio.sequest.SequestFilename;

public class BuildSummaryPeptideHitFileScanParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "\"File, Scan(s)\"";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    SequestFilename filename = (SequestFilename) obj.getPeakListInfo();
    return filename.getShortFilename();
  }

  public String getDescription() {
    return "SEQUEST out filename (scans)";
  }

}
