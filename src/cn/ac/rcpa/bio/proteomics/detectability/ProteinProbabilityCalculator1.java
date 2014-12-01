package cn.ac.rcpa.bio.proteomics.detectability;

import java.util.ArrayList;
import java.util.List;

public class ProteinProbabilityCalculator1 implements
		IProteinProbabilityCalculator {
	public double getProbability(ProteinDetectabilityEntry protein) {
		List<DetectabilityEntry> des = new ArrayList<DetectabilityEntry>(protein
				.getPeptideMap().values());

		double minDetectability = 1.1;
		for (DetectabilityEntry de : des) {
			if (de.isDetected() && de.getDetectability() < minDetectability) {
				minDetectability = de.getDetectability();
			}
		}

		List<DetectabilityEntry> candidateDes = new ArrayList<DetectabilityEntry>();
		for (DetectabilityEntry de : des) {
			if (de.getDetectability() >= minDetectability) {
				candidateDes.add(de);
			}
		}

		if (0 == candidateDes.size()) {
			return 0;
		}

		double ppro = 0.5;
		double ppostpro_exist = 1.0;
		for (DetectabilityEntry de : candidateDes) {
			if (de.isDetected()) {
				// ppostpro_exist *= (1 - de.getScore());
				ppostpro_exist *= de.getDetectability();
			} else {
				ppostpro_exist *= (1 - de.getDetectability());
			}
		}

		double ppostpro_notexist = 1.0;
		for (DetectabilityEntry de : des) {
			if (de.isDetected()) {
				ppostpro_notexist *= de.getScore();
			}
		}

		double result = ppro * ppostpro_exist
				/ (ppro * ppostpro_exist + (1 - ppro) * ppostpro_notexist);

		return result;
	}
}
