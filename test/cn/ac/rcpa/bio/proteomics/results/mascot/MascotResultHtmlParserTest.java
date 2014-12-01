package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.File;

import junit.framework.TestCase;

public class MascotResultHtmlParserTest extends TestCase {

	/*
	 * Test method for
	 * 'cn.ac.rcpa.bio.proteomics.results.mascot.MascotResultHtmlParser.parse(File)'
	 */
	public void testParseFile() throws Exception {
		MascotResult mr_old = new MascotResultHtmlParser(true).parseFile(new File(
				"data/mascot_result_protein_oldversion.html"));
		assertEquals(1, mr_old.size());
		assertEquals(2, mr_old.get(0).get(0).getPeptideCount());

		MascotResult mr_new = new MascotResultHtmlParser(true).parseFile(new File(
				"data/mascot_result_protein_newversion.html"));
		assertEquals(2, mr_new.size());
		assertEquals(3, mr_new.get(0).get(0).getPeptideCount());
		assertEquals(2, mr_new.get(1).get(0).getPeptideCount());
	}
}
