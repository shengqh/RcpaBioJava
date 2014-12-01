package cn.ac.rcpa.bio.sequest;

import cn.ac.rcpa.bio.sequest.impl.SequestParams31Reader;
import junit.framework.TestCase;

public class TestSequestParams31Reader extends TestCase {
	public void testLoadFromFile() throws Exception {
		String sourceFile = "data/sequest.old.params";
		SequestParams sp = new SequestParams31Reader().loadFromFile(sourceFile);

		assertEquals("D:\\database\\ser_sea.fasta", sp.getFirst_database_name());
		assertEquals("", sp.getSecond_database_name());
		assertEquals(3.0, sp.getPeptide_mass_tolerance());
		assertEquals("0 1 1 0.0 1.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0", sp
				.getIon_series());
		assertEquals(0.0, sp.getFragment_ion_tolerance());
		assertEquals(10, sp.getNum_output_lines());
		assertEquals(250, sp.getNum_results());
		assertEquals(5, sp.getNum_description_lines());
		assertEquals(0, sp.getShow_fragment_ions());
		assertEquals(100, sp.getPrint_duplicate_references());
		assertEquals(1, sp.getEnzyme_number());
		assertEquals(EnzymeLimitType.Full, sp.getEnzymeLimitType());
		assertEquals(3, sp.getMax_num_differential_AA_per_mod());
		assertEquals(0.0, sp.getDynamic_modification().getAdd_Cterm_peptide());
		assertEquals(0.0, sp.getDynamic_modification().getAdd_Nterm_peptide());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[0].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[1].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[2].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[3].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[4].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[5].getAddMass());
		assertEquals(0, sp.getNucleotide_reading_frame());
		assertEquals(0, sp.getMass_type_parent());
		assertEquals(0, sp.getMass_type_fragment());
		assertEquals(0, sp.getNormalize_xcorr());
		assertEquals(0, sp.getRemove_precursor_peak());
		assertEquals(0.0, sp.getIon_cutoff_percentage());
		assertEquals(2, sp.getMax_num_internal_cleavage_sites());
		assertEquals(0, sp.getMin_protein_mass());
		assertEquals(0, sp.getMax_protein_mass());
		assertEquals(0, sp.getMatch_peak_count());
		assertEquals(1, sp.getMatch_peak_allowed_error());
		assertEquals(1.0, sp.getMatch_peak_tolerance());
		assertEquals("", sp.getPartial_sequence());
		assertEquals("", sp.getSequence_header_filter());

		assertEquals(0.0, sp.getStatic_modification().getAdd_Cterm_peptide());
		assertEquals(0.0, sp.getStatic_modification().getAdd_Nterm_peptide());
		assertEquals(0.0, sp.getStatic_modification().getAdd_Cterm_protein());
		assertEquals(0.0, sp.getStatic_modification().getAdd_Nterm_protein());
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('G'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('A'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('S'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('P'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('V'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('T'));
		assertEquals(58.02, sp.getStatic_modification().getAminoacidModification(
				'C'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('L'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('I'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('X'));
		assertEquals(2.0, sp.getStatic_modification().getAminoacidModification('N'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('O'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('B'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('D'));
		assertEquals(2.0, sp.getStatic_modification().getAminoacidModification('Q'));
		assertEquals(2.0, sp.getStatic_modification().getAminoacidModification('K'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('Z'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('E'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('M'));
		assertEquals(3.0, sp.getStatic_modification().getAminoacidModification('H'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('F'));
		assertEquals(4.0, sp.getStatic_modification().getAminoacidModification('R'));
		assertEquals(1.0, sp.getStatic_modification().getAminoacidModification('Y'));
		assertEquals(2.0, sp.getStatic_modification().getAminoacidModification('W'));

		assertEquals("No_Enzyme", sp.getEnzyme(0).getEnzymeName());
		assertEquals(0, sp.getEnzyme(0).getOffset());
		assertEquals("-", sp.getEnzyme(0).getCleaveAt());
		assertEquals("-", sp.getEnzyme(0).getUncleaveAt());

		assertEquals("Trypsin", sp.getEnzyme(1).getEnzymeName());
		assertEquals(1, sp.getEnzyme(1).getOffset());
		assertEquals("KR", sp.getEnzyme(1).getCleaveAt());
		assertEquals("-", sp.getEnzyme(1).getUncleaveAt());

		assertEquals("Trypsin(KRLNH)", sp.getEnzyme(2).getEnzymeName());
		assertEquals(1, sp.getEnzyme(2).getOffset());
		assertEquals("KRLNH", sp.getEnzyme(2).getCleaveAt());
		assertEquals("-", sp.getEnzyme(2).getUncleaveAt());

		assertEquals("Chymotrypsin", sp.getEnzyme(3).getEnzymeName());
		assertEquals(1, sp.getEnzyme(3).getOffset());
		assertEquals("FWYL", sp.getEnzyme(3).getCleaveAt());
		assertEquals("-", sp.getEnzyme(3).getUncleaveAt());

		assertEquals("Chymotrypsin(FWY)", sp.getEnzyme(4).getEnzymeName());
		assertEquals(1, sp.getEnzyme(4).getOffset());
		assertEquals("FWY", sp.getEnzyme(4).getCleaveAt());
		assertEquals("P", sp.getEnzyme(4).getUncleaveAt());

		assertEquals("Clostripain", sp.getEnzyme(5).getEnzymeName());
		assertEquals(1, sp.getEnzyme(5).getOffset());
		assertEquals("R", sp.getEnzyme(5).getCleaveAt());
		assertEquals("-", sp.getEnzyme(5).getUncleaveAt());

		assertEquals("Cyanogen_Bromide", sp.getEnzyme(6).getEnzymeName());
		assertEquals(1, sp.getEnzyme(6).getOffset());
		assertEquals("M", sp.getEnzyme(6).getCleaveAt());
		assertEquals("-", sp.getEnzyme(6).getUncleaveAt());

		assertEquals("IodosoBenzoate", sp.getEnzyme(7).getEnzymeName());
		assertEquals(1, sp.getEnzyme(7).getOffset());
		assertEquals("W", sp.getEnzyme(7).getCleaveAt());
		assertEquals("-", sp.getEnzyme(7).getUncleaveAt());

		assertEquals("Proline_Endopept", sp.getEnzyme(8).getEnzymeName());
		assertEquals(1, sp.getEnzyme(8).getOffset());
		assertEquals("P", sp.getEnzyme(8).getCleaveAt());
		assertEquals("-", sp.getEnzyme(8).getUncleaveAt());

		assertEquals("Staph_Protease", sp.getEnzyme(9).getEnzymeName());
		assertEquals(1, sp.getEnzyme(9).getOffset());
		assertEquals("E", sp.getEnzyme(9).getCleaveAt());
		assertEquals("-", sp.getEnzyme(9).getUncleaveAt());

		assertEquals("Trypsin_K", sp.getEnzyme(10).getEnzymeName());
		assertEquals(1, sp.getEnzyme(10).getOffset());
		assertEquals("K", sp.getEnzyme(10).getCleaveAt());
		assertEquals("P", sp.getEnzyme(10).getUncleaveAt());

		assertEquals("Trypsin_R", sp.getEnzyme(11).getEnzymeName());
		assertEquals(1, sp.getEnzyme(11).getOffset());
		assertEquals("R", sp.getEnzyme(11).getCleaveAt());
		assertEquals("P", sp.getEnzyme(11).getUncleaveAt());

		assertEquals("GluC", sp.getEnzyme(12).getEnzymeName());
		assertEquals(1, sp.getEnzyme(12).getOffset());
		assertEquals("ED", sp.getEnzyme(12).getCleaveAt());
		assertEquals("-", sp.getEnzyme(12).getUncleaveAt());

		assertEquals("LysC", sp.getEnzyme(13).getEnzymeName());
		assertEquals(1, sp.getEnzyme(13).getOffset());
		assertEquals("K", sp.getEnzyme(13).getCleaveAt());
		assertEquals("-", sp.getEnzyme(13).getUncleaveAt());

		assertEquals("AspN", sp.getEnzyme(14).getEnzymeName());
		assertEquals(0, sp.getEnzyme(14).getOffset());
		assertEquals("D", sp.getEnzyme(14).getCleaveAt());
		assertEquals("-", sp.getEnzyme(14).getUncleaveAt());

		assertEquals("Elastase", sp.getEnzyme(15).getEnzymeName());
		assertEquals(1, sp.getEnzyme(15).getOffset());
		assertEquals("ALIV", sp.getEnzyme(15).getCleaveAt());
		assertEquals("P", sp.getEnzyme(15).getUncleaveAt());

		assertEquals("Elastase/Tryp/Chymo", sp.getEnzyme(16).getEnzymeName());
		assertEquals(1, sp.getEnzyme(16).getOffset());
		assertEquals("ALIVKRWFY", sp.getEnzyme(16).getCleaveAt());
		assertEquals("P", sp.getEnzyme(16).getUncleaveAt());
	}
	/*
	 * public void testReadAndWrite() throws FileNotFoundException, IOException {
	 * String sourceFile = "data/sequest.params"; String destFile =
	 * "data/sequest2.params"; SequestParams sp =
	 * SequestParams.loadFromFile(sourceFile); sp.saveToFile(destFile);
	 * 
	 * BufferedReader br1 = new BufferedReader(new FileReader(sourceFile));
	 * BufferedReader br2 = new BufferedReader(new FileReader(destFile)); String
	 * line1, line2; do { line1 = br1.readLine(); line2 = br2.readLine(); if
	 * (line1 == null || line2 == null) { break; }
	 * 
	 * assertTrue(line1 + "\n" + line2, line1.equals(line2)); } while (true);
	 * 
	 * if (line1 == null && line2 != null) { assertTrue("line1 == null && line2 == " +
	 * line2, false); } else if (line2 == null && line1 != null) {
	 * assertTrue("line2 == null && line1 == " + line1, false); } }
	 * 
	 * public void testSaveToFile() throws FileNotFoundException, IOException {
	 * String sourceFile = "data/sequest.params"; SequestParams sourceSp =
	 * SequestParams.loadFromFile(sourceFile);
	 * 
	 * String destFile = "data/sequest_default.params"; SequestParams sp = new
	 * SequestParams();
	 * sp.setFirst_database_name(sourceSp.getFirst_database_name());
	 * sp.saveToFile(destFile);
	 * 
	 * BufferedReader br1 = new BufferedReader(new FileReader(sourceFile));
	 * BufferedReader br2 = new BufferedReader(new FileReader(destFile)); String
	 * line1, line2; do { line1 = br1.readLine(); line2 = br2.readLine(); if
	 * (line1 == null || line2 == null) { break; }
	 * 
	 * assertEquals(line1 + "\n" + line2, line1, line2); } while (true);
	 * 
	 * assertEquals(line1 + "\n" + line2, line1, line2); }
	 */
}
