/*
 * Created on 2005-11-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.PeakList;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class PhosphoNeutralLossMatcher extends AbstractMatcher {
	private Map<Character, Double> dynamicModifications;

	private double minPrecursorNeutralLossIntensityScale;

	private double minBYNeutralLossIntensityScale;

	private double mzTolerance;

	private int NeutralLossLevel = 2;

	public PhosphoNeutralLossMatcher(
			Map<Character, Double> dynamicModifications, double mzTolerance,
			double minPrecursorNeutralLossIntensityScale,
			double minBYNeutralLossIntensityScale) {
		this.dynamicModifications = dynamicModifications;
		this.mzTolerance = mzTolerance;
		this.minPrecursorNeutralLossIntensityScale = minPrecursorNeutralLossIntensityScale;
		this.minBYNeutralLossIntensityScale = minBYNeutralLossIntensityScale;
	}

	public PhosphoNeutralLossMatcher(
			Map<Character, Double> dynamicModifications, double mzTolerance) {
		this.dynamicModifications = dynamicModifications;
		this.mzTolerance = mzTolerance;
		this.minPrecursorNeutralLossIntensityScale = 0.3;
		this.minBYNeutralLossIntensityScale = 0.1;
	}

	private boolean isPhospho(String peptide, int aminoacidIndex) {
		if (dynamicModifications == null) {
			return false;
		}

		if (!PeptideUtils.isModified(peptide, aminoacidIndex)) {
			return false;
		}

		Character c = peptide.charAt(aminoacidIndex + 1);

		if (dynamicModifications.containsKey(c)) {
			double value = dynamicModifications.get(c);
			if (Math.abs(value - (80.0)) < 0.1) {
				return true;
			}
		}
		return false;
	}

	public void match(IIdentifiedPeptideResult sr) {
		PeakList<MatchedPeak> peaks = sr.getExperimentalPeakList();

		double maxIntensity = peaks.getMaxIntensity();

		double precursorMH = peaks.getPrecursor();

		int precursorCharge = peaks.getCharge();

		String peptide = sr.getPeptide();

		NeutralLossCandidates candidates = new NeutralLossCandidates(peptide);

		List<INeutralLossType> nlTypes = getPhosphoNeutralLossCandidates(peptide);
		if (candidates.canLossWater()) {
			nlTypes.add(NL_WATER);
		}
		if (candidates.canLossAmmonia()) {
			nlTypes.add(NL_AMMONIA);
		}

		List<MatchedPeak> precursorNeutralLossCandidates = getPrecursorNeutralLossMz(
				precursorMH, precursorCharge, nlTypes);

		final double minNeutralLossIntensity = maxIntensity
				* minPrecursorNeutralLossIntensityScale;
		MatchedPeakUtils.match(peaks.getPeaks(),
				precursorNeutralLossCandidates, mzTolerance,
				minNeutralLossIntensity);

		final double minBYNeutralLossIntensity = maxIntensity
				* minBYNeutralLossIntensityScale;
		List<MatchedPeak> byNeutralLosses = getBYNeutralLossMz(sr, candidates);
		MatchedPeakUtils.match(peaks.getPeaks(), byNeutralLosses, mzTolerance,
				minBYNeutralLossIntensity);
	}

	private List<MatchedPeak> getBYNeutralLossMz(IIdentifiedPeptideResult sr,
			NeutralLossCandidates candidates) {
		List<MatchedPeak> result = new ArrayList<MatchedPeak>();

		Map<Integer, String> bModifiedPosition = getModificationPositionMapB(sr);
		Map<Integer, String> yModifiedPosition = getPhosphoModificationPositionMapY(sr);

		addNeutralLoss(result, sr, IonType.B, 1, bModifiedPosition, candidates
				.getBLossAmmonia(), candidates.getBLossWater());
		addNeutralLoss(result, sr, IonType.Y, 1, yModifiedPosition, candidates
				.getYLossAmmonia(), candidates.getYLossWater());

		if (sr.getExperimentalPeakList().getCharge() > 1) {
			addNeutralLoss(result, sr, IonType.B2, 2, bModifiedPosition,
					candidates.getBLossAmmonia(), candidates.getBLossWater());
			addNeutralLoss(result, sr, IonType.Y2, 2, yModifiedPosition,
					candidates.getYLossAmmonia(), candidates.getYLossWater());
		}

		return result;
	}

	private void addNeutralLoss(List<MatchedPeak> result,
			IIdentifiedPeptideResult sr, IonType ionType, int charge,
			Map<Integer, String> modifiedPosition, boolean[] canLossAmmonia,
			boolean[] canLossWater) {

		List<MatchedPeak> peaks = sr.getTheoreticalIonSeries().get(ionType);

		for (int i = 0; i < peaks.size(); i++) {
			List<INeutralLossType> nlTypes = new ArrayList<INeutralLossType>();

			String modified = modifiedPosition.get(i);
			for (int j = 0; j < modified.length(); j++) {
				if (modified.charAt(j) == 'S' || modified.charAt(j) == 'T') {
					nlTypes.add(NL_H3PO4);
				} else if (modified.charAt(j) == 'Y') {
					nlTypes.add(NL_HPO3);
				}
			}

			if (canLossWater[i]) {
				nlTypes.add(NL_WATER);
			}
			if (canLossAmmonia[i]) {
				nlTypes.add(NL_AMMONIA);
			}

			if (nlTypes.size() == 0) {
				continue;
			}

			List<INeutralLossType> candidates = NeutralLossGenerator
					.getTotalCombinationValues(nlTypes, NeutralLossLevel);

			for (INeutralLossType aType : candidates) {
				double nlMass = peaks.get(i).getMz() - aType.getMass() / charge;
				MatchedPeak nlPeak = new MatchedPeak(nlMass, 1.0, charge);
				if (aType.getName().contains("P")) {
					nlPeak.setIonType(IonType.NEUTRAL_LOSS_PHOSPHO);
				} else {
					nlPeak.setIonType(IonType.NEUTRAL_LOSS);
				}
				nlPeak.setDisplayName("["
						+ IonTypeUtils.getDisplayIonType(ionType)
						+ peaks.get(i).getIonIndex() + "-" + aType.getName()
						+ "]");
				nlPeak.setCharge(charge);
				result.add(nlPeak);
			}
		}
	}

	private Map<Integer, String> getModificationPositionMapB(
			IIdentifiedPeptideResult sr) {
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();
		String peptide = PeptideUtils.getMatchPeptideSequence(sr.getPeptide());
		int bIndex = -1;
		String lastModified = "";
		for (int i = 0; i < peptide.length(); i++) {
			if (!Character.isLetter(peptide.charAt(i))) {
				continue;
			}

			bIndex++;
			if (isPhospho(peptide, i)) {
				lastModified = lastModified + peptide.charAt(i);
			}
			result.put(bIndex, lastModified);
		}
		return result;
	}

	private Map<Integer, String> getPhosphoModificationPositionMapY(
			IIdentifiedPeptideResult sr) {
		Map<Integer, String> yModifiedPosition = new LinkedHashMap<Integer, String>();
		String peptide = PeptideUtils.getMatchPeptideSequence(sr.getPeptide());
		int yIndex = -1;
		String lastModified = "";
		for (int i = peptide.length() - 1; i >= 0; i--) {
			if (!Character.isLetter(peptide.charAt(i))) {
				continue;
			}

			yIndex++;
			if (isPhospho(peptide, i)) {
				lastModified = lastModified + peptide.charAt(i);
			}
			yModifiedPosition.put(yIndex, lastModified);
		}
		return yModifiedPosition;
	}

	/**
	 * Consider precursor lost a phopholation-site and lost a H2O again.
	 * 
	 * @param precursorMH
	 * @param precursorCharge
	 * @param nlTypes
	 * @return
	 */
	private List<MatchedPeak> getPrecursorNeutralLossMz(
			final double precursorMH, final int precursorCharge,
			List<INeutralLossType> nlTypes) {

		List<INeutralLossType> candidates = NeutralLossGenerator
				.getTotalCombinationValues(nlTypes, NeutralLossLevel);

		ArrayList<MatchedPeak> result = new ArrayList<MatchedPeak>();

		for (INeutralLossType aType : candidates) {
			double nlMass = (precursorMH + (precursorCharge - 1) * massH - aType
					.getMass())
					/ precursorCharge;
			MatchedPeak nlPeak = new MatchedPeak(nlMass, 1.0, precursorCharge);

			if (aType.getName().contains("PO")) {
				nlPeak.setIonType(IonType.PRECURSOR_NEUTRAL_LOSS_PHOSPHO);
			} else {
				nlPeak.setIonType(IonType.PRECURSOR_NEUTRAL_LOSS);
			}
			nlPeak.setDisplayName("[MH" + precursorCharge + "-"
					+ aType.getName() + "]");
			result.add(nlPeak);
		}

		return result;
	}

	private List<INeutralLossType> getPhosphoNeutralLossCandidates(
			String peptide) {
		List<INeutralLossType> result = new ArrayList<INeutralLossType>();
		for (int i = 0; i < peptide.length(); i++) {
			if (isPhospho(peptide, i)) {
				char modifiedAminoacid = peptide.charAt(i);

				if (('S' == modifiedAminoacid || 'T' == modifiedAminoacid)) {
					result.add(NL_H3PO4);
				} else if ('Y' == modifiedAminoacid) {
					result.add(NL_HPO3);
				}
			}
		}
		return result;
	}
}
