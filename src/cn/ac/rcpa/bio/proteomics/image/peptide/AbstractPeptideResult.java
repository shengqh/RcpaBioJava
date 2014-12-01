/*
 * Created on 2005-11-1
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.PeakList;
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.MassCalculator;

public abstract class AbstractPeptideResult implements IIdentifiedPeptideResult {
	protected String peptide;

	protected IonType[] ionTypes;

	public AbstractPeptideResult(String peptide, IonType[] ionTypes) {
		this.ionTypes = ionTypes;
		setPeptide(peptide);
	}

	public AbstractPeptideResult(String peptide) {
		this.ionTypes = new IonType[] { IonType.B, IonType.Y, IonType.B2,
				IonType.Y2 };
		setPeptide(peptide);
	}

	private Map<String, String> scores = new LinkedHashMap<String, String>();

	public Map<String, String> getScoreMap() {
		return scores;
	}

	protected PeakList<MatchedPeak> peaks = new PeakList<MatchedPeak>();

	protected Map<IonType, List<MatchedPeak>> ionSeries = new HashMap<IonType, List<MatchedPeak>>();

	protected boolean precursorMonoMass = true;

	protected boolean peakMonoMass = true;

	protected double precursorMass = 0.0;

	protected Map<Character, Double> dynamicModification = new HashMap<Character, Double>();

	protected Map<Character, Double> staticModification = new HashMap<Character, Double>();

	public void initTheoreticalPeaks(boolean aPrecursorMonoMass,
			boolean aPeakMonoMass, Map<Character, Double> aStaticModification,
			Map<Character, Double> aDynamicModification)
			throws SequenceValidateException {
		this.precursorMonoMass = aPrecursorMonoMass;
		this.peakMonoMass = aPeakMonoMass;
		this.staticModification = aStaticModification;
		this.dynamicModification = aDynamicModification;

		MassCalculator mcPeak = new MassCalculator(aPeakMonoMass);
		mcPeak.addStaticModifications(aStaticModification);
		mcPeak.addDynamicModifications(aDynamicModification);

		ionSeries = mcPeak.getSeries(peptide, ionTypes,
				new MatchedPeakAllocator());

		for (IonType ionType : ionSeries.keySet()) {
			List<MatchedPeak> pkl = ionSeries.get(ionType);
			for (int i = 0; i < pkl.size(); i++) {
				pkl.get(i).setIonType(ionType);
				pkl.get(i).setIonIndex(i + 1);
			}
		}

		MassCalculator mcPrecursor = new MassCalculator(aPrecursorMonoMass);
		mcPrecursor.addStaticModifications(aStaticModification);
		mcPrecursor.addDynamicModifications(aDynamicModification);
		precursorMass = mcPrecursor.getMass(peptide);
		if (aPrecursorMonoMass) {
			precursorMass += MassCalculator.Hmono;
		} else {
			precursorMass += MassCalculator.Havg;
		}
	}

	public boolean isPeakMonoMass() {
		return peakMonoMass;
	}

	public Map<IonType, List<MatchedPeak>> getTheoreticalIonSeries() {
		return ionSeries;
	}

	public double getPrecursorMass() {
		return precursorMass;
	}

	public boolean isPrecursorMonoMass() {
		return precursorMonoMass;
	}

	public PeakList<MatchedPeak> getExperimentalPeakList() {
		return peaks;
	}

	public void setExperimentalPeakList(PeakList<MatchedPeak> value) {
		if (null == value) {
			peaks = new PeakList<MatchedPeak>();
		} else {
			peaks = value;
		}
	}

	public String getPeptide() {
		return peptide;
	}

	public void setPeptide(String value) {
		if (value == null) {
			this.peptide = "";
		} else {
			this.peptide = PeptideUtils.getMatchPeptideSequence(value);
		}
	}

	public Map<Character, Double> getDynamicModification() {
		return dynamicModification;
	}

	public Map<Character, Double> getStaticModification() {
		return staticModification;
	}

	public boolean isBy2Matched() {
		boolean result = false;
		if (getExperimentalPeakList().getCharge() > 1) {
			for (MatchedPeak peak : getExperimentalPeakList().getPeaks()) {
				if (peak.getIonType() == IonType.B2
						|| peak.getIonType() == IonType.Y2) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private double deltaScore;

	public double getDeltaScore() {
		return deltaScore;
	}

	private double score;

	public double getScore() {
		return score;
	}

	public void setDeltaScore(double deltaScore) {
		this.deltaScore = deltaScore;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
