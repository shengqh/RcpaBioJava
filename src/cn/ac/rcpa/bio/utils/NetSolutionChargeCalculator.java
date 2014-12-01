package cn.ac.rcpa.bio.utils;

public class NetSolutionChargeCalculator {
  private NetSolutionChargeCalculator() {
  }

  private static boolean isAcidicAminoacid(char c) {
    return c == 'K' || c == 'R' || c == 'H';
  }

  private static boolean isBasicAminoacid(char c) {
    return c == 'D' || c == 'E';
  }

  public static int getAcidicNetSolutionCharge(String peptide) {
    int result = 1;
    for (int i = 1; i < peptide.length(); i++) {
      if (isAcidicAminoacid(peptide.charAt(i))) {
        result++;
      }
    }
    return result;
  }

  public static int getBasicNetSolutionCharge(String peptide) {
    int result = -1;
    for (int i = 0; i < peptide.length() - 1; i++) {
      if (isBasicAminoacid(peptide.charAt(i))) {
        result--;
      }
    }
    return result;
  }

}
