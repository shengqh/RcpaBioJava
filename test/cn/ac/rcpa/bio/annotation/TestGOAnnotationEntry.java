package cn.ac.rcpa.bio.annotation;

import junit.framework.TestCase;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author not attributable
 * @version 1.0
 */

public class TestGOAnnotationEntry extends TestCase {
  public void testParseIPIGOA() {
    String line = "UniProt	O00115	DRN2_HUMAN		GO:0003677	PMID:9714827	TAS		F	Deoxyribonuclease II alpha precursor	IPI00010348	protein	taxon:9606	20030904	PINC";
    GOAnnotationEntry entry = GOAnnotationEntry.parse(line);
    assertEquals("UniProt", entry.getDb());
    assertEquals("O00115", entry.getDb_object_id());
    assertEquals("DRN2_HUMAN", entry.getDb_object_symbol());
    assertEquals("", entry.getQualifier());
    assertEquals("GO:0003677", entry.getGoid());
    assertEquals("PMID:9714827", entry.getDb_reference());
    assertEquals("TAS", entry.getEvidence());
    assertEquals("F", entry.getAspect());
    assertEquals("Deoxyribonuclease II alpha precursor", entry.getDb_object_name());
    assertEquals("IPI00010348", entry.getSynonym());
    assertEquals("protein", entry.getDb_object_type());
    assertEquals("9606", entry.getTaxon_ID());
    assertEquals("20030904", entry.getDate());
    assertEquals("PINC", entry.getAssigned_By());
  }

  public void testParseSptr(){
    String     line = "UniProt	O00050	O00050		GO:0003676	GOA:interpro	IEA		F	Transposase		protein	taxon:	20040603	UniProt";
    GOAnnotationEntry entry = GOAnnotationEntry.parse(line);
    assertEquals("UniProt", entry.getDb());
    assertEquals("O00050", entry.getDb_object_id());
    assertEquals("O00050", entry.getDb_object_symbol());
    assertEquals("", entry.getQualifier());
    assertEquals("GO:0003676", entry.getGoid());
    assertEquals("GOA:interpro", entry.getDb_reference());
    assertEquals("IEA", entry.getEvidence());
    assertEquals("F", entry.getAspect());
    assertEquals("Transposase", entry.getDb_object_name());
    assertEquals("", entry.getSynonym());
    assertEquals("protein", entry.getDb_object_type());
    assertEquals("", entry.getTaxon_ID());
    assertEquals("20040603", entry.getDate());
    assertEquals("UniProt", entry.getAssigned_By());
  }

}
