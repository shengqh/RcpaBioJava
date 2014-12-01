/*
 * Created on 2005-1-19
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.statistics.impl;

import java.util.Collection;

import cn.ac.rcpa.bio.proteomics.statistics.IStatisticsCalculator;

/**
 * @author sqh
 *
 */
public abstract class AbstractStatisticsCalculator<E> implements IStatisticsCalculator<E> {

  final public void process(Collection<? extends E> objs) {
    System.out.println("Calculating " + getPrinciple());

    int icount = 0;
    int totalSize = objs.size();
    for (E obj : objs) {
      icount++;
      if (icount % 10000 == 0) {
        System.out.println("Calculating " + icount + "\t" + icount * 100
            / totalSize + "%");
      }

      doProcess(obj);
    }

    System.out.println("Calculating " + getPrinciple() + " finished!");
  }

  abstract protected void doProcess(E obj);
}
