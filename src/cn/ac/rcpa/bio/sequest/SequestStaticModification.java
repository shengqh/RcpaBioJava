package cn.ac.rcpa.bio.sequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.ac.rcpa.bio.aminoacid.Aminoacid;
import cn.ac.rcpa.bio.aminoacid.AminoacidModification;
import cn.ac.rcpa.bio.aminoacid.Aminoacids;

public class SequestStaticModification {
	private double add_Cterm_peptide;

	private double add_Cterm_protein;

	private double add_Nterm_peptide;

	private double add_Nterm_protein;

	private AminoacidModification[] add_aminoacids = new AminoacidModification[128];

	public double getAdd_Cterm_protein() {
		return add_Cterm_protein;
	}

	public double getAdd_Nterm_protein() {
		return add_Nterm_protein;
	}

	public double getAdd_Nterm_peptide() {
		return add_Nterm_peptide;
	}

	public void setAdd_Cterm_peptide(double add_Cterm_peptide) {
		this.add_Cterm_peptide = add_Cterm_peptide;
	}

	public void setAdd_Cterm_protein(double add_Cterm_protein) {
		this.add_Cterm_protein = add_Cterm_protein;
	}

	public void setAdd_Nterm_protein(double add_Nterm_protein) {
		this.add_Nterm_protein = add_Nterm_protein;
	}

	public void setAdd_Nterm_peptide(double add_Nterm_peptide) {
		this.add_Nterm_peptide = add_Nterm_peptide;
	}

	public double getAdd_Cterm_peptide() {
		return add_Cterm_peptide;
	}

	public SequestStaticModification() {
		doInitTermModification();
		doInitAminoacidModification();
	}

	public void init() {
		doInitTermModification();
		doInitAminoacidModification();
	}

	private void doInitTermModification() {
		add_Cterm_peptide = 0.0;
		add_Cterm_protein = 0.0;
		add_Nterm_peptide = 0.0;
		add_Nterm_protein = 0.0;
	}

	private void doInitAminoacidModification() {
		for (int i = 0; i < add_aminoacids.length; i++) {
			add_aminoacids[i] = null;
		}

		add_aminoacids['A'] = new AminoacidModification('A', 0.0);
		add_aminoacids['B'] = new AminoacidModification('B', 0.0);
		add_aminoacids['C'] = new AminoacidModification('C', 0.0);
		add_aminoacids['D'] = new AminoacidModification('D', 0.0);
		add_aminoacids['E'] = new AminoacidModification('E', 0.0);
		add_aminoacids['F'] = new AminoacidModification('F', 0.0);
		add_aminoacids['G'] = new AminoacidModification('G', 0.0);
		add_aminoacids['H'] = new AminoacidModification('H', 0.0);
		add_aminoacids['I'] = new AminoacidModification('I', 0.0);
		add_aminoacids['K'] = new AminoacidModification('K', 0.0);
		add_aminoacids['L'] = new AminoacidModification('L', 0.0);
		add_aminoacids['M'] = new AminoacidModification('M', 0.0);
		add_aminoacids['N'] = new AminoacidModification('N', 0.0);
		add_aminoacids['O'] = new AminoacidModification('O', 0.0);
		add_aminoacids['P'] = new AminoacidModification('P', 0.0);
		add_aminoacids['Q'] = new AminoacidModification('Q', 0.0);
		add_aminoacids['R'] = new AminoacidModification('R', 0.0);
		add_aminoacids['S'] = new AminoacidModification('S', 0.0);
		add_aminoacids['T'] = new AminoacidModification('T', 0.0);
		add_aminoacids['V'] = new AminoacidModification('V', 0.0);
		add_aminoacids['W'] = new AminoacidModification('W', 0.0);
		add_aminoacids['X'] = new AminoacidModification('X', 0.0);
		add_aminoacids['Y'] = new AminoacidModification('Y', 0.0);
		add_aminoacids['Z'] = new AminoacidModification('Z', 0.0);
	}

	public AminoacidModification[] getAminoacidModification() {
		ArrayList<AminoacidModification> result = new ArrayList<AminoacidModification>();
		for (int i = 0; i < add_aminoacids.length; i++) {
			if (add_aminoacids[i] != null && add_aminoacids[i].getAddMass() != 0.0) {
				result.add(add_aminoacids[i]);
			}
		}
		return (AminoacidModification[]) result
				.toArray(new AminoacidModification[0]);
	}

	public double getAminoacidModification(char aminoacid) {
		if (add_aminoacids[aminoacid] == null) {
			throw new IllegalArgumentException("Character " + aminoacid
					+ " is not a valid aminoacid!");
		}

		return add_aminoacids[aminoacid].getAddMass();
	}

	public void setAminoacidModification(char aminoacid, double addMass) {
		if (add_aminoacids[aminoacid] == null) {
			throw new IllegalArgumentException("Character " + aminoacid
					+ " is not a valid aminoacid!");
		}

		add_aminoacids[aminoacid].setAddMass(addMass);
	}

	public Map<Character, Double> getModificationMap(boolean monoIsotopic) {
		Map<Character, Double> result = new HashMap<Character, Double>();
		Aminoacids aas = new Aminoacids();

		for (AminoacidModification entry : add_aminoacids) {
			if (null == entry || 0.0 == entry.getAddMass()) {
				continue;
			}

			Aminoacid aa = aas.get(entry.getAminoacid());
			double aaMass = monoIsotopic ? aa.getMonoMass() : aa.getAverageMass();
			result.put(entry.getAminoacid(), aaMass + entry.getAddMass());
		}

		return result;
	}

}
