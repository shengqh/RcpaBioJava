package cn.ac.rcpa.bio.proteomics.detectability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProteinProbabilityCalculatorMDepth implements
		IProteinProbabilityCalculator {
	public double getProbability(ProteinDetectabilityEntry protein) {
		List<DetectabilityEntry> des = new ArrayList<DetectabilityEntry>(protein
				.getPeptideMap().values());

		Collections.sort(des, new Comparator<DetectabilityEntry>() {
			public int compare(DetectabilityEntry o1, DetectabilityEntry o2) {
				return Double.compare(o2.getDetectability(), o1.getDetectability());
			}
		});

		int totalDetected = 0;
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i).isDetected()) {
				totalDetected++;
			}
		}

		int alreadyDetected = 0;
		int nonDetected = 0;
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i).isDetected()) {
				alreadyDetected++;
				des.get(i)
						.setMDepthCount(totalDetected - alreadyDetected + nonDetected);
			} else {
				nonDetected++;
			}
		}

		int minMDepthIndex = -1;
		int minMDepth = Integer.MAX_VALUE;
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i).isDetected()) {
				if (minMDepth >= des.get(i).getMDepthCount()) {
					minMDepth = des.get(i).getMDepthCount();
					minMDepthIndex = i;
				}
			}
		}

		List<DetectabilityEntry> candidateDes = new ArrayList<DetectabilityEntry>();
		for (int i = 0; i <= minMDepthIndex; i++) {
			candidateDes.add(des.get(i));
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
