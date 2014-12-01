package cn.ac.rcpa.bio.proteomics.filter;

import java.util.HashSet;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.filter.IFilter;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description:
 * 判定Group中满足给定filter的IdentifiedPeptide的UniquePeptide个数是否大于给定域值minUniquePeptideCount</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quanhu
 * @version 1.0
 */
public class IdentifiedProteinGroupUniquePeptideCountFilter
    implements IFilter<IIdentifiedProteinGroup> {
  private int minUniquePeptideCount;
  private IFilter<IIdentifiedPeptide> filter;

  public IdentifiedProteinGroupUniquePeptideCountFilter(int count,
      IFilter<IIdentifiedPeptide> filter) {
    this.minUniquePeptideCount = count;
    this.filter = filter;
  }

  @SuppressWarnings("unchecked")
  public boolean accept(IIdentifiedProteinGroup group) {
    List<? extends IIdentifiedPeptideHit> peptides = group.getPeptideHits();
    HashSet<String> uniquePeptides = new HashSet<String> ();
    for (IIdentifiedPeptideHit pephit : peptides) {
      final IIdentifiedPeptide peptide = pephit.getPeptide(0);
      if (filter == null || filter.accept(peptide)) {
        uniquePeptides.add(PeptideUtils.getPurePeptideSequence(peptide.
            getSequence()));
        if (uniquePeptides.size() >= minUniquePeptideCount) {
          return true;
        }
      }
    }

    return false;
  }

  public String getType() {
    return "UniquePeptideCount";
  }
}
