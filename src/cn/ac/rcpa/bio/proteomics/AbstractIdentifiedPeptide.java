/*
 * Created on 2005-5-19
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public abstract class AbstractIdentifiedPeptide implements IIdentifiedPeptide {
	private List<String> proteinNames = new ArrayList<String>();

	private double observedMz;

	private double theoreticalSingleChargeMass;

	private double experimentalSingleChargeMass;

	private String sequence;

	private double pi;

	private FollowCandidatePeptideList candidates = new FollowCandidatePeptideList();

	public FollowCandidatePeptideList getFollowCandidates() {
		return candidates;
	}

	public void setFollowCandidates(FollowCandidatePeptideList candidates) {
		this.candidates = candidates;
	}

	public int getProteinNameCount() {
		return proteinNames.size();
	}

	public List<String> getProteinNames() {
		return Collections.unmodifiableList(proteinNames);
	}

	public void addProteinName(String proteinName) {
		if (!proteinNames.contains(proteinName)) {
			proteinNames.add(proteinName);
		}
	}

	public String getProteinName(int index) {
		return proteinNames.get(index);
	}

	public void removeProteinName(String proteinName) {
		proteinNames.remove(proteinName);
	}

	public void clearProteinNames() {
		proteinNames.clear();
	}

	public double getTheoreticalSingleChargeMass() {
		return theoreticalSingleChargeMass;
	}

	public void setTheoreticalSingleChargeMass(double singleChargeMass) {
		this.theoreticalSingleChargeMass = singleChargeMass;
	}

	public double getDiffToExperimentMass() {
		return getTheoreticalSingleChargeMass() - getExperimentalSingleChargeMass();
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
		this.pi = PeptideUtils.getPeptidePI(sequence);
	}

	public double getPI() {
		return pi;
	}

	public int compareTo(IIdentifiedPeptide arg0) {
		int result = this.getSequence().compareTo(arg0.getSequence());
		if (0 == result) {
			result = this.getPeakListInfo().getLongFilename().compareTo(
					arg0.getPeakListInfo().getLongFilename());
		}
		return result;
	}

	public double getExperimentalSingleChargeMass() {
		return experimentalSingleChargeMass;
	}

	public void setExperimentalSingleChargeMass(
			double experimentalSingleChargeMass) {
		this.experimentalSingleChargeMass = experimentalSingleChargeMass;
	}

	public double getObservedMz() {
		return observedMz;
	}

	public void setObservedMz(double observedMz) {
		this.observedMz = observedMz;
	}

	public int getCharge() {
		return getPeakListInfo().getCharge();
	}

	public void setCharge(int charge) {
		getPeakListInfo().setCharge(charge);
	}

}
