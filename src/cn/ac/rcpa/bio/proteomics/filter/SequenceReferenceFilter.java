package cn.ac.rcpa.bio.proteomics.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.filter.IFilter;

public class SequenceReferenceFilter
    implements IFilter<Sequence> {
  private Collection<String> tokens = new ArrayList<String>();
  private Collection<String> refusedTokens = new ArrayList<String>();
  public SequenceReferenceFilter(Collection<String> tokens) {
    for(String token:tokens){
      if (token.startsWith("-")){
        this.refusedTokens.add(token.substring(1).toUpperCase());
      }
      else {
        this.tokens.add(token.toUpperCase());
      }
    }
  }

  private boolean accept(String reference, Collection<String> curTokens){
    for (String token : curTokens) {
      if (reference.indexOf(token) != -1) {
        return true;
      }
    }
    return false;
  }

  public boolean accept(Sequence e) {
    final String reference = ((String) e.getAnnotation().getProperty(
        "description_line")).toUpperCase();
    return accept(reference, tokens) && !accept(reference, refusedTokens);
  }

  public String getType() {
    return "ReferenceFilter";
  }
}
