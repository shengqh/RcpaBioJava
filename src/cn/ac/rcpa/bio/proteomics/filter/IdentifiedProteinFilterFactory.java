package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.filter.IFilter;

final public class IdentifiedProteinFilterFactory {
  private IdentifiedProteinFilterFactory() {
  }

  public static IFilter<IIdentifiedProtein> createGOAFilter() {
    return IdentifiedProteinGOAFilter.getInstance();
  }

  public static IFilter<IIdentifiedProtein> createNameFilter(String identity) {
    return new IdentifiedProteinNameFilter(identity);
  }
}
