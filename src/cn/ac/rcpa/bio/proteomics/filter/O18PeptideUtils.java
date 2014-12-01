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
   * �ж�һ���Ķ��Ƿ�O18����ĶΣ�Ҫ����Cĩ��ΪK/R�������Ρ�
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
   * �ж�һ���Ķ��Ƿ����O18�ɱ�ǰ����ᣬҪ����Cĩ��ΪK/R��
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
   * �ж�һ���Ķ��Ƿ���O18ʵ�����������ĶΣ�Ҫ�����м䰱�������ΪK/R����ô���Ͳ��������Ρ�
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
