package cn.ac.rcpa.filter;

import java.util.Arrays;
import java.util.Collection;

public class AndFilter<E> implements IFilter<E> {
  private Collection<IFilter<E>> filters;
  private String type;

  private void setFilters(Collection<IFilter<E>> filters){
    if (filters == null || filters.size() == 0){
      throw new IllegalArgumentException("Parameter filters of AndFilter should not be empty!");
    }
    this.filters = filters;

    setType();
  }

  private void setType() {
    type = "";
    for(IFilter<E> filter:filters){
      if (type.length() == 0){
        type = filter.getType();
      }
      else{
        type = type + "_and_" + filter.getType();
      }
    }
  }

  public AndFilter(IFilter<E>[] filters ) {
    setFilters(Arrays.asList( filters));
  }

  public AndFilter(Collection<IFilter<E>> filters ) {
    setFilters(filters);
  }

  public boolean accept(E e) {
    for(IFilter<E> filter:filters){
      if (!filter.accept(e)){
        return false;
      }
    }
    return true;
  }

  public String getType() {
    return type;
  }
}
