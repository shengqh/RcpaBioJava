package cn.ac.rcpa.bio.database.impl;

import junit.framework.TestCase;

public class SwissProtIdentityParserTest extends TestCase {

	/*
	 * Test method for 'cn.ac.rcpa.bio.database.impl.SwissProtIdentityParser.getValue(String)'
	 */
	public void testGetValue() {
    String expectedReturn = "Q9CQV8";
    String actualReturn = SwissProtIdentityParser.getInstance().getValue(">143B_MOUSE (Q9CQV8) 14-3-3 protein beta/alpha (Protein kinase C inhibitor protein-1) (KCIP-1)");
    assertEquals("return value", expectedReturn, actualReturn);

	}

}
