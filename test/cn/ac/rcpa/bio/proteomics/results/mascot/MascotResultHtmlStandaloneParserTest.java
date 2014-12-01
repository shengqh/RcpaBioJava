package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.File;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.IsotopicType;

public class MascotResultHtmlStandaloneParserTest extends TestCase {
	public void testParseFile1() throws Exception {
		MascotResult mr = new MascotResultHtmlStandaloneParser(true)
				.parseFile(new File("data/mascot_result_protein_newversion.html"));

		assertEquals(33, mr.getPValueScore());
		assertEquals(0.05, mr.getPValue());

		assertEquals(IsotopicType.Monoisotopic, mr.getPeakIsotopicType());
		assertEquals(0.8, mr.getPeakTolerance(), 0.01);

		assertEquals(2, mr.size());

		MascotProtein protein1 = mr.get(0).get(0);
		assertEquals("sp|P00489|PHS2_RABIT", protein1.getProteinName());
		assertEquals(97610, protein1.getMW(), 1.0);
		assertEquals(
				"Glycogen phosphorylase, muscle form (EC 2.4.1.1) (Myophosphorylase) - Oryctolagus cuniculus (Rabbit",
				protein1.getReference());

		assertEquals(3, protein1.getPeptideCount());

		MascotPeptide pep11 = protein1.getPeptide(0);
		assertEquals(1053.5720, pep11.getObservedMz(), 0.0001);
		assertEquals("Cmpd.6612.6612.1.", pep11.getPeakListInfo().getLongFilename());
		assertEquals(2232, pep11.getQuery());
		assertEquals(1053.5720, pep11.getExperimentalSingleChargeMass(), 0.0001);
		assertEquals(1053.5727, pep11.getTheoreticalSingleChargeMass(), 0.0001);
		assertEquals(0.0007, pep11.getDiffToExperimentMass(), 0.0001);
		assertEquals(0, pep11.getMissCleavage());
		assertEquals(34, pep11.getScore());
		assertEquals(0.039, pep11.getPValue());
		assertEquals(1, pep11.getRank());
		assertEquals("R.VIFLENYR.V", pep11.getSequence());

		MascotPeptide pep12 = protein1.getPeptide(1);
		assertEquals(527.7459, pep12.getObservedMz(), 0.0001);
		assertEquals("Cmpd.5747.5747.2.", pep12.getPeakListInfo().getLongFilename());
		assertEquals(2239, pep12.getQuery());
		assertEquals(1054.4845, pep12.getExperimentalSingleChargeMass(), 0.0001);
		assertEquals(1054.4840, pep12.getTheoreticalSingleChargeMass(), 0.0001);
		assertEquals(-0.0005, pep12.getDiffToExperimentMass(), 0.0001);
		assertEquals(0, pep12.getMissCleavage());
		assertEquals(48, pep12.getScore());
		assertEquals(5.4E-5, pep12.getPValue(), 0.001);
		assertEquals(1, pep12.getRank());
		assertEquals("R.TNFDAFPDK.V", pep12.getSequence());

		MascotPeptide pep13 = protein1.getPeptide(2);
		assertEquals(527.7464, pep13.getObservedMz(), 0.0001);
		assertEquals("Cmpd.5743.5743.2.", pep13.getPeakListInfo().getLongFilename());
		assertEquals(2240, pep13.getQuery());
		assertEquals(1054.4855, pep13.getExperimentalSingleChargeMass(), 0.0001);
		assertEquals(1054.4840, pep13.getTheoreticalSingleChargeMass(), 0.0001);
		assertEquals(-0.0015, pep13.getDiffToExperimentMass(), 0.0001);
		assertEquals(0, pep13.getMissCleavage());
		assertEquals(47, pep13.getScore());
		assertEquals(0.0018, pep13.getPValue(), 0.001);
		assertEquals(1, pep13.getRank());
		assertEquals("R.TNFDAFPDK.V", pep13.getSequence());

		MascotProtein protein2 = mr.get(1).get(0);
		assertEquals("REVERSED_0008631", protein2.getProteinName());
		assertEquals("", protein2.getReference());

		assertEquals(2, protein2.getPeptideCount());

		MascotPeptide pep21 = protein2.getPeptide(0);
		assertEquals(699.4770, pep21.getObservedMz(), 0.0001);
		assertEquals("Standard_Protein_FIT_060222.6065.6065.1.", pep21
				.getPeakListInfo().getLongFilename());
		assertEquals(932, pep21.getQuery());
		assertEquals(699.4770, pep21.getExperimentalSingleChargeMass(), 0.0001);
		assertEquals(699.4188, pep21.getTheoreticalSingleChargeMass(), 0.0001);
		assertEquals(-0.0582, pep21.getDiffToExperimentMass(), 0.0001);
		assertEquals(0, pep21.getMissCleavage());
		assertEquals(33, pep21.getScore());
		assertEquals(0.049, pep21.getPValue());
		assertEquals(1, pep21.getRank());
		assertEquals("R.WPAVVK.L", pep21.getSequence());

		assertSame(protein1.getPeptide(2), protein2.getPeptide(1));
	}

	public void testParseFile2() throws Exception {
		MascotResult mr = new MascotResultHtmlStandaloneParser(true)
				.parseFile(new File("data/mascot_result_Caseins00001.htm"));

		assertEquals(44, mr.getPValueScore());
		assertEquals(0.05, mr.getPValue());

		assertEquals(IsotopicType.Monoisotopic, mr.getPeakIsotopicType());
		assertEquals(0.8, mr.getPeakTolerance(), 0.01);

		assertEquals(1, mr.size());

		MascotProtein protein1 = mr.get(0).get(0);
		assertEquals("ALBU_HUMAN", protein1.getProteinName());
		assertEquals("Serum albumin precursor - Homo sapiens (Human)", protein1
				.getReference());

		assertEquals(2, protein1.getPeptideCount());

		MascotPeptide pep11 = protein1.getPeptide(0);
		assertEquals(547.22, pep11.getObservedMz(), 0.01);
		assertEquals("Cmpd.51.51.3.", pep11.getPeakListInfo().getLongFilename());
		assertEquals(118, pep11.getQuery());
		assertEquals(1639.6454, pep11.getExperimentalSingleChargeMass(), 0.0001);
		assertEquals(1639.9377, pep11.getTheoreticalSingleChargeMass(), 0.0001);
		assertEquals(0.2922, pep11.getDiffToExperimentMass(), 0.0001);
		assertEquals(1, pep11.getMissCleavage());
		assertEquals(50, pep11.getScore());
		assertEquals(0.0091, pep11.getPValue(), 0.0001);
		assertEquals(1, pep11.getRank());
		assertEquals("K.KVPQVSTPTLVEVSR.N", pep11.getSequence());

		MascotPeptide pep12 = protein1.getPeptide(1);
		assertEquals(547.23, pep12.getObservedMz(), 0.01);
		assertEquals("Cmpd.47.47.3.", pep12.getPeakListInfo().getLongFilename());
		assertEquals(119, pep12.getQuery());
		assertEquals(1639.6754, pep12.getExperimentalSingleChargeMass(), 0.0001);
		assertEquals(1639.9377, pep12.getTheoreticalSingleChargeMass(), 0.0001);
		assertEquals(0.2622, pep12.getDiffToExperimentMass(), 0.0001);
		assertEquals(1, pep12.getMissCleavage());
		assertEquals(52, pep12.getScore());
		assertEquals(0.0062, pep12.getPValue(), 0.0001);
		assertEquals(1, pep12.getRank());
		assertEquals("K.KVPQVSTPTLVEVSR.N", pep12.getSequence());
	}

	public void testParseFileWithSameMatchProteins() throws Exception {
		MascotResult mr = new MascotResultHtmlStandaloneParser(false)
				.parseFile(new File("data/mascot_result_Caseins00001.htm"));

		assertEquals(44, mr.getPValueScore());
		assertEquals(0.05, mr.getPValue());

		assertEquals(IsotopicType.Monoisotopic, mr.getPeakIsotopicType());
		assertEquals(0.8, mr.getPeakTolerance(), 0.01);

		assertEquals(8, mr.size());

		assertEquals("TAU_HUMAN", mr.get(6).get(0).getProteinName());
		assertEquals(2, mr.get(6).size());
		assertEquals("TAU_PANTR", mr.get(6).get(1).getProteinName());
	}

	public void testConstruction() throws Exception {
		File testFile = new File("data/mascot_result_protein_newversion.html");
		// filter by rank 1 only
		MascotResult mr = new MascotResultHtmlStandaloneParser(false)
				.parseFile(testFile);
		assertEquals(2, mr.size());
		assertEquals(3, mr.get(0).get(0).getPeptideCount());
		assertEquals(3, mr.get(1).get(0).getPeptideCount());

		// filter by rank 1 and score
		mr = new MascotResultHtmlStandaloneParser(new MascotPeptideScoreFilter(40),
				false).parseFile(testFile);
		assertEquals(2, mr.size());
		assertEquals(2, mr.get(0).get(0).getPeptideCount());
		assertEquals(1, mr.get(1).get(0).getPeptideCount());

		mr = new MascotResultHtmlStandaloneParser(new MascotPeptidePValueFilter(
				0.01), false).parseFile(testFile);
		assertEquals(2, mr.size());
		assertEquals(2, mr.get(0).get(0).getPeptideCount());
		assertEquals(1, mr.get(1).get(0).getPeptideCount());

		mr = new MascotResultHtmlStandaloneParser(new MascotPeptidePValueFilter(
				0.0016), false).parseFile(testFile);
		assertEquals(1, mr.size());
		assertEquals(1, mr.get(0).get(0).getPeptideCount());

		mr = new MascotResultHtmlStandaloneParser(new MascotPeptidePValueFilter(
				1.0E-5), false).parseFile(testFile);
		assertEquals(0, mr.size());
	}

	public void testO18Result() throws Exception {
		MascotResult mr = new MascotResultHtmlStandaloneParser(true)
				.parseFile(new File("data/mascot_result_protein_o18.html"));
		assertEquals(1, mr.size());
		assertEquals(6, mr.get(0).size());
		assertEquals(10, mr.get(0).get(0).getPeptideCount());
	}
}
