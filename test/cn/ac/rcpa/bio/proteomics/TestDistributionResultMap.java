package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideHitExperimentalReader;

public class TestDistributionResultMap
    extends TestCase {
  private DistributionResultMap map = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    map = BuildSummaryPeptideHitExperimentalReader.getInstance().
        getExperimentalMap("data/peptideUtilsTest.peptides");
  }

  @Override
  protected void tearDown() throws Exception {
    map = null;
    super.tearDown();
  }

  public void testClassify() {
    Map<String, String> experimantalClassfiedNameMap = new HashMap<String,
        String> ();
    experimantalClassfiedNameMap.put("HLPP_Nano_Salt01", "HLPP_Nano_Salt");
    experimantalClassfiedNameMap.put("HLPP_Nano_Salt02", "HLPP_Nano_Salt");
    experimantalClassfiedNameMap.put("HLPP_Salt_Step_7", "HLPP_Salt_Step");
    experimantalClassfiedNameMap.put("HLPP_Salt2_Step_1", "HLPP_Salt_Step");
    experimantalClassfiedNameMap.put("HLPP_Salt2_Step_2", "HLPP_Salt_Step");
    experimantalClassfiedNameMap.put("Whole_HLPP_2", "Whole_HLPP");
    experimantalClassfiedNameMap.put("Whole_HLPP_3", "Whole_HLPP");
    map.classify(experimantalClassfiedNameMap);

    List<String> expectedReturn = Arrays.asList(new String[] {
                                                "HLPP_Nano_Salt",
                                                "HLPP_Salt_Step",
                                                "Whole_HLPP"});
    assertEquals(expectedReturn, map.getClassifiedNames());
  }

  public void testGetClassifiedNames() throws IOException {
    List<String> expectedReturn = Arrays.asList(new String[] {
                                                "HLPP_Nano_Salt01",
                                                "HLPP_Nano_Salt02",
                                                "HLPP_Salt2_Step_1",
                                                "HLPP_Salt2_Step_2",
                                                "HLPP_Salt_Step_7",
                                                "Whole_HLPP_2",
                                                "Whole_HLPP_3"});

    List<String> actualReturn = map.getClassifiedNames();
    assertEquals(expectedReturn, actualReturn);
  }

  public void testGetSortedKeys() {
    List<String> expectedReturn = Arrays.asList(new String[] {
                                                "HLPGPQQQAFKLLQGLEDFIAK",
                                                "HLPGPQQQAFQLLQGLEDFIAK",
                                                "VLILGSGGLSIGQAGEFDYSGSQAVK",
                                                "AADTIGYPVMIR"
    });

    List<String> actualReturn = map.getSortedKeys();
    assertEquals(expectedReturn.size(), actualReturn.size());
    assertEquals(expectedReturn.get(0), actualReturn.get(0));
    assertEquals(expectedReturn.get(1), actualReturn.get(1));
  }

}
