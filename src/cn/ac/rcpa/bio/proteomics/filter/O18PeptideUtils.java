package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.utils.SequenceUtils;

/**
 * <p>Title: O18 Peptide Utility</p>
 *
 * <p>Description: O18 related utility
 * internal</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng QuanHu (qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * @version 1.0
 */
public class O18PeptideUtils {
  public O18PeptideUtils() {
  }

  /**
   * 判断一个肽段是否O18标记肽段，要求是C末端为K/R并且修饰。
   *
   * @param matchSequence String
   * @return boolean
   */
  public static boolean isO18Peptide(String matchSequence) {
    if (!isO18CompatiblePeptide(matchSequence)){
      return false;
    }

    final char lastChar = matchSequence.charAt(matchSequence.length()-1);

    return SequenceUtils.isModifiedChar(lastChar);
  }

  /**
   * 判断一个肽段是否包含O18可标记氨基酸，要求是C末端为K/R。
   *
   * @param matchSequence String
   * @return boolean
   */
  public static boolean isO18CompatiblePeptide(String matchSequence) {
    if (!isO18ValidPeptide(matchSequence)){
      return false;
    }

    final char lastChar = matchSequence.charAt(matchSequence.length()-1);
    final char beforeChar = matchSequence.charAt(matchSequence.length()-2);

    return isO18Aminoacid(lastChar) || (isO18Aminoacid(beforeChar) && SequenceUtils.isModifiedChar(lastChar));
  }

  /**
   * 判断一个肽段是否是O18实验正常产生肽段，要求是中间氨基酸如果为K/R，那么，就不能是修饰。
   *
   * @param matchSequence String
   * @return boolean
   */
  public static boolean isO18ValidPeptide(String matchSequence) {
    if (matchSequence.length() < 2){
      return false;
    }

    for (int i = 0; i < matchSequence.length() - 1; i++) {
      if (isO18Aminoacid(matchSequence.charAt(i)) &&
          SequenceUtils.isModifiedChar(matchSequence.charAt(i + 1)) &&
          (i != matchSequence.length() - 2)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isO18Aminoacid(char aminoacid){
    return aminoacid == 'K' || aminoacid == 'R';
  }
}
