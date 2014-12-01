package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestBuildSummaryResult extends TestCase {
  public void testBuildGroupRelationship() throws IOException,
      SequestParseException {
    BuildSummaryResult bsResult = (BuildSummaryResult)(new BuildSummaryResultReader().read("data/parent_children.proteins"));
    bsResult.buildGroupRelationship();
    assertEquals(1,bsResult.getProteinGroup(0).getChildrenCount());
    assertEquals(0,bsResult.getProteinGroup(1).getChildrenCount());
    assertEquals(0,bsResult.getProteinGroup(0).getParentCount());
    assertEquals(1,bsResult.getProteinGroup(1).getParentCount());
    assertEquals(bsResult.getProteinGroup(1),bsResult.getProteinGroup(0).getChildren().iterator().next());
    assertEquals(bsResult.getProteinGroup(0),bsResult.getProteinGroup(1).getParents().iterator().next());
  }

  public void testClone() {
    BuildSummaryResult bsResult = new BuildSummaryResult();

    BuildSummaryProteinGroup group = new BuildSummaryProteinGroup();
    group.addProtein(BuildSummaryResultReader.parseProtein("$1-1  SE0307 (1208) DNA-directed RNA polymerase beta' chain protein 227 34  57.91%  135262.85 5.9"));
    bsResult.addProteinGroup(group);

    group = new BuildSummaryProteinGroup();
    group.addProtein(BuildSummaryResultReader.parseProtein("$2-1  SER0182 (1213) DNA-directed RNA polymerase beta' chain protein  232 36  58.94%  135858.53 5.9"));
    bsResult.addProteinGroup(group);

    group = new BuildSummaryProteinGroup();
    group.addProtein(BuildSummaryResultReader.parseProtein("$3-1  SE0306 (1184) DNA-directed RNA polymerase beta chain protein  164 28  51.06%  133039.37 4.91"));
    group.addProtein(BuildSummaryResultReader.parseProtein("$3-2  SER0181 (1183) DNA-directed RNA polymerase beta chain protein 164 28  51.06%  133021.34 4.91"));
    bsResult.addProteinGroup(group);

    BuildSummaryResult clonedResult = (BuildSummaryResult)bsResult.clone();

    assertFalse(clonedResult == bsResult);

    assertTrue(clonedResult.getProteinGroupCount() == bsResult.getProteinGroupCount() );

    for(int i = 0;i < clonedResult.getProteinGroupCount();i++){
      assertFalse(clonedResult.getProteinGroup(i) == bsResult.getProteinGroup(i));
      assertTrue(clonedResult.getProteinGroup(i).getProteinCount() == bsResult.getProteinGroup(i).getProteinCount());
    }
  }

}
