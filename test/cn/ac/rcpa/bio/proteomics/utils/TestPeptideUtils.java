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
package cn.ac.rcpa.bio.proteomics.utils;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.IdentifiedResultIOFactory;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

/**
 * @author sqh
 *
 */
public class TestPeptideUtils extends TestCase {

  public void testGetPurePeptideSequence() {
    assertEquals(PeptideUtils.getPurePeptideSequence("A.AJKDJFKD.K"),"AJKDJFKD");
    assertEquals(PeptideUtils.getPurePeptideSequence("A.AJ@K#D*JFKD.K"),"AJKDJFKD");
    assertEquals(PeptideUtils.getPurePeptideSequence("AJKDJFKD"),"AJKDJFKD");
    assertEquals(PeptideUtils.getPurePeptideSequence("AJ@K#D*JFKD"),"AJKDJFKD");
  }

  public void testGetMatchPeptideSequence(){
    assertEquals(PeptideUtils.getMatchPeptideSequence("A.AJKDJFKD.K"),"AJKDJFKD");
    assertEquals(PeptideUtils.getMatchPeptideSequence("A.AJ@K#D*JFKD.K"),"AJ@K#D*JFKD");

    //sometimes sequest will generate a missed-last-char identified peptide
    assertEquals(PeptideUtils.getMatchPeptideSequence("A.AJ@K#D*JFKD."),"AJ@K#D*JFKD");
  }

  public void testIsModified(){
    assertTrue(PeptideUtils.isModified("A.A*JKDJFKD.K"));
    assertTrue(PeptideUtils.isModified("-.A*JKDJFKD.K"));
    assertTrue(PeptideUtils.isModified("A.A*JKDJFKD.-"));
    assertTrue(PeptideUtils.isModified("-.A*JKDJFKD.-"));
    assertTrue(PeptideUtils.isModified("A*JKDJFKD"));

    assertFalse(PeptideUtils.isModified("A.AJKDJFKD.K"));
    assertFalse(PeptideUtils.isModified("-.AJKDJFKD.K"));
    assertFalse(PeptideUtils.isModified("A.AJKDJFKD.-"));
    assertFalse(PeptideUtils.isModified("-.AJKDJFKD.-"));
    assertFalse(PeptideUtils.isModified("AJKDJFKD"));
  }

  public void testGetScanPeptideMap() throws Exception {
    final List<BuildSummaryPeptideHit> peptides = IdentifiedResultIOFactory.readBuildSummaryPeptideHit("data/peptideUtilsTest.peptides");
    final  Map<String, List<BuildSummaryPeptide>> scanPeptideMap = PeptideUtils.getScanPeptideMap(peptides);
    assertEquals(scanPeptideMap.keySet().size(), 15);
    assertEquals(scanPeptideMap.get("HLPP_Salt_Step_7.10696.10696.2").size(), 1);
    assertEquals(scanPeptideMap.get("HLPP_Nano_Salt01.15505.15505.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Nano_Salt01.15515.15515.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Nano_Salt02.16656.16656.3").size(), 1);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.21154.21154.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.21191.21191.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.21200.21200.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.21709.21709.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_2.16483.16483.2").size(), 2);
    assertEquals(scanPeptideMap.get("Whole_HLPP_2.33562.33562.2").size(), 2);
    assertEquals(scanPeptideMap.get("Whole_HLPP_3.31784.31784.2").size(), 2);
    assertEquals(scanPeptideMap.get("Whole_HLPP_3.31957.31957.2").size(), 2);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.21134.21134.3").size(), 1);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.21172.21172.3").size(), 1);
    assertEquals(scanPeptideMap.get("HLPP_Salt2_Step_1.34982.34982.2").size(), 1);
  }

  public void testGetUniquePeptides() throws Exception {
    final List<BuildSummaryPeptideHit> peptides = IdentifiedResultIOFactory.readBuildSummaryPeptideHit("data/peptideUtilsTest.peptides");
    final List<BuildSummaryPeptide> uniquePeptides = PeptideUtils.getUniquePeptides(peptides);
    assertEquals(uniquePeptides.size(), 3);
    assertEquals(uniquePeptides.get(0).getSequence(),"K.AADTIGYPVMIR.S");
    assertEquals(uniquePeptides.get(1).getSequence(),"K.HLPGPQQQAFKLLQGLEDFIAK.K");
    assertEquals(uniquePeptides.get(2).getSequence(),"K.VLILGSGGLSIGQAGEFDYSGSQAVK.A");
  }
  
  public void testRemoveModification(){
  	assertEquals("A.BBBBB.C", PeptideUtils.removeModification("A.BBBBB.C"));
  	assertEquals("-.BBBBB.C", PeptideUtils.removeModification("-.BBBBB.C"));
  	assertEquals("A.BBBBB.-", PeptideUtils.removeModification("A.BBBBB.-"));
  	assertEquals("A.BBBBB.C", PeptideUtils.removeModification("A.B*B@B#B&Bp.C"));
  }

}
