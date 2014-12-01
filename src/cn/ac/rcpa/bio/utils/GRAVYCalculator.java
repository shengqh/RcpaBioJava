package cn.ac.rcpa.bio.utils;

import cn.ac.rcpa.bio.aminoacid.Aminoacids;

/*****
 * Class used to calculate Grand average of hydropathicity (GRAVY)
 * Author : Sheng QuanHu
 * Based on :
 * http://www.expasy.org/tools/pscale/Hphob.Doolittle.html
 * Amino acid scale: Hydropathicity.
 * Author(s): Kyte J., Doolittle R.F.
 * Reference: J. Mol. Biol. 157:105-132(1982).
 **/

public class GRAVYCalculator {
  private GRAVYCalculator() {
  }

  public static double getGRAVY(String proteinSequence) {
    double result = 0.0;
    int icount = 0;
    for (int i = 0; i < proteinSequence.length(); i++) {
      double hdropathicity = Aminoacids.getStableInstance().get(proteinSequence.
          charAt(i)).getHydropathicity();
      if (hdropathicity != 0.0) {
        result += hdropathicity;
        icount++;
      }
    }

    return icount == 0 ? 0.0 : result / icount;
  }

}
