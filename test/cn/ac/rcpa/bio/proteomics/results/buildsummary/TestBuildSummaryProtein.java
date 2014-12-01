package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;

public class TestBuildSummaryProtein extends TestCase {
  private BuildSummaryProtein protein = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    protein = BuildSummaryResultReader.parseProtein("$1-1	SE0307 (1208) DNA-directed RNA polymerase beta' chain protein	227	34	57.91%	135262.85	5.9");
  }

  @Override
  protected void tearDown() throws Exception {
    protein = null;
    super.tearDown();
  }

  public void testClone() {
    BuildSummaryProtein clonedProtein = (BuildSummaryProtein) protein.clone();

    assertFalse(clonedProtein == protein);

    assertEquals(protein.getProteinName(), clonedProtein.getProteinName());
    assertEquals(protein.getReference(), clonedProtein.getReference());
    assertEquals(protein.getSequence(), clonedProtein.getSequence());
    assertEquals(protein.getMW(), clonedProtein.getMW());
    assertEquals(protein.getPI(), clonedProtein.getPI());
    assertEquals(protein.getCoverage(), clonedProtein.getCoverage());

    assertFalse(protein.getAnnotation() == clonedProtein.getAnnotation());
    assertEquals(protein.getAnnotation(), clonedProtein.getAnnotation());

    assertFalse(protein.getPeptides() == clonedProtein.getPeptides());
    assertEquals(protein.getPeptides(), clonedProtein.getPeptides());

    assertEquals(protein.getDatabase(), clonedProtein.getDatabase());
    assertEquals(protein.getGroup(), clonedProtein.getGroup());
  }

}
