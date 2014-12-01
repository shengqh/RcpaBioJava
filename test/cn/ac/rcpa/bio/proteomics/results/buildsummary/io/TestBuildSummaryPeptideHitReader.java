package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

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
 * @author not attributable
 * @version 1.0
 */

public class TestBuildSummaryPeptideHitReader extends TestCase {
	public void testRead() throws Exception {
		String filename = "data/HIPP2.peptides";
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read(filename);
		assertEquals(2490, pephits.size());
		assertEquals(null, pephits.get(0).getProtease());
	}

	public void testGetPeptidePattern() {
		Pattern p = BuildSummaryPeptideHitReader.getPeptidePattern();
		assertTrue(p
				.matcher(
						"	MDI_0min_SAX_FT_090115_9,6830	-.GEPSCSSVHIAAR.T	1370.64816	0.00516	2	1	2.2615	0.137	337.8	1	13|24	IPI00876399		6.74	0")
				.find());
		assertTrue(p
				.matcher(
						"	\"MDI_0min_SAX_FT_090115_9,6830\"	-.GEPSCSSVHIAAR.T	1370.64816	0.00516	2	1	2.2615	0.137	337.8	1	13|24	IPI00876399		6.74	0")
				.find());
		assertTrue(p
				.matcher(
						"	MDI_0min_SAX_FT_090115_9,6830 - 6831	-.GEPSCSSVHIAAR.T	1370.64816	0.00516	2	1	2.2615	0.137	337.8	1	13|24	IPI00876399		6.74	0")
				.find());
		assertTrue(p
				.matcher(
						"	\"MDI_0min_SAX_FT_090115_9,6830 - 6831\"	-.GEPSCSSVHIAAR.T	1370.64816	0.00516	2	1	2.2615	0.137	337.8	1	13|24	IPI00876399		6.74	0")
				.find());
	}

	public void testParse() throws Exception {
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read("data/TestFollowCandidates.peptides");
		assertEquals(3, pephits.size());

		assertEquals(2, pephits.get(0).getFollowCandidates().size());
		assertEquals("K.GLEAEAT*YPYEGKDGPCR.Y", pephits.get(0)
				.getFollowCandidates().get(0).getSequence());
		assertEquals(2.6670, pephits.get(0).getFollowCandidates().get(0)
				.getScore());
		assertEquals(0.0154, pephits.get(0).getFollowCandidates().get(0)
				.getDeltaScore());
		assertEquals("K.GLEAEATYPY*EGKDGPCR.Y", pephits.get(0)
				.getFollowCandidates().get(1).getSequence());
		assertEquals(2.5464, pephits.get(0).getFollowCandidates().get(1)
				.getScore());
		assertEquals(0.0599, pephits.get(0).getFollowCandidates().get(1)
				.getDeltaScore());

		assertEquals(1, pephits.get(1).getFollowCandidates().size());
		assertEquals("-.M#ITIPLTNEVVS*MLSQNK.S", pephits.get(1)
				.getFollowCandidates().get(0).getSequence());
		assertEquals(2.7855, pephits.get(1).getFollowCandidates().get(0)
				.getScore());
		assertEquals(0.0888, pephits.get(1).getFollowCandidates().get(0)
				.getDeltaScore());

		assertEquals(0, pephits.get(2).getFollowCandidates().size());
	}

}
