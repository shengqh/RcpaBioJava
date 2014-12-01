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
package cn.ac.rcpa.bio.proteomics.statistics;

import java.util.ArrayList;
import java.util.Collection;

import cn.ac.rcpa.bio.proteomics.classification.IClassification;
import cn.ac.rcpa.bio.proteomics.statistics.impl.CompositeStatisticsCalculator;
import cn.ac.rcpa.bio.proteomics.statistics.impl.OneDimensionStatisticsCalculator;
import cn.ac.rcpa.bio.proteomics.statistics.impl.TwoDimensionStatisticsCalculator;

/**
 * @author sqh
 *
 */
public class StatisticsCalculatorFactory {
  public final static int OUTPUT_NONE = 0;
  public final static int OUTPUT_FIRST = 1;
  public final static int OUTPUT_SECOND = 2;
  public final static int OUTPUT_BOTH = 3;

  public static <E> IStatisticsCalculator<E> getStatisticsCalculator(IClassification<E> classification){
    return new OneDimensionStatisticsCalculator<E>(classification);
  }

  public static <E> IStatisticsCalculator<E> getStatisticsCalculator(IClassification<E> cf1, IClassification<E> cf2, int outputIndividual){
    return new TwoDimensionStatisticsCalculator<E>(cf1, cf2, outputIndividual);
  }

  public static <E> IStatisticsCalculator<E> getStatisticsCalculator(Collection<IClassification<E>> cfs){
    ArrayList<IStatisticsCalculator<E>> calcs = new ArrayList<IStatisticsCalculator<E>>();
    for (IClassification<E> cf:cfs) {
      calcs.add(getStatisticsCalculator(cf));
    }
    return new CompositeStatisticsCalculator<E>(calcs);
  }

  public static <E> IStatisticsCalculator<E> getCompositeStatisticsCalculator(Collection<IStatisticsCalculator<E>> calcs){
    return new CompositeStatisticsCalculator<E>(calcs);
  }
}
