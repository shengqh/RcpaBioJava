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

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
/**
 * @author sqh
 *
 */
public class IdentifiedPeptideChargeClassification
    extends AbstractDiscreteClassification<IIdentifiedPeptide, Integer> {

  private static IdentifiedPeptideChargeClassification instance;

  public static IdentifiedPeptideChargeClassification getInstance() {
    if (instance == null) {
      instance = new IdentifiedPeptideChargeClassification();
    }
    return instance;
  }

  private IdentifiedPeptideChargeClassification() {
    super();
  }

  @Override
  protected Integer doGetClassification(IIdentifiedPeptide obj) {
    return obj.getPeakListInfo().getCharge();
  }

  public String getPrinciple() {
    return "Charge";
  }
}
