package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import junit.framework.TestCase;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestBuildSummaryPeptide extends TestCase {
  public void testParseMultiProteins() {
    String outFileLine = "  1.   1 / 19      15861 1786.8390  0.0000  0.8720   114.7  10/64  gi|5541865|emb|CAB51072.1|       +3   -.EARGNSSETSHSVPEAK.-";
    BuildSummaryPeptide pt = BuildSummaryPeptide.parse(outFileLine);
    assertTrue(pt.getRank() == 1);
    assertTrue(pt.getSpRank() == 19);
    assertTrue(pt.getTheoreticalSingleChargeMass() == 1786.8390);
    assertTrue(pt.getDeltacn() == 0.0000);
    assertTrue(pt.getXcorr() == 0.8720);
    assertTrue(pt.getSp() == 114.7);
    assertTrue(pt.getMatchCount() == 10);
    assertTrue(pt.getTheoreticalCount() == 64);
    assertTrue(pt.getProteinName(0).equals("gi|5541865|emb|CAB51072.1|"));
    assertTrue(pt.getDuplicateRefCount() == 3);
    assertTrue(pt.getSequence().equals("-.EARGNSSETSHSVPEAK.-"));
  }

  public void testParseSingleProtein() {
    String outFileLine2 = "  1.   1 / 19      15861 1786.8390  0.0000  0.8720   114.7  10/64  gi|5541865|emb|CAB51072.1|            -.EARGNSSETSHSVPEAK.-";
    BuildSummaryPeptide pt = BuildSummaryPeptide.parse(outFileLine2);
    assertTrue(pt.getRank() == 1);
    assertTrue(pt.getSpRank() == 19);
    assertTrue(pt.getTheoreticalSingleChargeMass() == 1786.8390);
    assertTrue(pt.getDeltacn() == 0.0000);
    assertTrue(pt.getXcorr() == 0.8720);
    assertTrue(pt.getSp() == 114.7);
    assertTrue(pt.getMatchCount() == 10);
    assertTrue(pt.getTheoreticalCount() == 64);
    assertTrue(pt.getProteinName(0).equals("gi|5541865|emb|CAB51072.1|"));
    assertTrue(pt.getDuplicateRefCount() == 0);
    assertTrue(pt.getSequence().equals("-.EARGNSSETSHSVPEAK.-"));
  }
}
