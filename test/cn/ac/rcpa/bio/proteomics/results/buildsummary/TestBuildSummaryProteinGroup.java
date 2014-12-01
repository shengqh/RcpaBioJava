package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestBuildSummaryProteinGroup extends TestCase {
  public void testClone() {
    BuildSummaryProteinGroup group = new BuildSummaryProteinGroup();
    group
        .addProtein(BuildSummaryResultReader
            .parseProtein("$1-1  SE0307 (1208) DNA-directed RNA polymerase beta' chain protein 227 34  57.91%  135262.85 5.9"));
    group
        .addProtein(BuildSummaryResultReader
            .parseProtein("$2-1  SER0182 (1213) DNA-directed RNA polymerase beta' chain protein  232 36  58.94%  135858.53 5.9"));
    group
        .addProtein(BuildSummaryResultReader
            .parseProtein("$3-1  SE0306 (1184) DNA-directed RNA polymerase beta chain protein  164 28  51.06%  133039.37 4.91"));

    BuildSummaryProteinGroup clonedGroup = (BuildSummaryProteinGroup) group
        .clone();

    assertFalse(clonedGroup == group);

    assertEquals(clonedGroup.getNumber(), group.getNumber());
    assertEquals(clonedGroup.getProteinCount(), group.getProteinCount());

    for (int i = 0; i < clonedGroup.getProteinCount(); i++) {
      assertFalse(clonedGroup.getProtein(i) == group.getProtein(i));
    }
  }

  public void testParentChildren() throws IOException, SequestParseException {
    BuildSummaryResult bsResult = (BuildSummaryResult) (new BuildSummaryResultReader()
        .read("data/parent_children.proteins"));
    bsResult.getProteinGroup(0).addChild(bsResult.getProteinGroup(1));
    bsResult.getProteinGroup(0).addChild(bsResult.getProteinGroup(1));

    assertEquals(1, bsResult.getProteinGroup(0).getChildrenCount());
    assertEquals(0, bsResult.getProteinGroup(1).getChildrenCount());
    assertEquals(0, bsResult.getProteinGroup(0).getParentCount());
    assertEquals(1, bsResult.getProteinGroup(1).getParentCount());
    assertEquals(bsResult.getProteinGroup(1), bsResult.getProteinGroup(0)
        .getChildren().iterator().next());
    assertEquals(bsResult.getProteinGroup(0), bsResult.getProteinGroup(1)
        .getParents().iterator().next());

    bsResult.getProteinGroup(0).removeChild(bsResult.getProteinGroup(1));
    assertEquals(0, bsResult.getProteinGroup(0).getChildrenCount());
    assertEquals(0, bsResult.getProteinGroup(1).getParentCount());
  }

  public void testGetPeptideHits() throws Exception{
    BuildSummaryResult bsResult = (BuildSummaryResult) (new BuildSummaryResultReader()
        .read("data/proteinGroup.noredundant"));
    List<BuildSummaryPeptideHit> pephits = bsResult.getProteinGroup(0).getPeptideHits();
    assertEquals(3, pephits.size());
    assertEquals(2, pephits.get(0).getPeptideCount());
    assertEquals(23, pephits.get(0).getPeptide(0).getProteinNameCount());
    assertEquals(4, pephits.get(0).getPeptide(1).getProteinNameCount());
  }
}
