/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author sqh
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.utils;

import junit.framework.TestCase;

import org.biojava.bio.proteomics.ProteaseManager;

/**
 * @author sqh
 *
 */
public class TestMissedCleavagesCalculator extends TestCase {

  public void testGetTrypsinCount() {
    MissedCleavagesCalculator calc = new MissedCleavagesCalculator(ProteaseManager.getTrypsin());
    assertEquals(calc.getCount("AGFAGDDAPR"), 0);
    assertEquals(calc.getCount("ALESPERPFLAILGGAK"), 0);
    assertEquals(calc.getCount("ALESPERFFLAILGGAK"), 1);
    assertEquals(calc.getCount("ALESPERFFLKPLGGAK"), 1);
    assertEquals(calc.getCount("ALESPERFFLKILGGAK"), 2);
    assertEquals(calc.getCount("ALESPERFFLKILRGAK"), 3);
  }

}
