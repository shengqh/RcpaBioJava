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


public interface IPeptideDrawer {
  /**
   * Get information from SequestResult and call the 
   * function drawPeptide(List<MatchedPeak> peaks, String peptide,
   *   int yPosition) in builder to draw peptide.
   * @param builder
   * @param sr
   * @param yPosition
   */
  void draw(IMatchedPeptideSpectrumImageBuilder builder, IIdentifiedPeptideResult sr, int yPosition);
}
