package cn.ac.rcpa.bio.sequest;

import java.util.HashMap;
import java.util.Map;

public class SequestDynamicModification {
	private double add_Cterm_peptide = 0.0;

	private double add_Nterm_peptide = 0.0;

	private SequestDynamicModificationEntry[] add_markChars;

	public double getAdd_Nterm_peptide() {
		return add_Nterm_peptide;
	}

	public void setAdd_Cterm_peptide(double add_Cterm_peptide) {
		this.add_Cterm_peptide = add_Cterm_peptide;
	}

	public void setAdd_Nterm_peptide(double add_Nterm_peptide) {
		this.add_Nterm_peptide = add_Nterm_peptide;
	}

	public double getAdd_Cterm_peptide() {
		return add_Cterm_peptide;
	}

	public SequestDynamicModification() {
		doInit();
	}

	public void init() {
		doInit();
	}

	private void doInit() {
		add_Cterm_peptide = 0.0;
		add_Nterm_peptide = 0.0;
		add_markChars = new SequestDynamicModificationEntry[]{ 
				new SequestDynamicModificationEntry('*', 0.0),
				new SequestDynamicModificationEntry('#', 0.0),
				new SequestDynamicModificationEntry('@', 0.0),
				new SequestDynamicModificationEntry('^', 0.0),
				new SequestDynamicModificationEntry('~', 0.0),
				new SequestDynamicModificationEntry('$', 0.0)};
	}

	public final SequestDynamicModificationEntry[] getModifications() {
		return add_markChars;
	}

	public Map<Character, Double> getModificationMap() {
		Map<Character, Double> result = new HashMap<Character, Double>();

		for (SequestDynamicModificationEntry entry : add_markChars) {
			if (0.0 == entry.getAddMass()) {
				continue;
			}

			result.put(entry.getMarkChar(), entry.getAddMass());
		}

		return result;
	}
}
