package cn.ac.rcpa.bio.proteomics.classification.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestIdentifiedPeptideLengthClassification
    extends TestCase {
  private IdentifiedPeptideLengthClassification cla;
  private List<BuildSummaryPeptide> objs;

  public void testGetClassification() throws SequestParseException, IOException {
    assertEquals("18", cla.getClassification(objs.get(0)));
    assertEquals("18", cla.getClassification(objs.get(1)));
    assertEquals("9", cla.getClassification(objs.get(2)));
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    objs = new ArrayList<BuildSummaryPeptide> ();
    addSequence("K.GLEAEATY*PYEGKDGPCR.Y");
    addSequence("-.M#ITIPLT*NEVVSMLSQNK.S");
    addSequence("K.GLLNAIVIR.E");

    cla = IdentifiedPeptideLengthClassification.getInstance();
  }

  private void addSequence(String seq) {
    BuildSummaryPeptide pep = new BuildSummaryPeptide();
    pep.setSequence(seq);

    objs.add(pep);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
