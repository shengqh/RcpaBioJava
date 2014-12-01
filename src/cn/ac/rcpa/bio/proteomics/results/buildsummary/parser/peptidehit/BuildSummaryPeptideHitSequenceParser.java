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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class BuildSummaryPeptideHitSequenceParser implements
    IParser<BuildSummaryPeptideHit> {

  public String getTitle() {
    return "Sequence";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    List<String> seqs = new ArrayList<String>();
    for (BuildSummaryPeptide pep : obj.getPeptides()) {
      seqs.add(pep.getSequence());
    }

    return StringUtils.join(seqs.iterator(), " ! ");
  }

  public String getDescription() {
    return "Peptide sequence";
  }

}
