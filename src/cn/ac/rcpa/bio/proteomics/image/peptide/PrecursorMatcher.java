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
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.PeakList;

public class PrecursorMatcher implements IPeakMatcher {
	private double peakMzTolerance;

	private double minIntensityScale;

	public PrecursorMatcher(double peakMzTolerance) {
		this.peakMzTolerance = peakMzTolerance;
		this.minIntensityScale = 0.3;
	}

	public PrecursorMatcher(double tolerance, double minIntensityScale) {
		this.peakMzTolerance = tolerance;
		this.minIntensityScale = minIntensityScale;
	}

	public void match(IIdentifiedPeptideResult sr) {
		final PeakList<MatchedPeak> peaks = sr.getExperimentalPeakList();

		final double precursorMH = peaks.getPrecursor();

		final int charge = peaks.getCharge();

		final List<MatchedPeak> precursor = getPrecursorMz(precursorMH, charge);

		final double minIntensity = peaks.getMaxIntensity() * minIntensityScale;

		MatchedPeakUtils.match(peaks.getPeaks(), precursor, peakMzTolerance,
				minIntensity);
	}

	/**
	 * Consider precursor and precursor lost a H2O.
	 * 
	 * @param precursor
	 * @param charge
	 * @param nlTypes
	 * @return
	 */
	private List<MatchedPeak> getPrecursorMz(final double precursor,
			final int charge) {
		ArrayList<MatchedPeak> result = new ArrayList<MatchedPeak>();
		double nlMass = (precursor + charge - 1.0) / charge;
		MatchedPeak nlPeak = new MatchedPeak(nlMass, 1.0, charge);
		nlPeak.setIonIndex(0);
		nlPeak.setIonType(IonType.PRECURSOR);
		nlPeak.setCharge(charge);
		result.add(nlPeak);
		return result;
	}
}
