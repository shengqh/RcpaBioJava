package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.proteomics.ProteinInfoType;

public class TestBuildSummaryResultProteinInfoReader extends TestCase {
  private BuildSummaryResultProteinInfoReader buildSummaryResultProteinInfoReader = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    Set<ProteinInfoType> requiredTypes = new LinkedHashSet<ProteinInfoType>(
      Arrays.asList(ProteinInfoType.values()) );
    buildSummaryResultProteinInfoReader = new BuildSummaryResultProteinInfoReader(SequenceDatabaseType.IPI , requiredTypes);
  }

  @Override
  protected void tearDown() throws Exception {
    buildSummaryResultProteinInfoReader = null;
    super.tearDown();
  }

  public void testRead() throws IOException {
    String filename = "data/parent_children.proteins";
    Map<String, Map<ProteinInfoType, String>> actualReturn = buildSummaryResultProteinInfoReader.read(filename);
    assertEquals(new LinkedHashSet<String>(Arrays.asList(new String[]{"IPI00215349","IPI00364112"})), actualReturn.keySet());

    assertEquals("IPI:IPI00215349.3|TREMBL:Q5RKI0|REFSEQ_XP:XP_341229|ENSEMBL:ENSRNOP00000024012 Tax_Id=10116 Hypothetical protein", actualReturn.get("IPI00215349").get(ProteinInfoType.Reference));
    assertEquals("2", actualReturn.get("IPI00215349").get(ProteinInfoType.PepCount));
    assertEquals("2", actualReturn.get("IPI00215349").get(ProteinInfoType.UniquePepCount));
    assertEquals("3.14%", actualReturn.get("IPI00215349").get(ProteinInfoType.CoverPercent));
    assertEquals("66181.29", actualReturn.get("IPI00215349").get(ProteinInfoType.MW));
    assertEquals("6.15", actualReturn.get("IPI00215349").get(ProteinInfoType.PI));

  }

}
