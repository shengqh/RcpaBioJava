package cn.ac.rcpa.bio.sequest.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import cn.ac.rcpa.bio.sequest.ISequestParamsWriter;
import cn.ac.rcpa.bio.sequest.SequestParams;

public abstract class AbstractSequestParamsWriter implements 
		ISequestParamsWriter {
	public void saveToFile(String filename, SequestParams sp) throws IOException {
		PrintWriter bw = new PrintWriter(filename);
		try {
			writeSequestOption(bw, sp);
			writeEnzymeDefinition(bw, sp);
		} finally {
			bw.close();
		}
	}

	private void writeLine(PrintWriter bw, String str) throws IOException {
		bw.println(str);
	}

	private void writeDiff_search_options(PrintWriter bw, SequestParams sp)
			throws IOException {
		final DecimalFormat df = new DecimalFormat("0.0000");
		bw.print("diff_search_options =");
		for (int i = 0; i < sp.getDynamic_modification().getModifications().length; i++) {
			bw
					.print(" "
							+ df.format(sp.getDynamic_modification()
									.getModifications()[i].getAddMass())
							+ " "
							+ sp.getDynamic_modification().getModifications()[i]
									.getMarkChar());
		}
		bw.println();
	}

	/**
	 * writeSequestOption
	 * 
	 * @param bw
	 *          BufferedWriter
	 */
	private void writeSequestOption(PrintWriter bw, SequestParams sp)
			throws IOException {
		final DecimalFormat df = new DecimalFormat("0.0000");

		bw.println("[SEQUEST]");
		bw.println("first_database_name = " + sp.getFirst_database_name());
		bw.println("second_database_name = " + sp.getSecond_database_name());
		bw.println("peptide_mass_tolerance = "
				+ df.format(sp.getPeptide_mass_tolerance()));
		bw.println("ion_series = " + sp.getIon_series());
		bw.println("fragment_ion_tolerance = "
				+ df.format(sp.getFragment_ion_tolerance())
				+ "          ; leave at 0.0 unless you have real poor data");
		bw.println("num_output_lines = " + sp.getNum_output_lines()
				+ "                ; # peptide results to show");
		bw.println("num_results = " + sp.getNum_results()
				+ "                     ; # results to store");
		bw
				.println("num_description_lines = "
						+ sp.getNum_description_lines()
						+ "           ; # full protein descriptions to show for top N peptides");
		bw.println("show_fragment_ions = " + sp.getShow_fragment_ions()
				+ "              ; 0=no, 1=yes");
		bw.println("print_duplicate_references = "
				+ sp.getPrint_duplicate_references() + "      ; 0=no, 1=yes");

		writeEnzymeInfoLine(bw, sp);

		bw.println("max_num_differential_AA_per_mod = "
				+ sp.getMax_num_differential_AA_per_mod()
				+ "           ; max # of modified AA per diff. mod in a peptide");
		writeDiff_search_options(bw, sp);
		bw.println("term_diff_search_options = "
				+ df.format(sp.getDynamic_modification().getAdd_Cterm_peptide()) + " "
				+ df.format(sp.getDynamic_modification().getAdd_Nterm_peptide()));
		writeLine(
				bw,
				"nucleotide_reading_frame = "
						+ sp.getNucleotide_reading_frame()
						+ "        ; 0=protein db, 1-6, 7 = forward three, 8-reverse three, 9=all six");
		bw.println("mass_type_parent = " + sp.getMass_type_parent()
				+ "                ; 0=average masses, 1=monoisotopic masses");
		bw.println("mass_type_fragment = " + sp.getMass_type_fragment()
				+ "              ; 0=average masses, 1=monoisotopic masses");
		bw.println("normalize_xcorr = " + sp.getNormalize_xcorr()
				+ "                 ; use normalized xcorr values in the out file");
		bw.println("remove_precursor_peak = " + sp.getRemove_precursor_peak()
				+ "           ; 0=no, 1=yes");
		writeLine(
				bw,
				"ion_cutoff_percentage = "
						+ df.format(sp.getIon_cutoff_percentage())
						+ "           ; prelim. score cutoff % as a decimal number i.e. 0.30 for 30%");
		bw.println("max_num_internal_cleavage_sites = "
				+ sp.getMax_num_internal_cleavage_sites()
				+ "           ; maximum value is 5");
		writeLine(
				bw,
				"protein_mass_filter = "
						+ sp.getMin_protein_mass()
						+ " "
						+ sp.getMax_protein_mass()
						+ "           ; enter protein mass min & max value ( 0 for both = unused)");
		writeLine(
				bw,
				"match_peak_count = "
						+ sp.getMatch_peak_count()
						+ "                ; number of auto-detected peaks to try matching (max 5)");
		bw
				.println("match_peak_allowed_error = "
						+ sp.getMatch_peak_allowed_error()
						+ "        ;  number of allowed errors in matching auto-detected peaks");
		bw.println("match_peak_tolerance = "
				+ df.format(sp.getMatch_peak_tolerance())
				+ "            ; mass tolerance for matching auto-detected peaks");
		bw.println("create_output_files = " + sp.getCreate_output_files()
				+ "             ; 0=no, 1=yes");
		bw.println("partial_sequence = " + sp.getPartial_sequence());
		bw.println("sequence_header_filter = " + sp.getSequence_header_filter());
		bw.println();
		bw.println("add_Cterm_peptide = "
				+ df.format(sp.getStatic_modification().getAdd_Cterm_peptide())
				+ "               ; added to each peptide C-terminus");
		bw.println("add_Cterm_protein = "
				+ df.format(sp.getStatic_modification().getAdd_Cterm_protein())
				+ "               ; added to each protein C-terminus");
		bw.println("add_Nterm_peptide = "
				+ df.format(sp.getStatic_modification().getAdd_Nterm_peptide())
				+ "               ; added to each peptide N-terminus");
		bw.println("add_Nterm_protein = "
				+ df.format(sp.getStatic_modification().getAdd_Nterm_protein())
				+ "               ; added to each protein N-terminus");
		bw.println("add_G_Glycine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('G'))
				+ "                   ; added to G - avg.  57.0519, mono.  57.02146");
		bw.println("add_A_Alanine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('A'))
				+ "                   ; added to A - avg.  71.0788, mono.  71.03711");
		bw.println("add_S_Serine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('S'))
				+ "                    ; added to S - avg.  87.0782, mono.  87.02303");
		bw.println("add_P_Proline = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('P'))
				+ "                   ; added to P - avg.  97.1167, mono.  97.05276");
		bw.println("add_V_Valine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('V'))
				+ "                    ; added to V - avg.  99.1326, mono.  99.06841");
		bw.println("add_T_Threonine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('T'))
				+ "                 ; added to T - avg. 101.1051, mono. 101.04768");
		bw.println("add_C_Cysteine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('C'))
				+ "                  ; added to C - avg. 103.1388, mono. 103.00919");
		bw.println("add_L_Leucine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('L'))
				+ "                   ; added to L - avg. 113.1594, mono. 113.08406");
		bw.println("add_I_Isoleucine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('I'))
				+ "                ; added to I - avg. 113.1594, mono. 113.08406");
		bw
				.println("add_X_LorI = "
						+ df.format(sp.getStatic_modification().getAminoacidModification(
								'X'))
						+ "                      ; added to X - avg. 113.1594, mono. 113.08406");
		bw.println("add_N_Asparagine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('N'))
				+ "                ; added to N - avg. 114.1038, mono. 114.04293");
		bw.println("add_O_Ornithine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('O'))
				+ "                 ; added to O - avg. 114.1472, mono  114.07931");
		bw.println("add_B_avg_NandD = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('B'))
				+ "                 ; added to B - avg. 114.5962, mono. 114.53494");
		bw.println("add_D_Aspartic_Acid = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('D'))
				+ "             ; added to D - avg. 115.0886, mono. 115.02694");
		bw.println("add_Q_Glutamine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('Q'))
				+ "                 ; added to Q - avg. 128.1307, mono. 128.05858");
		bw.println("add_K_Lysine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('K'))
				+ "                    ; added to K - avg. 128.1741, mono. 128.09496");
		bw.println("add_Z_avg_QandE = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('Z'))
				+ "                 ; added to Z - avg. 128.6231, mono. 128.55059");
		bw.println("add_E_Glutamic_Acid = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('E'))
				+ "             ; added to E - avg. 129.1155, mono. 129.04259");
		bw.println("add_M_Methionine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('M'))
				+ "                ; added to M - avg. 131.1926, mono. 131.04049");
		bw.println("add_H_Histidine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('H'))
				+ "                 ; added to H - avg. 137.1411, mono. 137.05891");
		bw.println("add_F_Phenylalanine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('F'))
				+ "             ; added to F - avg. 147.1766, mono. 147.06841");
		bw.println("add_R_Arginine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('R'))
				+ "                  ; added to R - avg. 156.1875, mono. 156.10111");
		bw.println("add_Y_Tyrosine = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('Y'))
				+ "                  ; added to Y - avg. 163.1760, mono. 163.06333");
		bw.println("add_W_Tryptophan = "
				+ df.format(sp.getStatic_modification().getAminoacidModification('W'))
				+ "                ; added to W - avg. 186.2132, mono. 186.07931");
		bw.println();
	}

	protected abstract void writeEnzymeInfoLine(PrintWriter bw, SequestParams sp)
			throws IOException;

	protected abstract void writeEnzymeDefinition(PrintWriter bw, SequestParams sp)
			throws IOException;
}
