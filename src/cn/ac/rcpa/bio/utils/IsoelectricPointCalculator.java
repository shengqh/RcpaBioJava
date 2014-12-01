package cn.ac.rcpa.bio.utils;

import cn.ac.rcpa.utils.RcpaMathUtils;

/**
 * @author Sheng Quanhu (shengqh@gmail.com/shengqh@263.net)
 */
public class IsoelectricPointCalculator {

  /**
   *
   * @param proteinSequence UPPERCASED protein sequence, characters not in
   *   ('A'..'Z') will be ignored.
   * @return Isoelectric point of protein sequence
   */
  public static double getPI(String proteinSequence) {
    int[] comp = getAminoacidComposition(proteinSequence);
    if (comp == null) {
      return 7.0;
    }

    int nTermResidue = getNTermResidue(proteinSequence);
    int cTermResidue = getCTermResidue(proteinSequence);

    double phMin = PH_MIN;
    double phMax = PH_MAX;

    for (int i = 0; i < MAXLOOP && (phMax - phMin) > EPSI; i++) {
      double phMid = phMin + (phMax - phMin) / 2;

      double cter =
          RcpaMathUtils.exp10( -cPk[cTermResidue][0])
          / (RcpaMathUtils.exp10( -cPk[cTermResidue][0]) + RcpaMathUtils.exp10( -phMid));
      double nter =
          RcpaMathUtils.exp10( -phMid) /
          (RcpaMathUtils.exp10( -cPk[nTermResidue][1]) + RcpaMathUtils.exp10( -phMid));

      double carg =
          comp[R] * RcpaMathUtils.exp10( -phMid) /
          (RcpaMathUtils.exp10( -cPk[R][2]) + RcpaMathUtils.exp10( -phMid));
      double chis =
          comp[H] * RcpaMathUtils.exp10( -phMid) /
          (RcpaMathUtils.exp10( -cPk[H][2]) + RcpaMathUtils.exp10( -phMid));
      double clys =
          comp[K] * RcpaMathUtils.exp10( -phMid) /
          (RcpaMathUtils.exp10( -cPk[K][2]) + RcpaMathUtils.exp10( -phMid));

      double casp =
          comp[D] * RcpaMathUtils.exp10( -cPk[D][2]) /
          (RcpaMathUtils.exp10( -cPk[D][2]) + RcpaMathUtils.exp10( -phMid));
      double cglu =
          comp[E] * RcpaMathUtils.exp10( -cPk[E][2]) /
          (RcpaMathUtils.exp10( -cPk[E][2]) + RcpaMathUtils.exp10( -phMid));

      double ccys =
          comp[C] * RcpaMathUtils.exp10( -cPk[C][2]) /
          (RcpaMathUtils.exp10( -cPk[C][2]) + RcpaMathUtils.exp10( -phMid));
      double ctyr =
          comp[Y] * RcpaMathUtils.exp10( -cPk[Y][2]) /
          (RcpaMathUtils.exp10( -cPk[Y][2]) + RcpaMathUtils.exp10( -phMid));

      double charge =
          carg + clys + chis + nter - (casp + cglu + ctyr + ccys + cter);

      if (charge > 0.0) {
        phMin = phMid;
      }
      else {
        phMax = phMid;
      }
    }

    return phMax;
  }

  private static int[] getAminoacidComposition(String aSequence) {
    int[] result = new int[26];
    for (int i = 0; i < 26; i++) {
      result[i] = 0;
    }

    boolean validResidueExisted = false;
    for (int i = 0; i < aSequence.length(); i++) {
      if (aSequence.charAt(i) < 'A' ||
          aSequence.charAt(i) > 'Z') {
        continue;
      }

      result[aSequence.charAt(i) - 'A']++;
      validResidueExisted = true;
    }

    if (validResidueExisted) {
      return result;
    }
    else {
      return null;
    }
  }

  private static int getNTermResidue(String aSequence) {
    int result = 0;
    for (int i = 0; i < aSequence.length(); i++) {
      if (aSequence.charAt(i) >= 'A' &&
          aSequence.charAt(i) <= 'Z') {
        result = aSequence.charAt(i) - 'A';
        break;
      }
    }
    return result;
  }

  private static int getCTermResidue(String aSequence) {
    int result = 0;
    for (int i = aSequence.length() - 1; i >= 0; i--) {
      if (aSequence.charAt(i) >= 'A' &&
          aSequence.charAt(i) <= 'Z') {
        result = aSequence.charAt(i) - 'A';
        break;
      }
    }
    return result;
  }

  // Table of pk values :
  // Note: the current algorithm does not use the last two columns. Each
  // row corresponds to an amino acid starting with Ala. J, O and U are
  // inexistant, but here only in order to have the complete alphabet.
  //
  // Ct Nt Sm Sc Sn
  //

  static private double[][] cPk = {
      {
      3.55, 7.59, 0., 0., 0., }
      , { // A
      3.55, 7.50, 0., 0., 0., }
      , { // B
      3.55, 7.50, 9.00, 9.00, 9.00, }
      , { // C
      4.55, 7.50, 4.05, 4.05, 4.05, }
      , { // D
      4.75, 7.70, 4.45, 4.45, 4.45, }
      , { // E
      3.55, 7.50, 0., 0., 0., }
      , { // F
      3.55, 7.50, 0., 0., 0., }
      , { // G
      3.55, 7.50, 5.98, 5.98, 5.98, }
      , { // H
      3.55, 7.50, 0., 0., 0., }
      , { // I
      0.00, 0.00, 0., 0., 0., }
      , { // J
      3.55, 7.50, 10.00, 10.00, 10.00, }
      , { // K
      3.55, 7.50, 0., 0., 0., }
      , { // L
      3.55, 7.00, 0., 0., 0., }
      , { // M
      3.55, 7.50, 0., 0., 0., }
      , { // N
      0.00, 0.00, 0., 0., 0., }
      , { // O
      3.55, 8.36, 0., 0., 0., }
      , { // P
      3.55, 7.50, 0., 0., 0., }
      , { // Q
      3.55, 7.50, 12.0, 12.0, 12.0, }
      , { // R
      3.55, 6.93, 0., 0., 0., }
      , { // S
      3.55, 6.82, 0., 0., 0., }
      , { // T
      0.00, 0.00, 0., 0., 0., }
      , { // U
      3.55, 7.44, 0., 0., 0., }
      , { // V
      3.55, 7.50, 0., 0., 0., }
      , { // W
      3.55, 7.50, 0., 0., 0., }
      , { // X
      3.55, 7.50, 10.00, 10.00, 10.00, }
      , { // Y
      3.55, 7.50, 0., 0., 0.}
      , // Z
  };

  static private double PH_MIN = 0; /* minimum pH value */
  static private double PH_MAX = 14; /* maximum pH value */
  static private int MAXLOOP = 2000; /* maximum number of iterations */
  static private double EPSI = 0.0001; /* desired precision */
  static private int R = 'R' - 'A';
  static private int H = 'H' - 'A';
  static private int K = 'K' - 'A';
  static private int D = 'D' - 'A';
  static private int E = 'E' - 'A';
  static private int C = 'C' - 'A';
  static private int Y = 'Y' - 'A';

  private IsoelectricPointCalculator() {
    super();
  }
}
