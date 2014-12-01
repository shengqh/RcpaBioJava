package cn.ac.rcpa.bio.aminoacid;

import junit.framework.TestCase;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestAminoacids
    extends TestCase {
  private Aminoacids aminoacids = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    aminoacids = new Aminoacids();
  }

  @Override
  protected void tearDown() throws Exception {
    aminoacids = null;
    super.tearDown();
  }

  public void testAverageResiduesMass() {
    assertEquals(614.62, aminoacids.getAverageResiduesMass("ASDFGHJ"), 0.01);
  }

  public void testMonoResiduesMass() {
    assertEquals(614.2449, aminoacids.getMonoResiduesMass("ASDFGHJ"), 0.01);
  }

  public void testAveragePeptideMass() {
    assertEquals(632.62, aminoacids.getAveragePeptideMass("ASDFGHJ"), 0.01);
  }
}
