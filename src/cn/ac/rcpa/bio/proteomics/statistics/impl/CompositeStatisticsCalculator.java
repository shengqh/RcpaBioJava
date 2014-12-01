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
package cn.ac.rcpa.bio.proteomics.statistics.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import cn.ac.rcpa.bio.proteomics.statistics.IStatisticsCalculator;

/**
 * @author sqh
 * 
 */
public class CompositeStatisticsCalculator<E> implements
    IStatisticsCalculator<E> {
  final private ArrayList<IStatisticsCalculator<E>> calculators;

  public CompositeStatisticsCalculator(
      Collection<IStatisticsCalculator<E>> calculators) {
    this.calculators = new ArrayList<IStatisticsCalculator<E>>(calculators);
  }

  public void clear() {
    for (IStatisticsCalculator<E> calulator : calculators) {
      calulator.clear();
    }
  }

  public void process(Collection< ? extends E> objs) {
    for (IStatisticsCalculator<E> calulator : calculators) {
      calulator.process(objs);
    }
  }

  public void output(PrintWriter writer) throws IOException {
    for (IStatisticsCalculator<E> calulator : calculators) {
      calulator.output(writer);
      writer.println();
    }
  }

  public String getPrinciple() {
    return "CompositeStatisticsCalculator";
  }

}
