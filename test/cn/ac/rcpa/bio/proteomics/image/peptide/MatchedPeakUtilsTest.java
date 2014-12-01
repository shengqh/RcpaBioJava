package cn.ac.rcpa.bio.proteomics.image.peptide;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class MatchedPeakUtilsTest extends TestCase {

  /*
   * Test method for 'cn.ac.rcpa.msms.image.MatchedPeakUtils.unmatchAll(Collection<? extends MatchedPeak>)'
   */
  public void testUnmatchAll() {
    List<MatchedPeak> peaks= new ArrayList<MatchedPeak>();
    peaks.add(new MatchedPeak(1.0,1.0,1));
    peaks.add(new MatchedPeak(2.0,2.0,1));
    for(MatchedPeak peak:peaks){
      peak.setMatched(true);
    }
    MatchedPeakUtils.unmatchAll(peaks);
    assertFalse(peaks.get(0).isMatched());
    assertFalse(peaks.get(1).isMatched());
  }

}
