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

import java.text.DecimalFormat;

import cn.ac.rcpa.bio.proteomics.classification.IClassification;

abstract public class AbstractContinuousClassification<E> implements
    IClassification<E> {
  private double[] ranges;

  private String[] classifications;

  private static DecimalFormat df = new DecimalFormat("#.####");

  public final String[] getClassifications() {
    return classifications;
  }

  public AbstractContinuousClassification(double[] ranges) {
    this.ranges = new double[ranges.length + 1];
    for (int i = 0; i < ranges.length; i++) {
      this.ranges[i] = ranges[i];
    }
    this.ranges[ranges.length] = Double.MAX_VALUE;

    this.classifications = new String[ranges.length + 1];
    for (int i = 0; i < this.ranges.length; i++) {
      if (i == 0) {
        classifications[i] = "<=" + df.format(this.ranges[i]);
      } else if (i == this.ranges.length - 1) {
        classifications[i] = ">" + df.format(this.ranges[i - 1]);
      } else {
        classifications[i] = df.format(this.ranges[i - 1]) + "~"
            + df.format(this.ranges[i]);
      }
    }
  }

  final public String getClassification(E obj) {
    String result = "";

    double value = getValue(obj);
    for (int i = 0; i < ranges.length; i++) {
      if (value <= ranges[i]) {
        result = classifications[i];
        break;
      }
    }
    return result;
  }

  abstract protected double getValue(E obj);
}
