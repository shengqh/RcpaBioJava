package cn.ac.rcpa.bio.proteomics.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.filter.IFilter;

public class SequenceNameFilter
    implements IFilter<Sequence> {
  private ArrayList<String> tokens;

  public SequenceNameFilter(Collection<String> tokens) {
    this.tokens = new ArrayList<String>(tokens);
  }

  public boolean accept(Sequence e) {
    final String proteinName = e.getName();
    for(String token:tokens){
      if (proteinName.indexOf(token) != -1){
        return true;
      }
    }
    return false;
  }

  public String getType() {
    return "ProteinNameFilter";
  }
}
