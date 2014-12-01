package cn.ac.rcpa.bio.utils;

import java.util.LinkedHashMap;

import junit.framework.TestCase;

public class TestSequenceUtils extends TestCase {
  public void testGetProteinName() {
    assertEquals("AHNK_HUMAN", SequenceUtils.getProteinName("AHNK_HUMAN NEUROBLAST"));
    assertEquals("AHNK_HUMAN", SequenceUtils.getProteinName("AHNK_HUMAN"));
  }
  
  public void testGetAminoacidComposition(){
  	LinkedHashMap<Character, Double> comp = SequenceUtils.getAminoacidComposition(
  			"AFAFG", "AFGTE");
  	assertEquals(0.4, comp.get('A'),0.001);
  	assertEquals(0.4, comp.get('F'),0.001);
  	assertEquals(0.2, comp.get('G'),0.001);
  	assertEquals(0.0, comp.get('T'),0.001);
  	assertEquals(0.0, comp.get('E'),0.001);
  	
  	LinkedHashMap<Character, Double> comp2 = SequenceUtils.getAminoacidComposition(
  			"AF#A@FG", "AFGTE");
  	assertEquals(0.4, comp2.get('A'),0.001);
  	assertEquals(0.4, comp2.get('F'),0.001);
  	assertEquals(0.2, comp2.get('G'),0.001);
  	assertEquals(0.0, comp2.get('T'),0.001);
  	assertEquals(0.0, comp2.get('E'),0.001);
  	
  }

}
