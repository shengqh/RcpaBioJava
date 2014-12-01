package cn.ac.rcpa.filter;

public interface IFilter<E> {
  boolean accept(E e);
  String getType();
}
