package cn.ac.rcpa.processor;

import java.util.List;

public interface IProcessor<E> {
  /**
   * �������δ����˸ö����Processor�����ƣ��Ա�ͳ�Ƹö��󾭹�����Щprocessor����
   *
   * @param e E
   * @return Set
   */
  List<String> process(E e);
}
