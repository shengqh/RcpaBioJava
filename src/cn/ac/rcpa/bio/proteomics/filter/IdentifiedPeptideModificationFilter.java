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
   * 根据指定的aminoacid进行修饰肽段的过滤
   * 
   * @param modifiedAminoacids
   *          String
   * @param modifiedCount
   *          int 指定修饰的个数
   * @param isExplicit
   *          bool
   *          true表示肽段修饰的个数必须与指定修饰数完全一样，false表示必须大于等于指定修饰数
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
