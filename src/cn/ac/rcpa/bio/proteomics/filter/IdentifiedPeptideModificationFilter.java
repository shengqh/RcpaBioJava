package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.SequenceUtils;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideModificationFilter implements
    IFilter<IIdentifiedPeptide> {
  private String modifiedAminoacids;

  private int modifiedCount;

  private boolean isExplicit;

  /**
   * ����ָ����aminoacid���������ĶεĹ���
   * 
   * @param modifiedAminoacids
   *          String
   * @param modifiedCount
   *          int ָ�����εĸ���
   * @param isExplicit
   *          bool
   *          true��ʾ�Ķ����εĸ���������ָ����������ȫһ����false��ʾ������ڵ���ָ��������
   */
  public IdentifiedPeptideModificationFilter(String modifiedAminoacids,
      int modifiedCount, boolean isExplicit) {
    super();
    this.modifiedAminoacids = modifiedAminoacids;
    this.modifiedCount = modifiedCount;
    this.isExplicit = isExplicit;
  }

  private boolean isCandidateModifiedAminoacid(char aminoacid) {
    return modifiedAminoacids.indexOf(aminoacid) != -1;
  }

  public boolean accept(IIdentifiedPeptide e) {
    final String sequence = PeptideUtils.getMatchPeptideSequence(e
        .getSequence());
    int iModifiedCount = 0;
    for (int i = 0; i < sequence.length() - 1; i++) {
      if (isCandidateModifiedAminoacid(sequence.charAt(i))) {
        if (SequenceUtils.isModifiedChar(sequence.charAt(i + 1))) {
          iModifiedCount++;
        }
      }
    }
    return modifiedCount <= 0 ? iModifiedCount > 0
        : (isExplicit ? iModifiedCount == modifiedCount
            : iModifiedCount >= modifiedCount);
  }

  public String getType() {
    StringBuffer sb = new StringBuffer();
    sb.append("Modified_");
    sb.append(modifiedAminoacids);
    if (modifiedCount > 0) {
      sb.append("_");
      sb.append(modifiedCount);
      if (!isExplicit) {
        sb.append("+");
      }
    }
    return sb.toString();
  }
}
