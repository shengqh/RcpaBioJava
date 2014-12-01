package cn.ac.rcpa.processor;

import java.util.List;

public interface IProcessor<E> {
  /**
   * 返回依次处理了该对象的Processor的名称，以便统计该对象经过了哪些processor处理
   *
   * @param e E
   * @return Set
   */
  List<String> process(E e);
}
