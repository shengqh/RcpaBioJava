package cn.ac.rcpa.bio.database.impl;

import junit.framework.TestCase;

public class TestSwissProtAccessNumberParser extends TestCase {
  public void testGetValue() {
    String expectedReturn = "143B_MOUSE";
    String actualReturn = SwissProtAccessNumberParser.getInstance().getValue(">143B_MOUSE (Q9CQV8) 14-3-3 protein beta/alpha (Protein kinase C inhibitor protein-1) (KCIP-1)");
    assertEquals("return value", expectedReturn, actualReturn);
  }

}
