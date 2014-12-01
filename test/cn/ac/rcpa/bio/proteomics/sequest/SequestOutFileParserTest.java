package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class SequestOutFileParserTest extends TestCase {

	/*
	 * Test method for 'utils.SequestOutFileParser.readFirstSequestQueryFromFile(String, boolean)'
	 */
	public void testReadFirstSequestQueryFromFile() throws IOException {

		 SequestOutFileParser parser = new  SequestOutFileParser(0.1);
		 BuildSummaryPeptideHit phit = parser.readFirstSequestQueryFromFile("data/JWH_SAX_30_050906.10042.10042.2.out", false);
		 assertEquals("K.SKPAAADS*EGEEEEEDTAK.E",phit.getPeptide(0).getSequence());
		 assertEquals(5.5571, phit.getPeptide(0).getXcorr(), 0.0001);
		 assertEquals(0.4156, phit.getPeptide(0).getDeltacn(), 0.0001);
	}

}
