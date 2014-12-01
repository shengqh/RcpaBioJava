package cn.ac.rcpa.bio.proteomics.image.peptide;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class NeutralLossCandidates {
	private boolean bCanLossWater;
	private boolean bCanLossAmmonia;
	private boolean[] bLossWater;
	private boolean[] bLossAmmonia;
	private boolean[] yLossWater;
	private boolean[] yLossAmmonia;

	public boolean canLossWater() {
		return bCanLossWater;
	}

	public void setBCanLossWater(boolean canLossWater) {
		bCanLossWater = canLossWater;
	}

	public boolean canLossAmmonia() {
		return bCanLossAmmonia;
	}

	public void setBCanLossAmmonia(boolean canLossAmmonia) {
		bCanLossAmmonia = canLossAmmonia;
	}

	public boolean[] getBLossWater() {
		return bLossWater;
	}

	public void setBLossWater(boolean[] lossWater) {
		bLossWater = lossWater;
	}

	public boolean[] getBLossAmmonia() {
		return bLossAmmonia;
	}

	public void setBLossAmmonia(boolean[] lossAmmonia) {
		bLossAmmonia = lossAmmonia;
	}

	public boolean[] getYLossWater() {
		return yLossWater;
	}

	public void setYLossWater(boolean[] lossWater) {
		yLossWater = lossWater;
	}

	public boolean[] getYLossAmmonia() {
		return yLossAmmonia;
	}

	public void setYLossAmmonia(boolean[] lossAmmonia) {
		yLossAmmonia = lossAmmonia;
	}

	public NeutralLossCandidates(String peptide) {
		bCanLossWater = false;
		bCanLossAmmonia = false;
		bLossWater = new boolean[peptide.length()];
		bLossAmmonia = new boolean[peptide.length()];
		yLossWater = new boolean[peptide.length()];
		yLossAmmonia = new boolean[peptide.length()];
		
		String pureSeq = PeptideUtils.getPurePeptideSequence(peptide);
		for (int i = 0; i < pureSeq.length(); i++) {
			if (NeutralLossUtils.canLossWater(pureSeq.charAt(i))) {
				for (int j = i; j < pureSeq.length(); j++) {
					bLossWater[j] = true;
					bCanLossWater = true;
				}
				break;
			}
			bLossWater[i] = false;
		}

		for (int i = pureSeq.length() - 1; i >= 0; i--) {
			if (NeutralLossUtils.canLossWater(pureSeq.charAt(i))) {
				for (int j = i; j >= 0; j--) {
					yLossWater[pureSeq.length() - 1 - j] = true;
				}
				break;
			}
			yLossWater[pureSeq.length() - 1 - i] = false;
		}

		for (int i = 0; i < pureSeq.length(); i++) {
			if (NeutralLossUtils.canLossAmmonia(pureSeq.charAt(i))) {
				for (int j = i; j < pureSeq.length(); j++) {
					bLossAmmonia[j] = true;
					bCanLossAmmonia = true;
				}
				break;
			}
			bLossAmmonia[i] = false;
		}

		for (int i = pureSeq.length() - 1; i >= 0; i--) {
			if (NeutralLossUtils.canLossAmmonia(pureSeq.charAt(i))) {
				for (int j = i; j >= 0; j--) {
					yLossAmmonia[pureSeq.length() - 1 - j] = true;
				}
				break;
			}
			yLossAmmonia[pureSeq.length() - 1 - i] = false;
		}
	}
}
