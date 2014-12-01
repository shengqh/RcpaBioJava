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
import cn.ac.rcpa.bio.utils.NetSolutionChargeCalculator;

/**
 * @author sqh
 *
 */
public class PeptideSequenceBasicNetSolutionChargeClassification extends AbstractDiscreteClassification<String, Integer> {
  @Override
  protected Integer doGetClassification(String obj) {
    return NetSolutionChargeCalculator.getBasicNetSolutionCharge(PeptideUtils.getPurePeptideSequence(obj));
  }

  public String getPrinciple() {
    return "BasicNetSolutionCharge";
  }
}
