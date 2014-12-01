package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideHitFilterByPeptideFilter
    implements IFilter<IIdentifiedPeptideHit> {
  private IFilter<IIdentifiedPeptide> pepFilter;

  public IdentifiedPeptideHitFilterByPeptideFilter(IFilter<IIdentifiedPeptide>
      pepFilter) {
    this.pepFilter = pepFilter;
  }

  public boolean accept(IIdentifiedPeptideHit e) {
    return e.getPeptideCount() > 0 && pepFilter.accept(e.getPeptide(0));
  }

  public String getType() {
    return pepFilter.getType();
  }

}
