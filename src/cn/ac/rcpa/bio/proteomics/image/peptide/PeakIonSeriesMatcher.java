/*
 * Created on 2005-11-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.List;

import cn.ac.rcpa.bio.proteomics.IonType;

public class PeakIonSeriesMatcher implements IPeakMatcher {
  private IonType ionType;

  private double mzTolerance;
  
  private double minIntensityScale;

  public PeakIonSeriesMatcher(IonType ionType, double mzTolerance) {
    this.ionType = ionType;
    this.mzTolerance = mzTolerance;
    this.minIntensityScale = 0.05;
  }

  public PeakIonSeriesMatcher(IonType ionType, double mzTolerance, double minIntensityScale) {
    this.ionType = ionType;
    this.mzTolerance = mzTolerance;
    this.minIntensityScale = minIntensityScale;
  }

  public void match(IIdentifiedPeptideResult sr) {
    List<MatchedPeak> expPeaks = sr.getExperimentalPeakList().getPeaks();
    
    double minIntensity = sr.getExperimentalPeakList().getMaxIntensity() * minIntensityScale;

    MatchedPeakUtils.match(expPeaks, sr.getTheoreticalIonSeries().get(ionType), mzTolerance, minIntensity);
  }

}
