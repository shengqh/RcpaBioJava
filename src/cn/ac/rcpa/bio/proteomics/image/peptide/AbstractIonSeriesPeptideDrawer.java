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

import cn.ac.rcpa.bio.proteomics.IonType;

public abstract class AbstractIonSeriesPeptideDrawer implements IPeptideDrawer {
  private IonType ionType;

  public AbstractIonSeriesPeptideDrawer(IonType ionType) {
    this.ionType = ionType;
  }

  abstract protected String getMatchPeptide(IIdentifiedPeptideResult sr);

  public void draw(IMatchedPeptideSpectrumImageBuilder builder, IIdentifiedPeptideResult sr,
      int yPosition) {
    String matchPeptide = getMatchPeptide(sr);
    builder
        .drawPeptide(sr.getTheoreticalIonSeries().get(ionType), matchPeptide, yPosition);
  }

}
