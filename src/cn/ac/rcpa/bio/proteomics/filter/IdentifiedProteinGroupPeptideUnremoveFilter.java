package cn.ac.rcpa.bio.proteomics.filter;

import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description:
 * ���ݸ����Ĺ����Ķ�Filter����Group�е��׵��Ķν��й��ˣ����ͨ��ɸѡ���Ķθ������ڵ��ڸ���ֵ��
 * �򷵻�truee</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author not attributable
 * @version 1.0
 */
public class IdentifiedProteinGroupPeptideUnremoveFilter
    implements IFilter<IIdentifiedProteinGroup>  {
  private IFilter<IIdentifiedPeptide> pepFilter;
  private int minPassCount;

  public IdentifiedProteinGroupPeptideUnremoveFilter(IFilter<IIdentifiedPeptide>
      pepFilter, int minPassCount) {
    this.pepFilter = pepFilter;
    this.minPassCount = minPassCount;
  }

  @SuppressWarnings("unchecked")
  public boolean accept(IIdentifiedProteinGroup group) {
    if (group.getProteinCount() == 0) {
      return false;
    }

    List<? extends IIdentifiedPeptideHit> peptides = group.getPeptideHits();
    int passcount = 0;
    for(IIdentifiedPeptideHit peptide:peptides){
      if (pepFilter.accept(peptide.getPeptide(0))) {
        passcount ++;
      }
    }

    return passcount >= minPassCount;
  }

  public String getType() {
    return pepFilter.getType();
  }
}
