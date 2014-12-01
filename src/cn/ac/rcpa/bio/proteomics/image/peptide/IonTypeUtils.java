package cn.ac.rcpa.bio.proteomics.image.peptide;

import cn.ac.rcpa.bio.proteomics.IonType;

public class IonTypeUtils {
	public static String getDisplayIonType(IonType ionType) {
		if (ionType == IonType.B2 || ionType == IonType.Y2) {
			return ionType.toString().substring(0, 1).toLowerCase();
		} else {
			return ionType.toString().toLowerCase();
		}
	}
}
