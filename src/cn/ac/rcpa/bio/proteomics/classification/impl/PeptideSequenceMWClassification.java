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

import cn.ac.rcpa.bio.proteomics.SequenceValidateException;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.MassCalculator;

public class PeptideSequenceMWClassification extends AbstractContinuousClassification<String>{
  private MassCalculator calc;

  public PeptideSequenceMWClassification(double[] ranges, boolean monoIsotopic) {
    super(ranges);
    calc = new MassCalculator(monoIsotopic);
  }

  public void setMonoIsotopic(boolean value){
  	calc = new MassCalculator(value);
  }
  
  @Override
  protected double getValue(String obj) {
    try {
      return calc.getMass(PeptideUtils.getPurePeptideSequence(obj));
    } catch (SequenceValidateException e) {
      e.printStackTrace();
      throw new IllegalStateException(e.getMessage());
    }
  }

  public String getPrinciple() {
    return "MassWeight";
  }
}
