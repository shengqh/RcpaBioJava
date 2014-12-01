/*
 * Created on 2005-11-7
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.Peak;

public class MatchedPeak extends Peak {
	public MatchedPeak() {
	}

	public MatchedPeak(double mz, double intensity, int charge) {
		super(mz, intensity);
		this.charge = charge;
	}

	private String displayName = null;

	private boolean matched;

	private IonType ionType;

	private int charge;

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	private int ionIndex;

	private double matchedMZ;

	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public int getIonIndex() {
		return ionIndex;
	}

	public void setIonIndex(int ionIndex) {
		this.ionIndex = ionIndex;
	}

	public IonType getIonType() {
		return ionType;
	}

	public void setIonType(IonType ionType) {
		this.ionType = ionType;
	}

	public double getMatchedMZ() {
		return matchedMZ;
	}

	public void setMatchedMZ(double matchedMZ) {
		this.matchedMZ = matchedMZ;
	}

	@Override
	public String toString() {
		if (displayName != null) {
			return displayName + "-" + getMz();
		}

		if (ionType != null) {
			return ionType.toString() + ionIndex + "-" + getMz();
		}

		return String.valueOf(getMz());
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
