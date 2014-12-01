package cn.ac.rcpa.bio.proteomics.filter;

import java.util.Collection;
import java.util.HashSet;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedProteinNamesFilter
    implements IFilter<IIdentifiedProtein> {
  private HashSet<String> identities;

  public IdentifiedProteinNamesFilter(Collection<String> identities) {
    this.identities = new HashSet<String>();
    for(String identity:identities){
      this.identities.add(identity.toUpperCase());
    }
  }

  public boolean accept(IIdentifiedProtein prohit) {
    final String upcasedProteinName = prohit.getProteinName().toUpperCase();
    for (String identity : identities) {
      if (upcasedProteinName.indexOf(identity) != -1) {
        return true;
      }
    }
    return false;
  }

  public String getType() {
    return "ProteinNamesFilter";
  }
}
