/*
 * Created on 2005-12-30
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein;

import java.text.DecimalFormat;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.utils.MassCalculator;

public class BuildSummaryProteinMassWeightParser implements
    IParser<BuildSummaryProtein> {
  private DecimalFormat df = new DecimalFormat("0.0000");

  public String getTitle() {
    return "MW";
  }

  public String getValue(BuildSummaryProtein obj) {
    try {
      return df.format(new MassCalculator(false).getMass(obj.getSequence()));
    } catch (SequenceValidateException e) {
      throw new IllegalStateException(e);
    }
  }

  public String getDescription() {
    return "Protein mass weight";
  }

}
