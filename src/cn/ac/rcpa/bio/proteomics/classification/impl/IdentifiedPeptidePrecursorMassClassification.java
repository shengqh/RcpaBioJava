/*
 * Created on 2005-4-27
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
public class IdentifiedPeptidePrecursorMassClassification extends AbstractContinuousClassification<IIdentifiedPeptide>{

  public IdentifiedPeptidePrecursorMassClassification(double[] ranges) {
    super(ranges);
  }

  public String getPrinciple() {
    return "PrecursorMass";
  }


  @Override
  protected double getValue(IIdentifiedPeptide obj) {
    return (obj.getTheoreticalSingleChargeMass() + obj.getPeakListInfo().getCharge() - 1.0) / obj.getPeakListInfo().getCharge();
  }

}
