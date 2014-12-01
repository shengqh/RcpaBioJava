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
package cn.ac.rcpa.bio.proteomics.image;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;

public class SpectrumImageBuilder {
	private DrawPeakImageBuilder pklBuilder;

	private Dimension dimension;

	private Color defaultColor;

	private Color labeledColor;

	public SpectrumImageBuilder(Dimension dimension, Color defaultColor,
			Color labeledColor) {
		super();
		this.pklBuilder = new DrawPeakImageBuilder();
		this.dimension = dimension;
		this.defaultColor = defaultColor;
		this.labeledColor = labeledColor;
	}

	private PeakList<Peak> peaks = new PeakList<Peak>();

	public PeakList<Peak> getPeaks() {
		return peaks;
	}

	public void setPeaks(PeakList<Peak> peaks) {
		this.peaks = peaks;
	}

	public void setFixMaxIntensity(double fixMaxIntensity) {
		pklBuilder.setFixMaxIntensity(fixMaxIntensity);
	}

	public void drawImage(String filename) throws Exception {
		FileOutputStream fos = new FileOutputStream(filename);
		try {
			drawImage(fos);
		} finally {
			fos.close();
		}
	}

	public void drawImage(OutputStream os) throws Exception {
		List<IDrawPeak> dPeaks = DrawPeakUtils.convertToDrawPeak(peaks.getPeaks(),
				defaultColor, labeledColor);
		pklBuilder.draw(os, dimension, dPeaks);
	}
}
