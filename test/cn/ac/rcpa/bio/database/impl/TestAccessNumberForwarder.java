package cn.ac.rcpa.bio.database.impl;

import junit.framework.TestCase;

public class TestAccessNumberForwarder extends TestCase {
  private AccessNumberForwarder parser = AccessNumberForwarder.getInstance();

  public void testGetAccessNumber() {
    assertEquals("mmu:22138", parser.getValue("mmu:22138  Ttn; titin"));
    assertEquals("AAAAA", parser.getValue("AAAAA"));
  }

}
