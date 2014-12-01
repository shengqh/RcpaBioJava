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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.classification.IClassification;
import cn.ac.rcpa.utils.RcpaObjectUtils;

/**
 * @author sqh
 *
 */
abstract public class AbstractDiscreteClassification<E, V extends Comparable<V>> implements IClassification<E>{
  HashSet<V> classifications = new HashSet<V>();

  final public String getClassification(E obj){
    V result = doGetClassification(obj);
    classifications.add(result);
    return result.toString();
  }

  abstract protected V doGetClassification(E obj);

  final public String[] getClassifications() {
    List<V> result = new ArrayList<V>(classifications);
    Collections.sort(result);
    return RcpaObjectUtils.toStringArray(result);
  }
}
