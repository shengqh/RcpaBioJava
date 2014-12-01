/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author sqh
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.statistics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public interface IStatisticsCalculator<E> {
  String getPrinciple();
  void clear();
  void process(Collection<? extends E> objs);
  void output(PrintWriter writer) throws IOException ;
}
