package cn.ac.rcpa.bio.sequest;

import cn.ac.rcpa.bio.sequest.impl.SequestParams32Reader;
import junit.framework.TestCase;

public class TestSequestParams32Reader extends TestCase {
	public void testLoadFromFile() throws Exception {
		String sourceFile = "data/sequest.new.params";
		SequestParams sp = new SequestParams32Reader().loadFromFile(sourceFile);

		assertEquals("D:\\cxj\\lep_N15\\lai_newest_orf.REVERSED.fasta", sp
				.getFirst_database_name());
		assertEquals("", sp.getSecond_database_name());
		assertEquals(200.0, sp.getPeptide_mass_tolerance());
		assertEquals("0 1 1 0.0 1.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0", sp
				.getIon_series());
		assertEquals(1.0, sp.getFragment_ion_tolerance());
		assertEquals(10, sp.getNum_output_lines());
		assertEquals(250, sp.getNum_results());
		assertEquals(5, sp.getNum_description_lines());
		assertEquals(0, sp.getShow_fragment_ions());
		assertEquals(100, sp.getPrint_duplicate_references());
		assertEquals(0, sp.getEnzyme_number());
		assertEquals(3, sp.getMax_num_differential_per_peptide());

		assertEquals(0.0, sp.getDynamic_modification().getAdd_Cterm_peptide());
		assertEquals(0.0, sp.getDynamic_modification().getAdd_Nterm_peptide());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[0].getAddMass());
		assertEquals(15.99492, sp.getDynamic_modification()
				.getModifications()[1].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[2].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[3].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[4].getAddMass());
		assertEquals(0.0,
				sp.getDynamic_modification().getModifications()[5].getAddMass());
		assertEquals(0, sp.getNucleotide_reading_frame());
		assertEquals(1, sp.getMass_type_parent());
		assertEquals(1, sp.getMass_type_fragment());
		assertEquals(0, sp.getNormalize_xcorr());
		assertEquals(0, sp.getRemove_precursor_peak());
		assertEquals(0.0, sp.getIon_cutoff_percentage());
		assertEquals(0, sp.getMax_num_internal_cleavage_sites());
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
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'G'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'A'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'S'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'P'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'V'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'T'));
		assertEquals(58.0185, sp.getStatic_modification().getAminoacidModification(
				'C'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'L'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'I'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'X'));
		assertEquals(1.9941, sp.getStatic_modification().getAminoacidModification(
				'N'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('O'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('B'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'D'));
		assertEquals(1.9941, sp.getStatic_modification().getAminoacidModification(
				'Q'));
		assertEquals(1.9941, sp.getStatic_modification().getAminoacidModification(
				'K'));
		assertEquals(0.0, sp.getStatic_modification().getAminoacidModification('Z'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'E'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'M'));
		assertEquals(2.9911, sp.getStatic_modification().getAminoacidModification(
				'H'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'F'));
		assertEquals(3.9881, sp.getStatic_modification().getAminoacidModification(
				'R'));
		assertEquals(0.9970, sp.getStatic_modification().getAminoacidModification(
				'Y'));
		assertEquals(1.9941, sp.getStatic_modification().getAminoacidModification(
				'W'));

		assertEquals(1, sp.getEnzymeCount());
		assertEquals("Trypsin(KR)", sp.getEnzyme(0).getEnzymeName());
		assertEquals(1, sp.getEnzyme(0).getOffset());
		assertEquals("KR", sp.getEnzyme(0).getCleaveAt());
		assertEquals("-", sp.getEnzyme(0).getUncleaveAt());

		assertEquals(EnzymeLimitType.Partially, sp.getEnzymeLimitType());
	}
}
