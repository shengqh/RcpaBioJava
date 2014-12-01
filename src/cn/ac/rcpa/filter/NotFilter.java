package cn.ac.rcpa.filter;

public class NotFilter<E>
    implements IFilter<E> {
  private IFilter<E> originFilter;

  public NotFilter(IFilter<E> originFilter) {
    this.originFilter = originFilter;
  }

  public boolean accept(E e) {
    return!originFilter.accept(e);
  }

  public String getType() {
    return "not_" + originFilter.getType();
  }
}
