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

import org.biojava.bio.proteomics.Protease;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.MissedCleavagesCalculator;

/**
 * @author sqh
 *
 */
public class PeptideSequenceMissedCleavagesClassification
    extends AbstractDiscreteClassification<String, Integer> {
  private MissedCleavagesCalculator calc;

  public String getPrinciple() {
    return "MissedCleavages";
  }

  public PeptideSequenceMissedCleavagesClassification(Protease protease) {
    calc = new MissedCleavagesCalculator(protease);
  }

  @Override
  protected Integer doGetClassification(String obj) {
    return calc.getCount(PeptideUtils.getPurePeptideSequence(obj));
  }
}
