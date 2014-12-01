package cn.ac.rcpa.bio.sequest.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.sequest.ISequestParamsReader;
import cn.ac.rcpa.bio.sequest.SequestParams;

public abstract class AbstractSequestParamsReader implements ISequestParamsReader {
	private static final String SEQUEST_PATTERN_STRING = "(\\w*)\\s*=\\s*(.*?)(?=;|$)";

	private static Pattern sequestPattern = null;

	private static Pattern getSequestPattern() {
		if (sequestPattern == null) {
			sequestPattern = Pattern.compile(SEQUEST_PATTERN_STRING);
		}
		return sequestPattern;
	}

	/**
	 * readSequestOption
	 * 
	 * @param br
	 *          BufferedReader
	 * @param result
	 *          SequestParams
	 */
	private void readSequestOption(BufferedReader br, SequestParams result)
			throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		String line;
		while ((line = br.readLine()) != null) {
			if (line.trim().equals("[SEQUEST_ENZYME_INFO]")) {
				break;
			}
			Matcher match = getSequestPattern().matcher(line);
			if (match.find()) {
				map.put(match.group(1), match.group(2).trim());
			}
		}

		parseOptionFromMap(result, map);
	}

	protected void parseOptionFromMap(SequestParams result,
			HashMap<String, String> map) {
		result.setFirst_database_name(map.get("first_database_name"));
		result.setSecond_database_name(map.get("second_database_name"));
		result.setPeptide_mass_tolerance(Double.parseDouble(map
				.get("peptide_mass_tolerance")));
		result.setIon_series(map.get("ion_series"));
		result.setFragment_ion_tolerance(Double.parseDouble(map
				.get("fragment_ion_tolerance")));
		result.setNum_output_lines(Integer.parseInt(map.get("num_output_lines")));
		result.setNum_results(Integer.parseInt(map.get("num_results")));
		result.setNum_description_lines(Integer.parseInt(map
				.get("num_description_lines")));
		result.setShow_fragment_ions(Integer
				.parseInt(map.get("show_fragment_ions")));
		result.setPrint_duplicate_references(Integer.parseInt(map
				.get("print_duplicate_references")));
		result.setDiff_search_options(map.get("diff_search_options"));
		result.setTerm_diff_search_options(map.get("term_diff_search_options"));
		result.setNucleotide_reading_frame(Integer.parseInt(map
				.get("nucleotide_reading_frame")));
		result.setMass_type_parent(Integer.parseInt(map.get("mass_type_parent")));
		result.setMass_type_fragment(Integer
				.parseInt(map.get("mass_type_fragment")));
		result.setNormalize_xcorr(Integer.parseInt(map.get("normalize_xcorr")));
		result.setRemove_precursor_peak(Integer.parseInt(map
				.get("remove_precursor_peak")));
		result.setIon_cutoff_percentage(Double.parseDouble(map
				.get("ion_cutoff_percentage")));
		result.setMax_num_internal_cleavage_sites(Integer.parseInt(map
				.get("max_num_internal_cleavage_sites")));
		result.setProtein_mass_filter(map.get("protein_mass_filter"));

		result.setMatch_peak_count(Integer.parseInt(map.get("match_peak_count")));
		result.setMatch_peak_allowed_error(Integer.parseInt(map
				.get("match_peak_allowed_error")));
		result.setMax_num_internal_cleavage_sites(Integer.parseInt(map
				.get("max_num_internal_cleavage_sites")));
		result.setMatch_peak_tolerance(Double.parseDouble(map
				.get("match_peak_tolerance")));
		if (map.get("create_output_files") != null) {
			result.setCreate_output_files(Integer.parseInt(map
					.get("create_output_files")));
		} else {
			result.setCreate_output_files(1);
		}
		result.setPartial_sequence(map.get("partial_sequence"));
		result.setSequence_header_filter(map.get("sequence_header_filter"));

		result.getStatic_modification().setAdd_Cterm_peptide(
				Double.parseDouble(map.get("add_Cterm_peptide")));
		result.getStatic_modification().setAdd_Cterm_protein(
				Double.parseDouble(map.get("add_Cterm_protein")));
		result.getStatic_modification().setAdd_Nterm_peptide(
				Double.parseDouble(map.get("add_Nterm_peptide")));
		result.getStatic_modification().setAdd_Nterm_protein(
				Double.parseDouble(map.get("add_Nterm_protein")));
		result.getStatic_modification().setAminoacidModification('G',
				Double.parseDouble(map.get("add_G_Glycine")));
		result.getStatic_modification().setAminoacidModification('A',
				Double.parseDouble(map.get("add_A_Alanine")));
		result.getStatic_modification().setAminoacidModification('S',
				Double.parseDouble(map.get("add_S_Serine")));
		result.getStatic_modification().setAminoacidModification('P',
				Double.parseDouble(map.get("add_P_Proline")));
		result.getStatic_modification().setAminoacidModification('V',
				Double.parseDouble(map.get("add_V_Valine")));
		result.getStatic_modification().setAminoacidModification('T',
				Double.parseDouble(map.get("add_T_Threonine")));
		result.getStatic_modification().setAminoacidModification('C',
				Double.parseDouble(map.get("add_C_Cysteine")));
		result.getStatic_modification().setAminoacidModification('L',
				Double.parseDouble(map.get("add_L_Leucine")));
		result.getStatic_modification().setAminoacidModification('I',
				Double.parseDouble(map.get("add_I_Isoleucine")));
		result.getStatic_modification().setAminoacidModification('X',
				Double.parseDouble(map.get("add_X_LorI")));
		result.getStatic_modification().setAminoacidModification('N',
				Double.parseDouble(map.get("add_N_Asparagine")));
		result.getStatic_modification().setAminoacidModification('O',
				Double.parseDouble(map.get("add_O_Ornithine")));
		result.getStatic_modification().setAminoacidModification('B',
				Double.parseDouble(map.get("add_B_avg_NandD")));
		result.getStatic_modification().setAminoacidModification('D',
				Double.parseDouble(map.get("add_D_Aspartic_Acid")));
		result.getStatic_modification().setAminoacidModification('Q',
				Double.parseDouble(map.get("add_Q_Glutamine")));
		result.getStatic_modification().setAminoacidModification('K',
				Double.parseDouble(map.get("add_K_Lysine")));
		result.getStatic_modification().setAminoacidModification('Z',
				Double.parseDouble(map.get("add_Z_avg_QandE")));
		result.getStatic_modification().setAminoacidModification('E',
				Double.parseDouble(map.get("add_E_Glutamic_Acid")));
		result.getStatic_modification().setAminoacidModification('M',
				Double.parseDouble(map.get("add_M_Methionine")));
		result.getStatic_modification().setAminoacidModification('H',
				Double.parseDouble(map.get("add_H_Histidine")));
		result.getStatic_modification().setAminoacidModification('F',
				Double.parseDouble(map.get("add_F_Phenylalanine")));
		result.getStatic_modification().setAminoacidModification('R',
				Double.parseDouble(map.get("add_R_Arginine")));
		result.getStatic_modification().setAminoacidModification('Y',
				Double.parseDouble(map.get("add_Y_Tyrosine")));
		result.getStatic_modification().setAminoacidModification('W',
				Double.parseDouble(map.get("add_W_Tryptophan")));
	}

	public SequestParams loadFromFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException("File " + filename + " not exists!");
		}

		BufferedReader br = new BufferedReader(new FileReader(filename));
		SequestParams result = new SequestParams();

		readSequestOption(br, result);
		readEnzymeDefinition(br, result);

		return result;
	}
	
	protected abstract void readEnzymeDefinition(BufferedReader br,
			SequestParams result) throws IOException;

}
