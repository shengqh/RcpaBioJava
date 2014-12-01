package cn.ac.rcpa.bio.proteomics.utils;

import java.util.ArrayList;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideHitReader;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestIdentifiedPeptideHitUtils extends TestCase {
    public void testSortByAmbigiousLevel() throws SequestParseException {
      ArrayList<BuildSummaryPeptideHit> peps = new ArrayList<
          BuildSummaryPeptideHit> ();
      peps.add(BuildSummaryPeptideHitReader.parse("JWH_SAX_2_050906,23429	K.KEES*EESDDDMGFGLFD.-	2030.93000	-0.49842	2	1	5.1839	0.5662	1398.2	1	29|48	IPI:IPI00112342.1|ENSEMBL:ENSMUSP00000026581/IPI:IPI00139795.2|SWISS-PROT:P9902/IPI:IPI00380337.1|TREMBL:Q7TNQ6|RE	3.71	K.KEESEES*DDDMGFGLFD.-(4.7082,0.0918)"));
      peps.add(BuildSummaryPeptideHitReader.parse("JWH_SAX_2_050906,22827	K.KEES@EESDDDMGFGLFD.-	1932.97000	1.35025	2	1	4.0001	0.2248	683.1	1	20|32	IPI:IPI00112342.1|ENSEMBL:ENSMUSP00000026581/IPI:IPI00139795.2|SWISS-PROT:P9902/IPI:IPI00380337.1|TREMBL:Q7TNQ6|RE	3.71	K.KEESEES@DDDMGFGLFD.-(3.9003,0.0250)"));
      peps.add(BuildSummaryPeptideHitReader.parse("JWH_SAX_25_050906,20273	K.KEES*EESDDDM#GFGLFD.-	2046.93000	0.23249	2	1	4.6478	0.6015	1144.0	1	29|48	IPI:IPI00112342.1|ENSEMBL:ENSMUSP00000026581/IPI:IPI00139795.2|SWISS-PROT:P9902/IPI:IPI00380337.1|TREMBL:Q7TNQ6|RE	3.71	"));

      IdentifiedPeptideHitUtils.sortByCandidatePeptideCountAscending(peps);
      assertEquals("K.KEES*EESDDDM#GFGLFD.-",
                   peps.get(0).getPeptide(0).getSequence());
    }

}
