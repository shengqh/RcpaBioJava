/*
 * Created on 2005-12-5
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.utils;

import junit.framework.TestCase;

public class PatternValueParserTest extends TestCase {

  /*
   * Test method for 'cn.ac.rcpa.utils.PatternValueParser.getValue(String)'
   */
  public void testGetValue() {
    PatternValueParser parser = new PatternValueParser("Fraction",
        "JWH_(\\S+)_(\\d+)_", "-", "%s error");

    assertEquals("SAX-25", parser.getValue("JWH_SAX_25_050906"));

    try {
      parser.getValue("JWH_SAX_A_050906");
      fail();
    } catch (Exception ex) {
      assertEquals("JWH_SAX_A_050906 error", ex.getMessage());
    }
  }

}
