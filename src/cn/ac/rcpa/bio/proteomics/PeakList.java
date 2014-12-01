package cn.ac.rcpa.bio.proteomics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.ac.rcpa.bio.utils.MassCalculator;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class PeakList<T extends Peak> {
	private double precursorMZ;

	private int charge;

	private List<T> peaks;

	private int firstScan;

	private int lastScan;

	private double retentionTime;

	private double intensity;

	private String experimental;

	public String getExperimental() {
		return experimental;
	}

	public void setExperimental(String experimental) {
		this.experimental = experimental;
	}

	public PeakList() {
		precursorMZ = 0.0;
		charge = 0;
		firstScan = 0;
		lastScan = 0;
		intensity = 0;
		peaks = new ArrayList<T>();
	}

	public double getPrecursor() {
		if (0 == charge) {
			return precursorMZ;
		} else {
			return precursorMZ * charge - (charge - 1) * MassCalculator.Hmono;
		}
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public void setPrecursorMZ(double precursorMZ) {
		this.precursorMZ = precursorMZ;
	}

	public double getPrecursorMZ() {
		return precursorMZ;
	}

	public void setPrecursorAndCharge(double precursor, int charge) {
		if (0 >= charge) {
			throw new IllegalArgumentException(
					"Charge should be larger than zero");
		}

		this.charge = charge;
		this.precursorMZ = (precursor + (charge - 1) * MassCalculator.Hmono)
				/ charge;
	}

	public void setFirstScan(int firstScan) {
		this.firstScan = firstScan;
	}

	public void setLastScan(int lastScan) {
		this.lastScan = lastScan;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public int getCharge() {
		return charge;
	}

	public int getFirstScan() {
		return firstScan;
	}

	public int getLastScan() {
		return lastScan;
	}

	public double getIntensity() {
		return intensity;
	}

	public List<T> getPeaks() {
		return peaks;
	}

	public double getMinPeak() {
		double result = Double.MAX_VALUE;
		for (Peak peak : peaks) {
			if (peak.getMz() < result) {
				result = peak.getMz();
			}
		}
		return result;
	}

	public double getMaxPeak() {
		double result = Double.MIN_VALUE;
		for (Peak peak : peaks) {
			if (peak.getMz() > result) {
				result = peak.getMz();
			}
		}
		return result;
	}

	public double getMinIntensity() {
		double result = Double.MAX_VALUE;
		for (Peak peak : peaks) {
			if (peak.getIntensity() < result) {
				result = peak.getIntensity();
			}
		}
		return result;
	}

	public Peak getMaxIntensityPeak() {
		Peak result = new Peak(0.0, 0.0);
		for (Peak peak : peaks) {
			if (peak.getIntensity() > result.getIntensity()) {
				result = peak;
			}
		}
		return result;
	}

	public double getMaxIntensity() {
		double result = Double.MIN_VALUE;
		for (Peak peak : peaks) {
			if (peak.getIntensity() > result) {
				result = peak.getIntensity();
			}
		}
		return result;
	}

	public double getTotalIntensity() {
		double result = 0.0;
		for (Peak peak : peaks) {
			result += peak.getIntensity();
		}
		return result;
	}

	public List<Peak> getPeakByAnnotation(String annotation) {
		List<Peak> result = new ArrayList<Peak>();
		for (Peak peak : peaks) {
			if (peak.getAnnotations().contains(annotation)) {
				result.add(peak);
			}
		}
		return result;
	}

	public void addOrMergePeak(T peak, double peakThreshold) {
		for (Peak curPeak : peaks) {
			if (Math.abs(curPeak.getMz() - peak.getMz()) <= peakThreshold) {
				curPeak.getAnnotations().addAll(peak.getAnnotations());
				return;
			}
		}

		peaks.add(peak);
	}

	private static DecimalFormat df = new DecimalFormat("0.0000");

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PrecursorMZ=" + df.format(getPrecursor()) + "; Charge="
				+ charge + "\n");
		for (Peak peak : peaks) {
			sb.append(peak.toString() + "\n");
		}
		return sb.toString().trim();
	}

	public void sort() {
		Collections.sort(peaks);
	}

	public void sort(Comparator<? super Peak> c) {
		Collections.sort(peaks, c);
	}

	public void clearAnnotation() {
		for (Peak peak : peaks) {
			peak.getAnnotations().clear();
		}
	}

	public double getRetentionTime() {
		return retentionTime;
	}

	public void setRetentionTime(double retentionTime) {
		this.retentionTime = retentionTime;
	}

}
