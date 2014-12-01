package cn.ac.rcpa.bio.annotation.impl;

import java.io.File;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.bio.utils.GRAVYCalculator;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class GravyAnnotationProcessor
    extends AbstractValueRangeAnnotationProcessor {
  public GravyAnnotationProcessor(double[] thresholds) {
    super(thresholds);
  }

  /**
   * getValue
   *
   * @param fs FastaSequence
   * @param dbType DatabaseType
   * @return double
   */
  @Override
  protected double getValue(Sequence fs) {
    return GRAVYCalculator.getGRAVY(fs.seqString());
  }

  /**
   * getResultFile
   *
   * @param originFile File
   * @return File
   */
  @Override
  protected File getResultFile(File originFile) {
    return new File(originFile.getParentFile(), "STATISTIC/" + RcpaFileUtils.changeExtension(originFile.getName(), "gravy.stat"));
  }
}
