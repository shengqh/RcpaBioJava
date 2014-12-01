package cn.ac.rcpa.bio.proteomics.filter;

import junit.framework.TestCase;

public class TestO18PeptideUtils extends TestCase {
  public void testIsO18Peptide() {
    assertFalse(O18PeptideUtils.isO18Peptide("DNLKATDK"));
    assertTrue(O18PeptideUtils.isO18Peptide("DNLKATDK*"));

    assertFalse(O18PeptideUtils.isO18Peptide("DNLKATDR"));
    assertTrue(O18PeptideUtils.isO18Peptide("DNLKATDR*"));

    //without K/R in C-terminal
    assertFalse(O18PeptideUtils.isO18Peptide("DNLKATD"));

    //with modified K/R in internal
    assertFalse(O18PeptideUtils.isO18Peptide("DNLK*ATDK"));
  }

  public void testIsO18CompatiblePeptide() {
    assertTrue(O18PeptideUtils.isO18CompatiblePeptide("DNLKATDK"));
    assertTrue(O18PeptideUtils.isO18CompatiblePeptide("DNLKATDK*"));
    assertTrue(O18PeptideUtils.isO18CompatiblePeptide("DNLKATDR"));
    assertTrue(O18PeptideUtils.isO18CompatiblePeptide("DNLKATDR*"));

    //without K/R in C-terminal
    assertFalse(O18PeptideUtils.isO18CompatiblePeptide("DNLKATD"));

    //with modified K/R in internal
    assertFalse(O18PeptideUtils.isO18CompatiblePeptide("DNLK*ATDK"));
  }

  public void testIsO18ValidPeptide() {
    //with/without modified k/r in C-terminal is valid
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DNLKATDK"));
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DNLKATDK*"));
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DNLKATDR"));
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DNLKATDR*"));
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DNLKATD"));
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DNLATD"));

    //length must large than 2
    assertFalse(O18PeptideUtils.isO18ValidPeptide(""));
    assertFalse(O18PeptideUtils.isO18ValidPeptide("D"));
    assertTrue(O18PeptideUtils.isO18ValidPeptide("DK"));

    //cannot contain modified k/r in internal
    assertFalse(O18PeptideUtils.isO18ValidPeptide("DNLK*ATDK"));
    assertFalse(O18PeptideUtils.isO18ValidPeptide("DNLR*ATDK"));
    assertFalse(O18PeptideUtils.isO18ValidPeptide("DNLK*ATD"));
    assertFalse(O18PeptideUtils.isO18ValidPeptide("DNLR*ATD"));
  }


}
