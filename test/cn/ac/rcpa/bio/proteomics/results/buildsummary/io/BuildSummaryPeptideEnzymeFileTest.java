package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.biojava.bio.proteomics.Protease;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class BuildSummaryPeptideEnzymeFileTest extends TestCase {

	/*
	 * Test method for
	 * 'cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideEnzymeFile.read(String)'
	 */
	public void testRead() throws IOException {
		Map<String, Protease> map = BuildSummaryPeptideEnzymeFile
				.read("data/BuildSummaryPeptideEnzymeFileTest.peptides.enzyme");
		assertEquals(1, map.size());
		Protease protease = map
				.get(BuildSummaryPeptideEnzymeFile.DEFAULT_ENZYME_KEY);
		assertEquals(true, protease.isEndoProtease());
		assertEquals("KR", protease.getCleaveageResidues().seqString());
		assertEquals("", protease.getNotCleaveResidues().seqString());
	}

	/*
	 * Test method for
	 * 'cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideEnzymeFile.fill(String,
	 * List<BuildSummaryPeptideHit>)'
	 */
	public void testFill() throws Exception {
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read("data/BuildSummaryPeptideEnzymeFileTest.peptides");
		for (BuildSummaryPeptideHit pephit : pephits) {
			Protease protease = pephit.getProtease();
			assertEquals(true, protease.isEndoProtease());
			assertEquals("KR", protease.getCleaveageResidues().seqString());
			assertEquals("", protease.getNotCleaveResidues().seqString());
		}
	}

}
