package cn.ac.rcpa.bio.database.ebi.protein;

import java.io.IOException;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.biojava.bio.BioException;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;
import cn.ac.rcpa.bio.database.ebi.protein.entry.Reference;

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

public class TestProteinEntrySwissProtParser extends TestCase {
  
  public TestProteinEntrySwissProtParser() throws NoSuchElementException, IOException, BioException{
    final ProteinEntryParser parser = new ProteinEntryParser();
    parser.open(testFilename);
    spEntry = parser.getNextEntry();
    parser.close();
  }
  
  private final String testFilename = "data/AAC4_HUMAN.sp";

  private ProteinEntry spEntry;

  public void testID() {
    assertEquals(spEntry.getEntry_name(), "AAC4_HUMAN");
    assertEquals(spEntry.getData_class(), "STANDARD");
    assertEquals(spEntry.getMolecule_type(), "PRT");
    assertEquals(spEntry.getSequence_length(), 911);
  }

  public void testAC() {
    assertEquals(4, spEntry.getAc_numberCount());
    assertEquals(spEntry.getAc_number(0), "O43707");
    assertEquals(spEntry.getAc_number(1), "O76048");
    assertEquals(spEntry.getAc_number(2), "HYPOTHETICAL_AC1");
    assertEquals(spEntry.getAc_number(3), "HYPOTHETICAL_AC2");
  }

  public void testDT() {
    assertEquals(spEntry.getCreate(), "16-OCT-2001 (Rel. 40, Created)");
    assertEquals(spEntry.getSequence_update(),
        "16-OCT-2001 (Rel. 40, Last sequence update)");
    assertEquals(spEntry.getAnnotation_update(),
        "25-JAN-2005 (Rel. 46, Last annotation update)");
  }

  public void testDE() {
    assertEquals(spEntry.getDescription(),
        "Alpha-actinin 4 (Non-muscle alpha-actinin 4) (F-actin cross linking protein).");
  }

  public void testGN() {
    assertEquals(spEntry.getGene_name(), "Name=ACTN4;");
  }

  public void testOS() {
    assertEquals(spEntry.getOrganism_species(), "Homo sapiens (Human)");
  }

  public void testOG() {
    assertEquals(spEntry.getOrganelle(), "Hypothetical Organelle.");
  }

  public void testOC() {
    assertEquals(
        spEntry.getOrganism_classification(),
        "Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia; Eutheria; Primates; Catarrhini; Hominidae; Homo");
  }

  public void testOX() {
    assertEquals(spEntry.getTaxonomy_id(), "NCBI_TaxID=9606");
  }

  public void testRerference() {
    assertEquals(8, spEntry.getReferenceCount());
    Reference ref = spEntry.getReference(7);
    assertEquals(ref.getPosition(),
        "VARIANTS FSGS1 GLU-255; ILE-259 AND PRO-262.");
    assertEquals(ref.getComment(), "TISSUE=Lymphocytes;");
    assertEquals(ref.getMedline_num(), 20164321);
    assertEquals(ref.getPubmed_num(), 10700177);
    assertEquals(ref.getDoi_num(), "10.1038/73456");
    assertEquals(
        ref.getAuthor(),
        "Kaplan J.M., Kim S.H., North K.N., Rennke H., Correia L.A., Tong H.-Q., Mathis B.J., Rodriguez-Perez J.-C., Allen P.G., Beggs A.H., Pollak M.R.;");
    assertEquals(
        ref.getTitle(),
        "Mutations in ACTN4, encoding alpha-actinin-4, cause familial focal segmental glomerulosclerosis.");
    assertEquals(ref.getLocation(), "Nat. Genet. 24:251-256(2000).");
  }

  public void testCC() {
    assertEquals(spEntry.getFree_commentCount(), 11);
    assertEquals(spEntry.getFree_comment(0).getCc_topic(), "FUNCTION");
    assertEquals(spEntry.getFree_comment(0).getCc_details(), "F-actin cross-linking protein which is thought to anchor actin to a variety of intracellular structures. This is a bundling protein.");
    assertEquals(spEntry.getFree_comment(1).getCc_topic(), "SUBUNIT");
    assertEquals(spEntry.getFree_comment(2).getCc_topic(), "SUBCELLULAR LOCATION");
    assertEquals(spEntry.getFree_comment(3).getCc_topic(), "TISSUE SPECIFICITY");
    assertEquals(spEntry.getFree_comment(4).getCc_topic(), "DISEASE");
    assertEquals(spEntry.getFree_comment(5).getCc_topic(), "DISEASE");
    assertEquals(spEntry.getFree_comment(6).getCc_topic(), "SIMILARITY");
    assertEquals(spEntry.getFree_comment(7).getCc_topic(), "SIMILARITY");
    assertEquals(spEntry.getFree_comment(8).getCc_topic(), "SIMILARITY");
    assertEquals(spEntry.getFree_comment(9).getCc_topic(), "SIMILARITY");
    assertEquals(spEntry.getFree_comment(10).getCc_topic(), "CAUTION");
    assertEquals(spEntry.getFree_comment(10).getCc_details(), "Ref.3 sequence differs from that shown by a high number of frameshifts, some of which we have corrected. The remaining sequence conflicts include both small frameshifts as well as point changes. They are probably sequencing errors when compared with the other mammalian sequences.");
  }

  public void testDR() {
    assertEquals(33, spEntry.getDb_referenceCount());
    assertEquals(spEntry.getDb_reference(0).getDb(), "EMBL");
    assertEquals(spEntry.getDb_reference(0).getPrimary_identifier(), "D89980");
    assertEquals(spEntry.getDb_reference(0).getSecondary_identifier(), "BAA24447.1");
    assertEquals(spEntry.getDb_reference(0).getTertiary_identifier(), "ALT_INIT");

    assertEquals(spEntry.getDb_reference(32).getDb(), "PROSITE");
    assertEquals(spEntry.getDb_reference(32).getPrimary_identifier(), "PS00018");
    assertEquals(spEntry.getDb_reference(32).getSecondary_identifier(), "EF_HAND");
    assertEquals(spEntry.getDb_reference(32).getTertiary_identifier(), "1");
  }

  public void testKW() {
    assertEquals(spEntry.getKeyword(), "Actin-binding; Calcium-binding; Disease mutation; Multigene family; Nuclear protein; Repeat.");
  }

  public void testFT() throws Exception {
    assertEquals(spEntry.getFeature_tableCount(), 28);
  }

  public void testSQ() {
    assertEquals(spEntry.getSequence().length(), 911);
  }
}
