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
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class YSeriesPeptideDrawer extends AbstractIonSeriesPeptideDrawer {
  public YSeriesPeptideDrawer(IonType ionType) {
    super(ionType);
  }

  @Override
  protected String getMatchPeptide(IIdentifiedPeptideResult sr) {
    String matchPeptide = PeptideUtils.getMatchPeptideSequence(sr.getPeptide());
    StringBuilder sb = new StringBuilder();
    for (int i = matchPeptide.length() - 1; i >= 0; i--) {
      if (Character.isLetter(matchPeptide.charAt(i))) {
        sb.append(matchPeptide.charAt(i));
      } else if(i != 0){
        sb.append(matchPeptide.charAt(i - 1));
        sb.append(matchPeptide.charAt(i));
        i--;
      }
    }
    return sb.toString();
  }

}
