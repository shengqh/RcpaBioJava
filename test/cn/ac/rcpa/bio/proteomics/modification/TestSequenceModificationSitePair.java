package cn.ac.rcpa.bio.proteomics.modification;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestSequenceModificationSitePair extends TestCase {
  private static final String modifiedAminoacids = "STY";

  public void testMergeWithAmbigiousOfDifferentSite() {
    // GLEAEATY*PYEGKDGPCR + GLEAEATYPY#EGKDGPCR -> GLEAEATYpPYpEGKDGPCR
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.GLEAEATY*PYEGKDGPCR.Y");

    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.GLEAEATYPY#EGKDGPCR.Y");

    current.mergeWithAmbiguous(another);
    assertEquals("GLEAEATYpPYpEGKDGPCR", current.getModifiedSequence());
  }

  public void testMergeWithAmbigiousOfMultipleState() {
    // KEES*EES@DDDMGFGLFD + KEES@EES*DDDMGFGLFD -> KEES*EES@DDDMGFGLFD
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.KEES*EES@DDDMGFGLFD.-");

    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.KEES@EES*DDDMGFGLFD.-");

    current.mergeWithAmbiguous(another);
    assertEquals("KEES*EES@DDDMGFGLFD", current.getModifiedSequence());
  }

  public void testMergeSurePeptideWithContainmentAscending() {
    // KEES*EES*DDDM#GFGLFD + EESEES*DDDMGFGLFD -> KEES*EES*DDDMGFGLFD
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.KEES*EES*DDDM#GFGLFD.-");

    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.EESEES*DDDMGFGLFD.-");

    current.mergeWithSurePeptide(another);
    assertEquals("KEES*EES*DDDMGFGLFD", current.getModifiedSequence());
  }

  public void testMergeSurePeptideWithContainmentDescending() {
    // KEESEES*DDDM#GFGLFD + EES*EES*DDDMGFGLFD -> KEES*EES*DDDMGFGLFD
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.KEESEES*DDDM#GFGLFD.-");

    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.EES*EES*DDDMGFGLFD.-");

    current.mergeWithSurePeptide(another);
    assertEquals("KEES*EES*DDDMGFGLFD", current.getModifiedSequence());
  }

  public void testMergeSurePeptideWithOverlap() {
    // KEES*EES*DDDM#GFGLFD + EESEES*DDDMGFGLFD -> KEES*EES*DDDMGFGLFD
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "R.KEES*EES*DDDM#GFGLFD.K");

    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.EESEES*DDDMGFGLFDK.R");

    assertTrue(current.mergeOverlap(another, "EESEESDDDMGFGLFD"));
    assertEquals("KEES*EES*DDDMGFGLFDK", current.getModifiedSequence());
  }

  public void testMergeSurePeptideWithAmbigiousSequenceAscending() {
    // KT*EESpEESpEDDMGFGLFD + KT*EESEES*EDDMGFGLFD -> KT*EESEES*EDDMGFGLFD
    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.KT*EESEES*EDDMGFGLFD.-");
    another.mergeWithAmbiguous(new SequenceModificationSitePair("STY",
        "K.KT*EES*EESEDDMGFGLFD.-"));

    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.KT*EESEES*EDDMGFGLFD.-");

    current.mergeWithSurePeptide(another);
    assertEquals(2, current.getModifiedCount());
    assertEquals("KT*EESEES*EDDMGFGLFD", current.getModifiedSequence());
  }

  public void testMergeSurePeptideWithAmbigiousSequenceDescending() {
    // KT*EESpEESpEDDMGFGLFD + KT*EESEES*EDDMGFGLFD -> KT*EESEES*EDDMGFGLFD
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.KT*EESEES*EDDMGFGLFD.-");
    current.mergeWithAmbiguous(new SequenceModificationSitePair("STY",
        "K.KT*EES*EESEDDMGFGLFD.-"));

    SequenceModificationSitePair another = new SequenceModificationSitePair(
        "STY", "K.KT*EESEES*EDDMGFGLFD.-");

    current.mergeWithSurePeptide(another);
    assertEquals(2, current.getModifiedCount());
    assertEquals("KT*EESEES*EDDMGFGLFD", current.getModifiedSequence());
  }

  public void testToString() {
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.GLEAEATY*PYEGKDGPCR.Y");
    assertEquals("GLEAEATY*PYEGKDGPCR", current.getModifiedSequence());
  }

  public void testComplex() {
    SequenceModificationSitePair current = new SequenceModificationSitePair(
        "STY", "K.KEESEES@DDDMGFGLFD.-");

    current.mergeWithSurePeptide(new SequenceModificationSitePair("STY",
        "K.KEESEES*DDDMGFGLFD.-"));
    assertEquals("KEESEES&DDDMGFGLFD", current.getModifiedSequence());

    current.mergeWithSurePeptide(new SequenceModificationSitePair("STY",
        "K.KEES*EESDDDMGFGLFD.-"));
    assertEquals("KEES*EES&DDDMGFGLFD", current.getModifiedSequence());

    SequenceModificationSitePair other = new SequenceModificationSitePair(
        "STY", "K.KEES*EES*DDDM#GFGLFD.-");
    other.mergeWithSurePeptide(new SequenceModificationSitePair("STY",
        "K.KEES*EES@DDDMGFGLFD.-"));
    assertEquals("KEES*EES&DDDMGFGLFD", other.getModifiedSequence());

    current.mergeWithSurePeptide(other);
    assertEquals("KEES*EES&DDDMGFGLFD", current.getModifiedSequence());

    current.mergeWithSurePeptide(new SequenceModificationSitePair("STY",
        "K.EESEES*DDDM#GFGLFD.-"));
    assertEquals("KEES*EES&DDDMGFGLFD", current.getModifiedSequence());
  }

  public void testMergeWithSurePeptideInDiffenentSite()
      throws SequestParseException {
    // SpASpSpDTpSpEELNSQDSPK + SASSDTSEELNSQDS*PK -> SpASpSpDTpSpEELNSQDS*PK
    SameModificationPeptides peptide = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218) ! R.SASSDTS*EELNSQDSPK.R(2.7904,0.0608) ! R.SASSDT*SEELNSQDSPK.R(2.7302,0.0811)");
    SequenceModificationSitePair current = peptide.getSequence();

    assertEquals("SpASpSpDTpSpEELNSQDSPK", current.getModifiedSequence());

    SequenceModificationSitePair other = new SequenceModificationSitePair(
        "STY", "R.SASSDTSEELNSQDS*PK.R");
    current.mergeWithSurePeptide(other);

    assertEquals(2, current.getModifiedCount());
    assertEquals(1, current.getTrueModifiedCount());
    assertEquals("SpASpSpDTpSpEELNSQDS*PK", current.getModifiedSequence());
  }

  public void testMergeWithSurePeptideInDiffenentSite2()
      throws SequestParseException {
    // SpASpSpDTpSpEELNSQDSPK + SpASpSDTSEELNSQDSPK -> SpASpSDTSEELNSQDSPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218) ! R.SASSDTS*EELNSQDSPK.R(2.7904,0.0608) ! R.SASSDT*SEELNSQDSPK.R(2.7302,0.0811)")
        .getSequence();

    SequenceModificationSitePair other = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.SAS*SDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.S*ASSDTSEELNSQDSPK.R(4.0774,0.0102)")
        .getSequence();

    current.mergeWithSurePeptide(other);

    assertEquals(1, current.getModifiedCount());
    assertEquals(0, current.getTrueModifiedCount());
    assertEquals("SpASpSDTSEELNSQDSPK", current.getModifiedSequence());

    SequenceModificationSitePair current2 = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218) ! R.SASSDTS*EELNSQDSPK.R(2.7904,0.0608) ! R.SASSDT*SEELNSQDSPK.R(2.7302,0.0811)")
        .getSequence();

    other.mergeWithSurePeptide(current2);
    assertEquals(1, other.getModifiedCount());
    assertEquals(0, other.getTrueModifiedCount());
    assertEquals("SpASpSDTSEELNSQDSPK", other.getModifiedSequence());
  }

  public void testMergeWithShorterSequence() throws SequestParseException {
    // EEVAS*EPEEAASpPTpTPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_25_050906,11650	K.EEVAS*EPEEAAS*PTTPK.K	1932.78000	0.41812	2	1	3.5533	0.3247	589.1	1	26|64	IPI:IPI00318048.4|SWISS-PROT:Q9D6Z1|REFSEQ_NP:NP_077155|ENSEMBL:ENSMUSP00000028/IPI:IPI00473297.1|ENSEMBL:ENSMUSP0	3.98	K.EEVAS*EPEEAASPT*TPK.K(3.3650,0.0530)")
        .getSequence();

    // EEVAS@EPEEAAS*PTTPK
    SequenceModificationSitePair another = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_30_050906,11586	K.EEVAS@EPEEAAS*PTTPK.K	1834.81000	-0.50321	2	1	3.9960	0.4818	1171.0	1	28|48	IPI:IPI00318048.4|SWISS-PROT:Q9D6Z1|REFSEQ_NP:NP_077155|ENSEMBL:ENSMUSP00000028/IPI:IPI00473297.1|ENSEMBL:ENSMUSP0	3.98	")
        .getSequence();

    current.mergeTrueModificationSiteWithShorterSequence(another);
    assertEquals("EEVAS&EPEEAAS*PTTPK", current.getModifiedSequence());
  }

  public void testMergeWithLongerSequence() throws SequestParseException {
    // EEVAS*EPEEAASpPTpTPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_25_050906,11650	E.EVAS*EPEEAAS*PTTPK.K	1932.78000	0.41812	2	1	3.5533	0.3247	589.1	1	26|64	IPI:IPI00318048.4|SWISS-PROT:Q9D6Z1|REFSEQ_NP:NP_077155|ENSEMBL:ENSMUSP00000028/IPI:IPI00473297.1|ENSEMBL:ENSMUSP0	3.98	E.EVAS*EPEEAASPT*TPK.K(3.3650,0.0530)")
        .getSequence();

    // EEVAS@EPEEAAS*PTTPK
    SequenceModificationSitePair another = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_30_050906,11586	K.EEVAS@EPEEAAS*PTTPK.K	1834.81000	-0.50321	2	1	3.9960	0.4818	1171.0	1	28|48	IPI:IPI00318048.4|SWISS-PROT:Q9D6Z1|REFSEQ_NP:NP_077155|ENSEMBL:ENSMUSP00000028/IPI:IPI00473297.1|ENSEMBL:ENSMUSP0	3.98	")
        .getSequence();

    current.mergeTrueModificationSiteWithLongerSequence(another);
    assertEquals("EEVAS&EPEEAAS*PTTPK", current.getModifiedSequence());
  }

  public void testMergeTrueModificationSite() throws SequestParseException {
    // EEVAS*EPEEAASpPTpTPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_25_050906,11650	K.EEVAS*EPEEAAS*PTTPK.K	1932.78000	0.41812	2	1	3.5533	0.3247	589.1	1	26|64	IPI:IPI00318048.4|SWISS-PROT:Q9D6Z1|REFSEQ_NP:NP_077155|ENSEMBL:ENSMUSP00000028/IPI:IPI00473297.1|ENSEMBL:ENSMUSP0	3.98	K.EEVAS*EPEEAASPT*TPK.K(3.3650,0.0530)")
        .getSequence();

    // EEVAS@EPEEAAS*PTTPK
    SequenceModificationSitePair another = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "			JWH_SAX_30_050906,11586	K.EEVAS@EPEEAAS*PTTPK.K	1834.81000	-0.50321	2	1	3.9960	0.4818	1171.0	1	28|48	IPI:IPI00318048.4|SWISS-PROT:Q9D6Z1|REFSEQ_NP:NP_077155|ENSEMBL:ENSMUSP00000028/IPI:IPI00473297.1|ENSEMBL:ENSMUSP0	3.98	")
        .getSequence();

    current.mergeTrueModificationSite(another);
    assertEquals("EEVAS&EPEEAAS*PTTPK", current.getModifiedSequence());
  }

  public void testContainAllAmbigiousSite() throws SequestParseException {
    // SpASpSpDTpSpEELNSQDSPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218) ! R.SASSDTS*EELNSQDSPK.R(2.7904,0.0608) ! R.SASSDT*SEELNSQDSPK.R(2.7302,0.0811)")
        .getSequence();

    // SpASpSDTSEELNSQDSPK
    SequenceModificationSitePair other = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.SAS*SDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.S*ASSDTSEELNSQDSPK.R(4.0774,0.0102)")
        .getSequence();

    assertTrue(current.getModifiedSequence() + " : " + other.toString(),
        current.containAllAmbiguousSite(other));

    // SpASSDTSEELNSQDSpPK
    SequenceModificationSitePair other2 = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.SAS*SDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.SASSDTSEELNSQDS*PK.R(4.0774,0.0102)")
        .getSequence();

    assertFalse(current.getModifiedSequence() + " : " + other2.toString(),
        current.containAllAmbiguousSite(other2));
  }

  public void testAllAmbigiousSiteContainedIn() throws SequestParseException {
    // SpASpSpDTpSpEELNSQDSPK + SpASpSDTSEELNSQDSPK -> SpASpSDTSEELNSQDSPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218) ! R.SASSDTS*EELNSQDSPK.R(2.7904,0.0608) ! R.SASSDT*SEELNSQDSPK.R(2.7302,0.0811)")
        .getSequence();

    SequenceModificationSitePair other = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.SAS*SDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.S*ASSDTSEELNSQDSPK.R(4.0774,0.0102)")
        .getSequence();

    assertTrue(other.toString() + " : " + current.getModifiedSequence(), other
        .allAmbiguousSiteContainedIn(current));

    // SpASSDTSEELNSQDSpPK
    SequenceModificationSitePair other2 = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.SAS*SDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.SASSDTSEELNSQDS*PK.R(4.0774,0.0102)")
        .getSequence();

    assertFalse(other2.toString() + " : " + current.getModifiedSequence(),
        other2.allAmbiguousSiteContainedIn(current));
  }

  public void testReplaceAmbigiousSite() throws SequestParseException {
    // SpASpSpDTpSpEELNSQDSPK + SpASpSDTSEELNSQDSPK -> SpASpSDTSEELNSQDSPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218) ! R.SASSDTS*EELNSQDSPK.R(2.7904,0.0608) ! R.SASSDT*SEELNSQDSPK.R(2.7302,0.0811)")
        .getSequence();

    SequenceModificationSitePair other = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.SAS*SDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.S*ASSDTSEELNSQDSPK.R(4.0774,0.0102)")
        .getSequence();

    current.replaceAmbiguousSite(other);

    assertEquals("SpASpSDTSEELNSQDSPK", current.getModifiedSequence());
  }

  public void testMergeAmbigiousSites() throws SequestParseException {
    // SpASpSpDTSEELNSQDSPK + SpASSDTSEELNSQDSpPK -> SpASpSpDTSEELNSQDSpPK
    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_45_050906,11004	R.SASS*DTSEELNSQDSPK.R	1862.74000	1.39560	2	1	2.9711	0.2420	388.9	4	14|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|E	3.92	R.SAS*SDTSEELNSQDSPK.R(2.9248,0.0156) ! R.S*ASSDTSEELNSQDSPK.R(2.9065,0.0218)")
        .getSequence();

    SequenceModificationSitePair other = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11703	R.S*ASSDTSEELNSQDSPK.R	1862.74000	0.01560	2	1	4.1194	0.5363	861.0	1	20|32	IPI:IPI00109311.1|SWISS-PROT:P70441|TREMBL:Q8BYD8;Q9R1A1|REFSEQ_NP:NP_036160|EN	3.92	R.SASSDTSEELNSQDS*PK.R(4.0774,0.0102)")
        .getSequence();

    current.mergeAmbiguousSites(other);

    assertEquals("SpASpSpDTSEELNSQDSpPK", current.getModifiedSequence());
  }

  public void testMergeDifferentModifiedCount() throws SequestParseException {
    // SpASpSpDTSEELNSQDSPK + SpASSDTSEELNSQDSpPK -> SpASpSpDTSEELNSQDSpPK
    SequenceModificationSitePair other = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_30_050906,11220	R.DHSS*QS*EEEVVEGEK.E	1849.61000	0.32907	2	1	4.3762	0.4071	381.0	1	27|56	IPI:IPI00131125.1|SWISS-PROT:Q9Z2F7|TREMBL:Q91Z78|REFSEQ_NP:NP_033891|ENSEMBL:E	4.14	R.DHS*SQS*EEEVVEGEK.E(4.3392,0.0084)")
        .getSequence();
    assertEquals("DHSpSpQS*EEEVVEGEK", other.getModifiedSequence());

    SequenceModificationSitePair current = SameModificationPeptides
        .parseFromBuildSummaryPeptideHit(
            modifiedAminoacids,
            "JWH_SAX_4_050906,11910	R.DHSS*QSEEEVVEGEK.E	1769.65000	0.60340	2	1	3.6933	0.4103	585.6	1	20|28	IPI:IPI00131125.1|SWISS-PROT:Q9Z2F7|TREMBL:Q91Z78|REFSEQ_NP:NP_033891|ENSEMBL:E	4.14	R.DHSSQS*EEEVVEGEK.E(3.3405,0.0955)")
        .getSequence();
    assertEquals("DHSSpQSpEEEVVEGEK", current.getModifiedSequence());

    current.mergeWithSurePeptide(other);

    assertEquals("DHSpSpQS*EEEVVEGEK", current.getModifiedSequence());
    assertEquals(2, current.getModifiedCount());
  }

}
