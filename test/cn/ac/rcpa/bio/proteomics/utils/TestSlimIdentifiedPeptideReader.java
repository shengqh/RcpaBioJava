package cn.ac.rcpa.bio.proteomics.utils;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestSlimIdentifiedPeptideReader extends TestCase {
	public void testRead() throws Exception, IOException {
		List<SlimIdentifiedPeptide> peptides = new SlimIdentifiedPeptideReader(
				new String[] { "XC", "DeltaCn" })
				.read("data/BuildSummaryPeptideEnzymeFileTest.peptides");
		Assert.assertEquals(3, peptides.size());
		Assert.assertEquals(2, peptides.get(0).getScoreMap().size());
		Assert.assertEquals("K.AADTIGYPVMIR.S", peptides.get(0).getSequence());
		Assert.assertEquals(2, peptides.get(0).getCharge());
		Assert.assertEquals("HLPP_Salt_Step_7,10696", peptides.get(0)
				.getFileName().getShortFilename());
		Assert.assertEquals(2.4226, Double.parseDouble(peptides.get(0)
				.getScoreMap().get("XC")), 0.0001);
		Assert.assertEquals(0.3671, Double.parseDouble(peptides.get(0)
				.getScoreMap().get("DeltaCn")), 0.0001);
	}
}