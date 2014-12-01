package cn.ac.rcpa.bio.proteomics.modification;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestSameModificationPeptide
    extends TestCase {
  private static final String modifiedAminoacids = "STY";

  public void testGetSequence() throws SequestParseException {
    String line = "JWH_SAX_30_050906,22746	K.KT*EESEES*EDDMGFGLFD.-	2044.96000	-0.74575	2	1	3.8037	0.4133	807.0	1	25|48	IPI:IPI00113377.1|SWISS-PROT:P47955|REFSEQ_NP:NP_061341|ENSEMBL:ENSMUSP00000008/IPI:IPI00118632.1|ENSEMBL:ENSMUSP0/IPI:IPI00134920.1|ENSEMBL:ENSMUSP0	3.76	K.KT*EES*EESEDDMGFGLFD.-(3.5470,0.0675)";

    SameModificationPeptides peptide = SameModificationPeptides.
        parseFromBuildSummaryPeptideHit(modifiedAminoacids, line);

    assertEquals("KT*EESpEESpEDDMGFGLFD", peptide.getSequence().toString());
    assertEquals(2, peptide.getSequence().getModifiedCount());
    assertEquals(1, peptide.getSequence().getTrueModifiedCount());
    assertEquals(2, peptide.getSequence().getAmbiguousModifiedCount());
  }
}
