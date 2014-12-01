package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedProteinReferenceFilter
    implements IFilter<IIdentifiedProtein> {
  private String identity;

  public IdentifiedProteinReferenceFilter(String identity) {
    this.identity = identity.toUpperCase();
  }

  public boolean accept(IIdentifiedProtein prohit) {
    return prohit.getReference().toUpperCase().indexOf(identity) != -1;
  }

  public String getType() {
    return identity;
  }
}
