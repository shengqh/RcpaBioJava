/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.IsoelectricPointCalculator;

public class PeptideSequencePIClassification extends AbstractContinuousClassification<String>{

  public PeptideSequencePIClassification(double[] ranges) {
    super(ranges);
  }

  @Override
  protected double getValue(String obj) {
    return IsoelectricPointCalculator.getPI(PeptideUtils.getPurePeptideSequence(obj));
  }

  public String getPrinciple() {
    return "IsotopicPoint";
  }
}
