package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.regex.Matcher;

import junit.framework.TestCase;

public class SequestPeptideResultTest extends TestCase {
	private SequestPeptideResult sr;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		sr = new SequestPeptideResult("K.LY*CS*ECDKCFTK.K");
		sr
				.parseModification("(STY* +79.96633) (M# +15.99492) (ST@ -18.00000) C=160.16523  Enzyme:Trypsin(KR) (1)");
	}

	@Override
	public void tearDown() throws Exception {
		sr = null;
		super.tearDown();
	}

	public void testMassTypePattern() {
		Matcher matcher = SequestPeptideResult.massTypePattern
				.matcher(" (M+H)+ mass = 536.2354 ~ 1.5000 (+1), fragment tol = 0.0, AVG/MONO");
		assertTrue(matcher.find());
		assertEquals("AVG", matcher.group(1));
		assertEquals("MONO", matcher.group(2));
	}

	public void testParseModification() {
		assertEquals(79.96633, sr.getDynamicModification().get('*'));
		assertEquals(15.99492, sr.getDynamicModification().get('#'));
		assertEquals(-18.00000, sr.getDynamicModification().get('@'));
		assertEquals(160.16523, sr.getStaticModification().get('C'));
	}

	public void testParseOutFile() throws Exception {
		SequestPeptideResult spr = new SequestPeptideResult(
				"R.ESQGQVGGLEREXRGVQDGR.H");
		spr.parseFromFile("data/HPPP_Flow_Salt_Step_04.13341.13341.2.dta",
				"data/HPPP_Flow_Salt_Step_04.13341.13341.2.out");
		//assertEquals(2170.3900, spr.getPrecursorMass(), 3.1);
	}
}
