package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.DistributionResultMap;

public class TestBuildSummaryResultExperimentalReader extends TestCase {
  private BuildSummaryResultExperimentalReader buildSummaryResultExperimentalReader = BuildSummaryResultExperimentalReader.getInstance();

  public void testGetPeptideExperimentalMap() throws IOException {
    String buildSummaryResultFile = "data/parent_children.proteins";
    DistributionResultMap actualReturn = buildSummaryResultExperimentalReader.getExperimentalMap(buildSummaryResultFile);
    assertEquals(2, actualReturn.size());
  }

}
