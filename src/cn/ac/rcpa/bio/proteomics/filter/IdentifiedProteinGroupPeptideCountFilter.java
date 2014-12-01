package cn.ac.rcpa.bio.proteomics.filter;

import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description:
 * 判定Group中满足给定filter的IdentifiedPeptide个数是否大于给定域值minPeptideCount</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quanhu
 * @version 1.0
 */
public class IdentifiedProteinGroupPeptideCountFilter
    implements IFilter<IIdentifiedProteinGroup> {
  private int minPeptideCount;
  private IFilter<IIdentifiedPeptide> filter;

  public IdentifiedProteinGroupPeptideCountFilter(int minPeptideCount,
                                                  IFilter<IIdentifiedPeptide>
                                                  filter) {
    this.minPeptideCount = minPeptideCount;
    this.filter = filter;
  }

  @SuppressWarnings("unchecked")
  public boolean accept(IIdentifiedProteinGroup group) {
    List<? extends IIdentifiedPeptideHit> peptides = group.getPeptideHits();
    int iCount = 0;
    for (IIdentifiedPeptideHit pephit : peptides) {
      final IIdentifiedPeptide peptide = pephit.getPeptide(0);
      if (filter == null || filter.accept(peptide)) {
        iCount++;
        if (iCount >= minPeptideCount) {
          return true;
        }
      }
    }
    return false;
  }

  public String getType() {
    return "PeptideCount";
  }

}
