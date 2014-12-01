package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description:
 * 根据给定的过滤肽段Filter，对Group中蛋白的肽段进行过滤，如果所有肽段被过滤掉，则删除所有Protein，返回false</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author not attributable
 * @version 1.0
 */
public class IdentifiedProteinGroupPeptideHitRemoverFilter
    implements IFilter<IIdentifiedProteinGroup> {
  private IFilter<IIdentifiedPeptide> pepFilter;
  public IdentifiedProteinGroupPeptideHitRemoverFilter(IFilter<
      IIdentifiedPeptide>
      pepFilter) {
    this.pepFilter = pepFilter;
  }

  public boolean accept(IIdentifiedProteinGroup group) {
    if (group.getProteinCount() == 0) {
      return false;
    }

    for(int pro = 0;pro < group.getProteinCount();pro++){
      IIdentifiedProtein protein = group.getProtein(pro);
      for (int i = protein.getPeptideCount() - 1; i >= 0; i--) {
        if (!pepFilter.accept(protein.getPeptide(i))) {
          protein.removePeptide(i);
        }
      }
    }

    if (group.getProtein(0).getPeptideCount() == 0) {
      group.clearProteins();
    }

    return group.getProteinCount() > 0;
  }

  public String getType() {
    return pepFilter.getType();
  }
}
