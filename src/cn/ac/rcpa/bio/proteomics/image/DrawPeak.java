package cn.ac.rcpa.bio.proteomics.image;

import java.awt.Color;

import cn.ac.rcpa.bio.proteomics.Peak;

public class DrawPeak implements IDrawPeak {
	public DrawPeak() {
		this.mz = 0.0;
		this.intensity = 0.0;
		this.label = null;
		this.color = Color.BLACK;
	}

	public DrawPeak(Peak source) {
		this.mz = source.getMz();
		this.intensity = source.getIntensity();
		this.label = null;
		this.color = Color.BLACK;
	}

	public DrawPeak(double mz, double intensity) {
		this.mz = mz;
		this.intensity = intensity;
		this.label = null;
		this.color = Color.BLACK;
	}

	public DrawPeak(Peak source, String label, Color color) {
		this.mz = source.getMz();
		this.intensity = source.getIntensity();
		this.label = label;
		this.color = color;
	}

	public DrawPeak(double mz, double intensity, String label, Color color) {
		this.mz = mz;
		this.intensity = intensity;
		this.label = label;
		this.color = color;
	}

	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String value) {
		this.label = value;
	}

	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color value) {
		this.color = value;
	}

	private double intensity;

	public double getIntensity() {
		return intensity;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	private double mz;

	public double getMz() {
		return mz;
	}

	public void setMz(double mz) {
		this.mz = mz;
	}
}
