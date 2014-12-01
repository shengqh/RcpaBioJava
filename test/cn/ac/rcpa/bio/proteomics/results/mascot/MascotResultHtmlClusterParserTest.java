package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.File;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.IsotopicType;

public class MascotResultHtmlClusterParserTest extends TestCase {
	public void testProteinInfoPattern() throws Exception {
	}

	public void testParse() throws Exception {
		MascotResult mr = new MascotResultHtmlClusterParser(true).parseFile(new File(
				"data/mascot_result_protein_oldversion.html"));
		assertEquals(28, mr.getPValueScore());
		assertEquals(0.05, mr.getPValue());
		assertEquals(1, mr.size());
		assertEquals(IsotopicType.Monoisotopic, mr.getPeakIsotopicType());
		assertEquals(0.8, mr.getPeakTolerance());

		MascotProtein protein1 = mr.get(0).get(0);
		assertEquals("sp|P02769|ALBU_BOVIN", protein1.getProteinName());
		assertEquals(71244, protein1.getMW(), 1.0);
		assertEquals(
				"Serum albumin precursor (Allergen Bos d 6) - Bos taurus (Bovine).",
				protein1.getReference());

		assertEquals(2, protein1.getPeptideCount());

		MascotPeptide pep11 = protein1.getPeptide(0);
		assertEquals(370.98, pep11.getObservedMz(), 0.0001);
		assertEquals("Mix_F_101606_LM03.293.303.1.", pep11.getPeakListInfo().getLongFilename());
		assertEquals(43, pep11.getQuery());
		assertEquals(370.98, pep11.getExperimentalSingleChargeMass(), 0.01);
		assertEquals(373.17, pep11.getTheoreticalSingleChargeMass(), 0.01);
		assertEquals(2.19, pep11.getDiffToExperimentMass(), 0.01);
		assertEquals(0, pep11.getMissCleavage());
		assertEquals(36, pep11.getScore());
		assertEquals(0.0, pep11.getPValue());
		assertEquals(1, pep11.getRank());
		assertEquals("QEP", pep11.getSequence());

		MascotPeptide pep12 = protein1.getPeptide(1);
		assertEquals(345.40, pep12.getObservedMz(), 0.01);
		assertEquals("Mix_F_101606_LM03.599.602.2.", pep12.getPeakListInfo().getLongFilename());
		assertEquals(237, pep12.getQuery());
		assertEquals(689.79, pep12.getExperimentalSingleChargeMass(), 0.01);
		assertEquals(689.38, pep12.getTheoreticalSingleChargeMass(), 0.01);
		assertEquals(-0.42, pep12.getDiffToExperimentMass(), 0.01);
		assertEquals(0, pep12.getMissCleavage());
		assertEquals(41, pep12.getScore());
		assertEquals(0.0, pep12.getPValue(), 0.001);
		assertEquals(1, pep12.getRank());
		assertEquals("AWSVAR", pep12.getSequence());

	}
}
