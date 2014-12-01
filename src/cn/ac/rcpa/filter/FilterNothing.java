package cn.ac.rcpa.filter;

public class FilterNothing<E>
    implements IFilter<E> {
  public FilterNothing() {
  }

  public boolean accept(E e) {
    return true;
  }

  public String getType() {
    return "FilterNothing";
  }
}
