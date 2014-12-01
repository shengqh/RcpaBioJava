package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface IDistributionReader {
  /**
   * ��ȡ����������漰��ʵ����
   *
   * @param buildSummaryFile String
   * @return Set
   * @throws IOException
   */
  Set<String> getExperimental(String buildSummaryFile) throws
      IOException;

  /**
   * ȡ����/�Ķε�ʵ��ֲ�ͼ��
   *
   * @param buildSummaryFile String
   * @return DistributionResultMap
   * @throws IOException
   */
  DistributionResultMap getExperimentalMap(String
                                           buildSummaryFile) throws
      IOException;

  /**
   * ȡ����/�Ķε���ϸʵ��ֲ�ͼ��
   * ���ڵ��׶��ԣ���������ף�������һϵ���Ķεķֲ�ͼ��
   * �����Ķζ��ԣ�������ĶΣ���Ӧ��һϵ�в�ͬ�����Ķεķֲ�ͼ��
   *
   * @param buildSummaryFile String
   * @return Map
   * @throws IOException
   */
  Map<String, DistributionResultMap> getExperimentalDetailMap(String
                                           buildSummaryFile) throws
      IOException;
}
