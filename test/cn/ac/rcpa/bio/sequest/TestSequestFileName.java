package cn.ac.rcpa.bio.sequest;

import junit.framework.TestCase;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestSequestFileName extends TestCase {

  public void testParse() throws SequestParseException {
    SequestFilename dtaFileName = SequestFilename.parse("20021121-tu_1.2261.2263.1.dta");
    assertEquals("20021121-tu_1", dtaFileName.getExperiment());
    assertEquals(2261, dtaFileName.getFirstScan());
    assertEquals(2263, dtaFileName.getLastScan());
    assertEquals(1, dtaFileName.getCharge());
    assertEquals("dta", dtaFileName.getExtension());

    SequestFilename dtaFileName2 = SequestFilename.parse("20021121_tu.1.2261.2263.1.dta");
    assertEquals("20021121_tu.1", dtaFileName2.getExperiment());
    assertEquals(2261, dtaFileName2.getFirstScan());
    assertEquals(2263, dtaFileName2.getLastScan());
    assertEquals(1, dtaFileName2.getCharge());
    assertEquals("dta", dtaFileName2.getExtension());
  }

  public void testParseShortFilename() throws SequestParseException {
    SequestFilename dtaFileName = SequestFilename.parseShortFilename("20030109-totalLiver04,3670 - 3672");
    assertEquals("20030109-totalLiver04", dtaFileName.getExperiment());
    assertEquals(3670, dtaFileName.getFirstScan());
    assertEquals(3672, dtaFileName.getLastScan());

    SequestFilename dtaFileName2 = SequestFilename.parseShortFilename("20030109-totalLiver04,3670");
    assertEquals("20030109-totalLiver04", dtaFileName2.getExperiment());
    assertEquals(3670, dtaFileName2.getFirstScan());
    assertEquals(3670, dtaFileName2.getLastScan());

    SequestFilename dtaFileName3 = SequestFilename.parseShortFilename("\"20030109-totalLiver04,3670\"");
    assertEquals("20030109-totalLiver04", dtaFileName3.getExperiment());

    SequestFilename dtaFileName4 = SequestFilename.parseShortFilename("20030109-totalLiver04.Raw,3670");
    assertEquals("20030109-totalLiver04", dtaFileName4.getExperiment());
  }

}
