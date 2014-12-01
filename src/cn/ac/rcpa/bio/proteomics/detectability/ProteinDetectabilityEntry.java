package cn.ac.rcpa.bio.proteomics.detectability;

import java.util.LinkedHashMap;
import java.util.Map;

import org.biojava.bio.seq.Sequence;

public class ProteinDetectabilityEntry {
	private String name;

	private Sequence sequence;

	private double probability;

	private double coverage;

	private Map<String, DetectabilityEntry> peptideMap = new LinkedHashMap<String, DetectabilityEntry>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public Map<String, DetectabilityEntry> getPeptideMap() {
		return peptideMap;
	}

	public int getUniquePeptideCount() {
		int result = 0;
		for (DetectabilityEntry de : peptideMap.values()) {
			if (de.isDetected()) {
				result++;
			}
		}
		return result;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public void calculateCoverage() throws IllegalAccessException {
		if (sequence == null) {
			throw new IllegalAccessException("Assign sequence first!");
		}

		if (sequence.seqString().length() == 0) {
			throw new IllegalAccessException("Sequence is empty!");
		}

		boolean[] masked = new boolean[sequence.seqString().length()];
		for (int i = 0; i < masked.length; i++) {
			masked[i] = false;
		}

		for (String peptide : peptideMap.keySet()) {
			DetectabilityEntry de = peptideMap.get(peptide);
			if (!de.isDetected()) {
				continue;
			}

			int ipos = sequence.seqString().indexOf(peptide);
			if (ipos < 0) {
				throw new IllegalAccessException("Peptide " + peptide
						+ " is not contained in protein " + sequence.getName());
			}

			for (int i = ipos; i < ipos + peptide.length(); i++) {
				masked[i] = true;
			}
		}

		double totalMasked = 0.0;
		for (boolean mask : masked) {
			if (mask) {
				totalMasked += 1.0;
			}
		}

		this.coverage = totalMasked / sequence.seqString().length();
	}

	public double getCoverage() {
		return coverage;
	}
}
