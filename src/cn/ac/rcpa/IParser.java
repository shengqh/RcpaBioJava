package cn.ac.rcpa;

public interface IParser<T> {
  String getTitle();
  String getDescription();
  String getValue(T obj);
}
