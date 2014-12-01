package cn.ac.rcpa.bio.sequest;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TestSequestOutFile extends TestCase {

  public void testRead() throws IOException {
    String sOutFileName = "data/12228_15N_1_01.6027.6027.2.out";
    SequestOutFile out = SequestOutFile.read(sOutFileName, 2);

    assertEquals(2, out.getPeptideHitCount());
    assertEquals(1201.6200, out.getExperimentSingleChargeMass(), 0.001);
    assertEquals(0.4368, out.getPeptideHit(0).getDeltacn(), 0.001);

    assertEquals(0, out.getPeptideHit(0).getDuplicateRefCount());
    assertEquals(1, out.getPeptideHit(1).getDuplicateRefCount());
    assertEquals(2, out.getPeptideHit(1).getProteinNameCount());
    assertEquals("SE0430", out.getPeptideHit(1).getProteinName(0));
    assertEquals("SER0310", out.getPeptideHit(1).getProteinName(1));
  }

}
