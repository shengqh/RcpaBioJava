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

public class IdentifiedPeptidePIClassification
    extends IdentifiedPeptideClassificationBasedOnSequence {

  public IdentifiedPeptidePIClassification(double[] ranges) {
    super(new PeptideSequencePIClassification(ranges));
  }
}
