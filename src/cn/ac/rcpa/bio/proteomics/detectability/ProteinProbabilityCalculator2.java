package cn.ac.rcpa.bio.proteomics.detectability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProteinProbabilityCalculator2 implements
		IProteinProbabilityCalculator {

	public static double getProbabilityFrom(List<DetectabilityEntry> des,
			int index) {
		double ppro = 0.5;
		double ppostpro_exist = 1.0;
		for (int i = 0; i < index; i++) {
			DetectabilityEntry de = des.get(i);
			if (de.isDetected()) {
				ppostpro_exist *= (1 - de.getScore());
			} else {
				ppostpro_exist *= (1 - de.getDetectability());
			}
		}
		ppostpro_exist *= 1 - des.get(index).getScore();

		double ppostpro_notexist = 1.0;
		for (int i = 0; i <= index; i++) {
			DetectabilityEntry de = des.get(i);
			if (de.isDetected()) {
				ppostpro_notexist *= de.getScore();
			}
		}

		double result = ppro * ppostpro_exist
				/ (ppro * ppostpro_exist + (1 - ppro) * ppostpro_notexist);

		return result;
	}

	public double getProbability(ProteinDetectabilityEntry protein) {
		List<DetectabilityEntry> des = new ArrayList<DetectabilityEntry>(protein
				.getPeptideMap().values());
		Collections.sort(des, new Comparator<DetectabilityEntry>() {
			public int compare(DetectabilityEntry o1, DetectabilityEntry o2) {
				return Double.compare(o2.getDetectability(), o1.getDetectability());
			}
		});

		double result = 1.0;
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i).isDetected()) {
				double p = getProbabilityFrom(des, i);
				// System.out.println(i + "-" + p);
				result *= (1 - p);
			}
		}

		result = 1.0 - result;
		return result;
	}
}
