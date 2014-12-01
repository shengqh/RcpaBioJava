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

public class BuildSummaryPeptideHitReferencesParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "References";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < obj.getPeptideCount(); i++) {
      sb.append((i == 0 ? "\t" : " ! "));

      for (int j = 0; j < obj.getPeptide(i).getProteinNameCount(); j++) {
        if (j != 0) {
          sb.append('/');
        }
        sb.append(obj.getPeptide(i).getProteinName(j));
      }
    }
    return sb.toString();
  }

  public String getDescription() {
    return "Protein references";
  }

}
