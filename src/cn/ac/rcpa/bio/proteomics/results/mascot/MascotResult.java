package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IsotopicType;

public class MascotResult extends ArrayList<MascotProteinGroup> {
	private double pValue;

	private int pValueScore;

	private IsotopicType peakIsotopicType;

	private double peakTolerance;

	public double getPValue() {
		return pValue;
	}

	public void setPValue(double value) {
		pValue = value;
	}

	public int getPValueScore() {
		return pValueScore;
	}

	public void setPValueScore(int valueScore) {
		pValueScore = valueScore;
	}

	public List<MascotPeptide> getPeptides() {
		Map<Integer, MascotPeptide> queryPeptideMap = new HashMap<Integer, MascotPeptide>();
		for (MascotProteinGroup group : this) {
			for (MascotProtein protein : group) {
				for (MascotPeptide peptide : protein.getPeptides()) {
					queryPeptideMap.put(peptide.getQuery(), peptide);
				}
				break;
			}
		}

		ArrayList<MascotPeptide> result = new ArrayList<MascotPeptide>(
				queryPeptideMap.values());
		Collections.sort(result);
		return result;
	}

	public IsotopicType getPeakIsotopicType() {
		return peakIsotopicType;
	}

	public void setPeakIsotopicType(IsotopicType peakIsotopicType) {
		this.peakIsotopicType = peakIsotopicType;
	}

	public double getPeakTolerance() {
		return peakTolerance;
	}

	public void setPeakTolerance(double peakTolerance) {
		this.peakTolerance = peakTolerance;
	}
}
