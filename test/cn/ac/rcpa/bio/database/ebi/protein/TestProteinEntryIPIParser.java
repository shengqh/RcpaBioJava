package cn.ac.rcpa.bio.database.ebi.protein;

import java.io.IOException;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.biojava.bio.BioException;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Li Long, Sheng QuanHu
 * @version 1.0
 */

public class TestProteinEntryIPIParser extends TestCase {
  
  public TestProteinEntryIPIParser() throws NoSuchElementException, IOException, BioException{
    final ProteinEntryParser parser = new ProteinEntryParser();
    parser.open(testFilename);
    ipiEntry = parser.getNextEntry();
    parser.close();
  }
  
  private final String testFilename = "data/AAC4_HUMAN.ipi";

  private ProteinEntry ipiEntry;

  public void testID() {
    assertEquals(ipiEntry.getEntry_name(), "IPI00013808.1");
    assertEquals(ipiEntry.getData_class(), "IPI");
    assertEquals(ipiEntry.getMolecule_type(), "PRT");
    assertEquals(ipiEntry.getSequence_length(), 911);
  }

  public void testAC() {
    assertEquals(1, ipiEntry.getAc_numberCount());
    assertEquals(ipiEntry.getAc_number(0), "IPI00013808");
  }

  public void testDT() {
    assertEquals(ipiEntry.getCreate(), "01-OCT-2001 (IPI Human rel. 2.00, Created)");
    assertEquals(ipiEntry.getSequence_update(),
        "01-OCT-2001 (IPI Human rel. 2.00, Last sequence update)");
    assertEquals(ipiEntry.getAnnotation_update(), null);
  }

  public void testDE() {
    assertEquals(ipiEntry.getDescription(),
        "ALPHA-ACTININ 4.");
  }

  public void testGN() {
    assertEquals(ipiEntry.getGene_name(), null);
  }

  public void testOS() {
    assertEquals(ipiEntry.getOrganism_species(), "Homo sapiens (Human)");
  }

  public void testOG() {
    assertEquals(ipiEntry.getOrganelle(), null);
  }

  public void testOC() {
    assertEquals(
        ipiEntry.getOrganism_classification(),
        "Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia; Eutheria; Primates; Catarrhini; Hominidae; Homo");
  }

  public void testOX() {
    assertEquals(ipiEntry.getTaxonomy_id(), "NCBI_TaxID=9606");
  }

  public void testRerference() {
    assertEquals(0, ipiEntry.getReferenceCount());
  }

  public void testCC() {
    assertEquals(ipiEntry.getFree_commentCount(), 3);
    assertEquals(ipiEntry.getFree_comment(0).getCc_topic(), "CHROMOSOME");
    assertEquals(ipiEntry.getFree_comment(0).getCc_details(), "19.");
    assertEquals(ipiEntry.getFree_comment(1).getCc_topic(), "START CO-ORDINATE");
    assertEquals(ipiEntry.getFree_comment(1).getCc_details(), "43830167.");
    assertEquals(ipiEntry.getFree_comment(2).getCc_topic(), "END CO-ORDINATE");
    assertEquals(ipiEntry.getFree_comment(2).getCc_details(), "43913010.");
  }

  public void testDR() {
    assertEquals(34, ipiEntry.getDb_referenceCount());
    assertEquals(ipiEntry.getDb_reference(0).getDb(), "ENSEMBL");
    assertEquals(ipiEntry.getDb_reference(0).getPrimary_identifier(), "ENSP00000252699");
    assertEquals(ipiEntry.getDb_reference(0).getSecondary_identifier(), "ENSG00000130402");
    assertEquals(ipiEntry.getDb_reference(0).getTertiary_identifier(), "-");

    assertEquals(ipiEntry.getDb_reference(33).getDb(), "RZPD");
    assertEquals(ipiEntry.getDb_reference(33).getPrimary_identifier(), "RZ_Hs.374303");
    assertEquals(ipiEntry.getDb_reference(33).getSecondary_identifier(), "-");
    assertEquals(ipiEntry.getDb_reference(33).getTertiary_identifier(), "Clones and other research material");
  }

  public void testKW() {
    assertEquals(ipiEntry.getKeyword(), null);
  }

  public void testFT() throws Exception {
    assertEquals(ipiEntry.getFeature_tableCount(), 0);
  }

  public void testSQ() {
    assertEquals(ipiEntry.getSequence().length(), 911);
  }
}
