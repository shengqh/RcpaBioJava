package cn.ac.rcpa.bio.sequest;

import junit.framework.TestCase;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng QuanHu
 * @version 1.0
 */
public class TestSequestParams extends TestCase {

  public void testSetIonsSeries() {
    SequestParams sp = new SequestParams();

    String ionseries = "1 1 1 0.0 1.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0";
    sp.setIon_series(ionseries);
    assertEquals(sp.getIon_series(), ionseries);

    ionseries = "a 2 2 0.0 1.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0";
    try {
      sp.setIon_series(ionseries);
      fail("cannot catch exception");
    } catch (IllegalArgumentException ex) {
    }

    ionseries = "1 1 1 0.0 1.0 0.0 0.0 0.0 0.0 0.0 1.0";
    try {
      sp.setIon_series(ionseries);
      fail("cannot catch exception");
    } catch (IllegalArgumentException ex) {
    }
  }
}
