package cn.ac.rcpa.bio.sequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequestParams {

	private String first_database_name;

	private String second_database_name;

	private double peptide_mass_tolerance;

	private int peptide_mass_units;// 0=amu, 1=mmu, 2=ppm

	private boolean using_ion_series_a;

	private boolean using_ion_series_b;

	private boolean using_ion_series_y;

	private double[] ion_series_others = { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
			1.0, 0.0 };

	private double fragment_ion_tolerance;

	private int num_output_lines;

	private int num_results;

	private int num_description_lines;

	private int show_fragment_ions;

	private int print_duplicate_references;

	private int enzyme_number;

	private EnzymeLimitType enzymeLimitType = EnzymeLimitType.Full;

	/**
	 * deprecated in new version of sequest
	 */
	private int max_num_differential_AA_per_mod;

	/**
	 * add in new version of sequest
	 */
	private int max_num_differential_per_peptide;

	private SequestDynamicModification dynamic_modification = new SequestDynamicModification();

	private int nucleotide_reading_frame;

	private int mass_type_parent;

	private int mass_type_fragment;

	private int normalize_xcorr;

	private int remove_precursor_peak;

	private double ion_cutoff_percentage;

	private int max_num_internal_cleavage_sites;

	private int min_protein_mass;

	private int max_protein_mass;

	private int match_peak_count;

	private int match_peak_allowed_error;

	private double match_peak_tolerance;

	private int create_output_files;

	private String partial_sequence;

	private String sequence_header_filter;

	private SequestStaticModification static_modification = new SequestStaticModification();

	private ArrayList<SequestEnzyme> enzymeList = new ArrayList<SequestEnzyme>();

	private void doInit() {
		first_database_name = "";
		second_database_name = "";
		peptide_mass_tolerance = 3.0;
		peptide_mass_units = 0;

		using_ion_series_a = false;
		using_ion_series_b = true;
		using_ion_series_y = true;
		ion_series_others[0] = 0.0;
		ion_series_others[1] = 1.0;
		ion_series_others[2] = 0.0;
		ion_series_others[3] = 0.0;
		ion_series_others[4] = 0.0;
		ion_series_others[5] = 0.0;
		ion_series_others[6] = 0.0;
		ion_series_others[7] = 1.0;
		ion_series_others[8] = 0.0;

		fragment_ion_tolerance = 0.0;
		num_output_lines = 10;
		num_results = 500;
		num_description_lines = 5;
		show_fragment_ions = 0;
		print_duplicate_references = 1;
		enzyme_number = 1;
		max_num_differential_AA_per_mod = 4;

		dynamic_modification.init();

		nucleotide_reading_frame = 0;
		mass_type_parent = 0;
		mass_type_fragment = 0;
		normalize_xcorr = 0;
		remove_precursor_peak = 0;
		ion_cutoff_percentage = 0.0;
		max_num_internal_cleavage_sites = 2;
		min_protein_mass = 0;
		max_protein_mass = 0;
		match_peak_count = 0;
		match_peak_allowed_error = 1;
		match_peak_tolerance = 1.0;
		create_output_files = 1;
		partial_sequence = "";
		sequence_header_filter = "";

		static_modification.init();

		doInitEnzyme();
	}

	private void doInitEnzyme() {
		enzymeList.clear();
		enzymeList.add(new SequestEnzyme("No_Enzyme", 0, "-", "-"));
		enzymeList.add(new SequestEnzyme("Trypsin", 1, "KR", "-"));
		enzymeList.add(new SequestEnzyme("Trypsin(KRLNH)", 1, "KRLNH", "-"));
		enzymeList.add(new SequestEnzyme("Chymotrypsin", 1, "FWYL", "-"));
		enzymeList.add(new SequestEnzyme("Chymotrypsin(FWY)", 1, "FWY", "P"));
		enzymeList.add(new SequestEnzyme("Clostripain", 1, "R", "-"));
		enzymeList.add(new SequestEnzyme("Cyanogen_Bromide", 1, "M", "-"));
		enzymeList.add(new SequestEnzyme("IodosoBenzoate", 1, "W", "-"));
		enzymeList.add(new SequestEnzyme("Proline_Endopept", 1, "P", "-"));
		enzymeList.add(new SequestEnzyme("Staph_Protease", 1, "E", "-"));
		enzymeList.add(new SequestEnzyme("Trypsin_K", 1, "K", "P"));
		enzymeList.add(new SequestEnzyme("Trypsin_R", 1, "R", "P"));
		enzymeList.add(new SequestEnzyme("GluC", 1, "ED", "-"));
		enzymeList.add(new SequestEnzyme("LysC", 1, "K", "-"));
		enzymeList.add(new SequestEnzyme("AspN", 0, "D", "-"));
		enzymeList.add(new SequestEnzyme("Elastase", 1, "ALIV", "P"));
		enzymeList
				.add(new SequestEnzyme("Elastase/Tryp/Chymo", 1, "ALIVKRWFY", "P"));
	}

	public int getNormalize_xcorr() {
		return normalize_xcorr;
	}

	public int getMax_num_internal_cleavage_sites() {
		return max_num_internal_cleavage_sites;
	}

	public int getNucleotide_reading_frame() {
		return nucleotide_reading_frame;
	}

	public int getMatch_peak_count() {
		return match_peak_count;
	}

	public String getSecond_database_name() {
		return second_database_name;
	}

	public int getMax_num_differential_AA_per_mod() {
		return max_num_differential_AA_per_mod;
	}

	public double getMatch_peak_tolerance() {
		return match_peak_tolerance;
	}

	public String getIon_series() {
		StringBuffer sb = new StringBuffer();
		final DecimalFormat df = new DecimalFormat("0.0");
		sb.append(using_ion_series_a ? 1 : 0);
		sb.append(" ");
		sb.append(using_ion_series_b ? 1 : 0);
		sb.append(" ");
		sb.append(using_ion_series_y ? 1 : 0);
		for (int i = 0; i < ion_series_others.length; i++) {
			sb.append(" " + df.format(ion_series_others[i]));
		}
		return sb.toString();
	}

	public double getFragment_ion_tolerance() {
		return fragment_ion_tolerance;
	}

	public double getIon_cutoff_percentage() {
		return ion_cutoff_percentage;
	}

	public double getPeptide_mass_tolerance() {
		return peptide_mass_tolerance;
	}

	public int getMatch_peak_allowed_error() {
		return match_peak_allowed_error;
	}

	public SequestDynamicModification getDynamic_modification() {
		return dynamic_modification;
	}

	public String getSequence_header_filter() {
		return sequence_header_filter;
	}

	public String getFirst_database_name() {
		return first_database_name;
	}

	public int getMass_type_fragment() {
		return mass_type_fragment;
	}

	public String getPartial_sequence() {
		return partial_sequence;
	}

	public int getMass_type_parent() {
		return mass_type_parent;
	}

	public void setEnzyme_number(int enzyme_number) {
		this.enzyme_number = enzyme_number;
	}

	public void setNormalize_xcorr(int normalize_xcorr) {
		this.normalize_xcorr = normalize_xcorr;
	}

	public void setMax_num_internal_cleavage_sites(
			int max_num_internal_cleavage_sites) {
		this.max_num_internal_cleavage_sites = max_num_internal_cleavage_sites;
	}

	public void setNucleotide_reading_frame(int nucleotide_reading_frame) {
		this.nucleotide_reading_frame = nucleotide_reading_frame;
	}

	public void setMatch_peak_count(int match_peak_count) {
		this.match_peak_count = match_peak_count;
	}

	public void setSecond_database_name(String second_database_name) {
		this.second_database_name = second_database_name;
	}

	public void setMax_num_differential_AA_per_mod(
			int max_num_differential_AA_per_mod) {
		this.max_num_differential_AA_per_mod = max_num_differential_AA_per_mod;
	}

	public void setMatch_peak_tolerance(double match_peak_tolerance) {
		this.match_peak_tolerance = match_peak_tolerance;
	}

	public void setIon_series(String ion_series) {
		final String ION_SERIES_PATTERN = "\\s*(\\d)\\s+(\\d)\\s+(\\d)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)";
		final Pattern pattern = Pattern.compile(ION_SERIES_PATTERN);
		Matcher matcher = pattern.matcher(ion_series);
		if (matcher.find()) {
			this.using_ion_series_a = matcher.group(1).equals("1");
			this.using_ion_series_b = matcher.group(2).equals("1");
			this.using_ion_series_y = matcher.group(3).equals("1");
			for (int i = 0; i < this.ion_series_others.length; i++) {
				try {
					this.ion_series_others[i] = Double.parseDouble(matcher.group(i + 4));
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(ion_series
							+ " is not a valid ion series");
				}
			}
		} else {
			throw new IllegalArgumentException(ion_series
					+ " is not a valid ion series");
		}
	}

	public void setFragment_ion_tolerance(double fragment_ion_tolerance) {
		this.fragment_ion_tolerance = fragment_ion_tolerance;
	}

	public void setIon_cutoff_percentage(double ion_cutoff_percentage) {
		this.ion_cutoff_percentage = ion_cutoff_percentage;
	}

	public void setPeptide_mass_tolerance(double peptide_mass_tolerance) {
		this.peptide_mass_tolerance = peptide_mass_tolerance;
	}

	public void setMatch_peak_allowed_error(int match_peak_allowed_error) {
		this.match_peak_allowed_error = match_peak_allowed_error;
	}

	public void setSequence_header_filter(String sequence_header_filter) {
		this.sequence_header_filter = sequence_header_filter;
	}

	public void setFirst_database_name(String first_database_name) {
		this.first_database_name = first_database_name;
	}

	public void setMass_type_fragment(int mass_type_fragment) {
		this.mass_type_fragment = mass_type_fragment;
	}

	public void setPartial_sequence(String partial_sequence) {
		this.partial_sequence = partial_sequence;
	}

	public void setMass_type_parent(int mass_type_parent) {
		this.mass_type_parent = mass_type_parent;
	}

	public void setShow_fragment_ions(int show_fragment_ions) {
		this.show_fragment_ions = show_fragment_ions;
	}

	public void setRemove_precursor_peak(int remove_precursor_peak) {
		this.remove_precursor_peak = remove_precursor_peak;
	}

	public void setPrint_duplicate_references(int print_duplicate_references) {
		this.print_duplicate_references = print_duplicate_references;
	}

	public void setCreate_output_files(int create_output_files) {
		this.create_output_files = create_output_files;
	}

	public void setNum_results(int num_results) {
		this.num_results = num_results;
	}

	public void setNum_description_lines(int num_description_lines) {
		this.num_description_lines = num_description_lines;
	}

	public void setNum_output_lines(int num_output_lines) {
		this.num_output_lines = num_output_lines;
	}

	public void setMax_protein_mass(int max_protein_mass) {
		this.max_protein_mass = max_protein_mass;
	}

	public void setMin_protein_mass(int min_protein_mass) {
		this.min_protein_mass = min_protein_mass;
	}

	public int getEnzyme_number() {
		return enzyme_number;
	}

	public int getShow_fragment_ions() {
		return show_fragment_ions;
	}

	public int getRemove_precursor_peak() {
		return remove_precursor_peak;
	}

	public int getPrint_duplicate_references() {
		return print_duplicate_references;
	}

	public int getCreate_output_files() {
		return create_output_files;
	}

	public int getNum_results() {
		return num_results;
	}

	public int getNum_description_lines() {
		return num_description_lines;
	}

	public int getNum_output_lines() {
		return num_output_lines;
	}

	public int getMax_protein_mass() {
		return max_protein_mass;
	}

	public int getMin_protein_mass() {
		return min_protein_mass;
	}

	public SequestStaticModification getStatic_modification() {
		return static_modification;
	}

	public int getEnzymeCount() {
		return enzymeList.size();
	}

	public SequestEnzyme getEnzyme(int index) {
		return enzymeList.get(index);
	}

	public void addEnzyme(SequestEnzyme enzyme) {
		enzymeList.add(enzyme);
	}

	public void removeEnzyme(SequestEnzyme enzyme) {
		enzymeList.remove(enzyme);
	}

	public SequestParams() {
		doInit();
	}

	public void clearEnzyme() {
		enzymeList.clear();
	}

	public void setProtein_mass_filter(String string) {
		final String PROTEIN_MASS_FILTER = "\\s*(\\d*)\\s+(\\d*)\\s*";
		final Pattern pattern = Pattern.compile(PROTEIN_MASS_FILTER);
		Matcher match = pattern.matcher(string);
		if (!match.find()) {
			throw new IllegalArgumentException(string
					+ " is not a valid protein_mass_filter line!");
		}

		try {
			min_protein_mass = Integer.parseInt(match.group(1));
			max_protein_mass = Integer.parseInt(match.group(2));
		} catch (Exception ex) {
			throw new IllegalArgumentException(string
					+ " is not a valid protein_mass_filter line!");
		}
	}

	public void setTerm_diff_search_options(String string) {
		final String TERM_DIFF_SEARCH_OPTION = "\\s*(\\S*)\\s+(\\S*)\\s*";
		final Pattern termDiffSearchOptionPattern = Pattern
				.compile(TERM_DIFF_SEARCH_OPTION);
		Matcher match = termDiffSearchOptionPattern.matcher(string);
		if (!match.find()) {
			throw new IllegalArgumentException(string
					+ " is not a valid term_diff_search_option line!");
		}

		try {
			dynamic_modification.setAdd_Cterm_peptide(Double.parseDouble(match
					.group(1)));
			dynamic_modification.setAdd_Nterm_peptide(Double.parseDouble(match
					.group(2)));
		} catch (Exception ex) {
			throw new IllegalArgumentException(string
					+ " is not a valid term_diff_search_option line!");
		}
	}

	public void setDiff_search_options(String string) {
		final String MARK_CHARS = "*#@^~$";
		final String DIFF_SEARCH_OPTION = "\\s*(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)";
		final Pattern diffSearchOptionPattern = Pattern.compile(DIFF_SEARCH_OPTION);

		Matcher match = diffSearchOptionPattern.matcher(string);
		if (!match.find()) {
			throw new IllegalArgumentException(string
					+ " is not a valid diff_search_option line!");
		}

		try {
			for (int i = 0; i < 6; i++) {
				dynamic_modification.getModifications()[i].setMarkChar(MARK_CHARS
						.charAt(i));
				dynamic_modification.getModifications()[i].setAddMass(Double
						.parseDouble(match.group(i * 2 + 1)));
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(string
					+ " is not a valid diff_search_option line : " + ex.getMessage());
		}
	}

	public int getPeptide_mass_units() {
		return peptide_mass_units;
	}

	public void setPeptide_mass_units(int peptide_mass_units) {
		this.peptide_mass_units = peptide_mass_units;
	}

	public EnzymeLimitType getEnzymeLimitType() {
		return enzymeLimitType;
	}

	public void setEnzymeLimitType(EnzymeLimitType enzymeLimitType) {
		this.enzymeLimitType = enzymeLimitType;
	}

	public int getMax_num_differential_per_peptide() {
		return max_num_differential_per_peptide;
	}

	public void setMax_num_differential_per_peptide(
			int max_num_differential_per_peptide) {
		this.max_num_differential_per_peptide = max_num_differential_per_peptide;
	}

	public boolean isPrecursorMonoisotopic() {
		return 1 == this.mass_type_parent;
	}

	public boolean isFragmentMonoisotopic() {
		return 1 == this.mass_type_fragment;
	}
}
