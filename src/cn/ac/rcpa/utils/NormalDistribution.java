package cn.ac.rcpa.utils;

import java.text.DecimalFormat;

public class NormalDistribution {
	private double mean;

	private double sampleVariance;

	private int sampleCount;

	public NormalDistribution(double mean, double sampleVariance, int sampleCount) {
		super();
		this.mean = mean;
		this.sampleVariance = sampleVariance;
		this.sampleCount = sampleCount;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public double getSampleVariance() {
		return sampleVariance;
	}

	public void setSampleVariance(double sampleVariance) {
		this.sampleVariance = sampleVariance;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("0.##");
		sb.append("[");
		sb.append(df.format(mean));
		sb.append(" , ");
		sb.append(df.format(sampleVariance));
		sb.append(" , ");
		sb.append(sampleCount);
		sb.append("]");
		return sb.toString();
	}

}
