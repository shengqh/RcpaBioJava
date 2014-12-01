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
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.MassCalculator;

public class BuildSummaryPeptideHitMWParser implements
    IParser<BuildSummaryPeptideHit> {
  private DecimalFormat df = new DecimalFormat("0.0000");

  private static MassCalculator mc;

  public String getTitle() {
    return "AverageMW";
  }

  public String getDescription() {
    return "Peptide average mass weight";
  }

  public String getValue(BuildSummaryPeptideHit obj) {
    try {
      return df
          .format(getMc().getMass(
              PeptideUtils.getPurePeptideSequence(obj.getPeptide(0)
                  .getSequence())));
    } catch (SequenceValidateException e) {
      throw new IllegalStateException(e);
    }
  }

  private MassCalculator getMc() {
    if (null == mc) {
      mc = new MassCalculator(false);
    }
    return mc;
  }

}
