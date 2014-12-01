package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.util.Set;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.DistributionResultMap;

public class TestBuildSummaryPeptideExperimentalHitReader
    extends TestCase {
  private BuildSummaryPeptideHitExperimentalReader reader = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    reader = BuildSummaryPeptideHitExperimentalReader.getInstance();
  }

  @Override
  protected void tearDown() throws Exception {
    reader = null;
    super.tearDown();
  }


  public void testGetExperimental() throws Exception {
    Set<String> experimentals = reader.getExperimental("data/peptideUtilsTest.peptides");
    assertEquals(7,experimentals.size());
  }

  public void testGetPeptideExperimentalMap() throws Exception {
    DistributionResultMap map = reader.getExperimentalMap("data/peptideUtilsTest.peptides");
    assertEquals(4,map.size());
    assertEquals(new Integer(2), map.get("HLPGPQQQAFKLLQGLEDFIAK").get("HLPP_Nano_Salt01"));
  }
}
