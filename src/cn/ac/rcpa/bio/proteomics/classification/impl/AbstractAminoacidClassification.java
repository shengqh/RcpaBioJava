/*
 * Created on 2005-8-8
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.classification.impl;

/**
 * @author sqh
 *
 */
abstract public class AbstractAminoacidClassification<E> extends AbstractDiscreteClassification<E, Integer>{
  protected char aminoacid;
  public AbstractAminoacidClassification(char aminoacid){
    this.aminoacid = aminoacid;
  }

  public String getPrinciple() {
    return "Aminoacid_" + aminoacid;
  }
}
