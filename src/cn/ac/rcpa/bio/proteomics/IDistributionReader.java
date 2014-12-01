package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface IDistributionReader {
  /**
   * 读取鉴定结果中涉及的实验名
   *
   * @param buildSummaryFile String
   * @return Set
   * @throws IOException
   */
  Set<String> getExperimental(String buildSummaryFile) throws
      IOException;

  /**
   * 取蛋白/肽段的实验分布图。
   *
   * @param buildSummaryFile String
   * @return DistributionResultMap
   * @throws IOException
   */
  DistributionResultMap getExperimentalMap(String
                                           buildSummaryFile) throws
      IOException;

  /**
   * 取蛋白/肽段的详细实验分布图。
   * 对于蛋白而言，是这个蛋白，包含的一系列肽段的分布图。
   * 对于肽段而言，是这个肽段，对应的一系列不同修饰肽段的分布图。
   *
   * @param buildSummaryFile String
   * @return Map
   * @throws IOException
   */
  Map<String, DistributionResultMap> getExperimentalDetailMap(String
                                           buildSummaryFile) throws
      IOException;
}
