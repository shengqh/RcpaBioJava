package cn.ac.rcpa.bio.proteomics.image;

import java.awt.Color;

public interface IDrawPeak {
	double getMz();

	void setMz(double value);

	double getIntensity();

	void setIntensity(double value);

	String getLabel();

	void setLabel(String value);

	Color getColor();

	void setColor(Color value);
}
