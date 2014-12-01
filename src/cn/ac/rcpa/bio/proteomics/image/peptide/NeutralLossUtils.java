package cn.ac.rcpa.bio.proteomics.image.peptide;

public class NeutralLossUtils {
	private NeutralLossUtils() {
	}

	public static boolean canLossWater(char aminoacid) {
		return aminoacid == 'S' || aminoacid == 'T';
	}

	public static boolean canLossWater(String peptide) {
		for (int i = 0; i < peptide.length(); i++) {
			if (canLossWater(peptide.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean canLossAmmonia(char aminoacid) {
		return aminoacid == 'R' || aminoacid == 'K' || aminoacid == 'N'
				|| aminoacid == 'Q';
	}

	public static boolean canLossAmmonia(String peptide) {
		for (int i = 0; i < peptide.length(); i++) {
			if (canLossAmmonia(peptide.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
