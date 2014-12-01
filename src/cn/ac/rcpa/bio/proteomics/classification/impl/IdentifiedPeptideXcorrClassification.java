/*
 * Created on 2006-2-16
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
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;

public class IdentifiedPeptideXcorrClassification extends
    AbstractContinuousClassification<IIdentifiedPeptide> {
  public IdentifiedPeptideXcorrClassification(double[] ranges) {
    super(ranges);
  }

  @Override
  protected double getValue(IIdentifiedPeptide obj) {
    BuildSummaryPeptide pep = (BuildSummaryPeptide) obj;
    return pep.getXcorr();
  }

  public String getPrinciple() {
    return "XCorr";
  }

}
