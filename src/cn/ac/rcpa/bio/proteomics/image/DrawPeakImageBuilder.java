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
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

public class DrawPeakImageBuilder implements IDrawPeakImageBuilder {
	private static final double NINETY_DEGREES = Math.toRadians(90.0);

	private int maxMz = Integer.MAX_VALUE;

	private int maxIntensity = Integer.MAX_VALUE;

	private BufferedImage image;

	private Graphics2D g2;

	private Dimension dimension;

	private int fontHeight;

	private FontRenderContext frc;

	private int top;

	private int bottom;

	private int left;

	private int right;

	private double scaleWidth;

	private double scaleHeight;

	private static int[] steps = new int[] { 10, 25, 50 };

	private double fixMaxIntensity = 0;

	public void setFixMaxIntensity(double fixMaxIntensity) {
		this.fixMaxIntensity = fixMaxIntensity;
	}

	private void initMaxMzIntensity(List<IDrawPeak> peaks) {
		maxMz = 0;

		for (IDrawPeak peak : peaks) {
			if (maxMz < peak.getMz()) {
				maxMz = (int) peak.getMz();
			}
		}
		maxMz = (maxMz / 100 + 1) * 100;

		if (0 != fixMaxIntensity) {
			maxIntensity = (int) fixMaxIntensity;
		} else {
			maxIntensity = 0;
			for (IDrawPeak peak : peaks) {
				if (maxIntensity < peak.getIntensity()) {
					maxIntensity = (int) peak.getIntensity();
				}
			}
		}
	}

	private void initImage(List<IDrawPeak> peaks) {
		image = new BufferedImage(dimension.width, dimension.height,
				BufferedImage.TYPE_INT_RGB);
		g2 = image.createGraphics();

		initMaxMzIntensity(peaks);

		initImageVariables();

		clearImage();

		drawXScale();

		drawYScale();

		drawPeaks(peaks);

		g2.dispose();
	}

	private void doDrawPeak(IDrawPeak peak) {
		int curMz = getTransformedX(peak.getMz());
		int curIntensity = getTransformedY(peak.getIntensity());

		drawPeakLine(peak, curMz, curIntensity);

		if (peak.getLabel() != null) {
			drawVerticalTextInPeakImage(peak.getLabel(), curMz, curIntensity);
		}
	}

	private void drawPeakLine(IDrawPeak peak, int curMz, int curIntensity) {
		g2.setColor(peak.getColor());
		g2.drawLine(curMz, bottom, curMz, curIntensity);
	}

	private void drawPeaks(List<IDrawPeak> peaks) {
		Color oldColor = g2.getColor();
		for (IDrawPeak peak : peaks) {
			doDrawPeak(peak);
		}
		g2.setColor(oldColor);
	}

	private void drawYScale() {
		g2.setColor(Color.BLACK);
		g2.drawLine(left, bottom, left, top);
		for (int i = 0; i <= 100; i += 2) {
			int curPercent = getPercentY(i);
			if (i % 10 == 0) {
				g2.drawLine(left, curPercent, left - 10, curPercent);
				final String number = Integer.toString(i);
				int strWidth = getStringWidth(number);
				g2
						.drawString(number, left - 10 - strWidth, curPercent + fontHeight
								/ 3);
			} else {
				g2.drawLine(left, curPercent, left - 5, curPercent);
			}
		}

		drawVerticalTextCenter("Intensity", left - 35, (top + bottom) / 2);
	}

	private void drawVerticalTextInPeakImage(String str, int originX, int originY) {
		int totalHeight = originY - 10 - getStringWidth(str);
		if (totalHeight < top) {
			drawVerticalText(str, originX + 10 + fontHeight / 6, originY
					+ getStringWidth(str));
		} else {
			drawVerticalText(str, originX + fontHeight / 6, originY - 10);
		}
	}

	private void drawVerticalText(String str, int x, int y) {
		g2.translate(x, y);
		g2.rotate(-NINETY_DEGREES);
		g2.drawString(str, 0, 0);
		g2.rotate(NINETY_DEGREES);
		g2.translate(-x, -y);
	}

	private void drawVerticalTextCenter(String str, int x, int y) {
		int strWidth = getStringWidth(str);
		drawVerticalText(str, x + fontHeight / 2, y + strWidth / 2);
	}

	private int getTransformedX(double mz) {
		return (int) (left + mz * scaleWidth);
	}

	private int getTransformedY(double intensity) {
		return (int) (bottom - intensity * scaleHeight);
	}

	private int getPercentY(int percentY) {
		return (int) (bottom - percentY * (bottom - top) / 100);
	}

	private void drawXScale() {
		g2.setColor(Color.BLACK);
		g2.drawLine(left, bottom, right, bottom);

		int xStep = calculateXStep();

		for (int i = 0; i < maxMz; i += xStep) {
			int curMz = getTransformedX(i);
			if (i % 100 == 0) {
				g2.drawLine(curMz, bottom, curMz, bottom + 10);
				final String number = Integer.toString(i);
				int strWidth = getStringWidth(number);
				g2.drawString(number, curMz - strWidth / 2, bottom + 10 + fontHeight);
			} else if (i % 50 == 0) {
				g2.drawLine(curMz, bottom, curMz, bottom + 7);
			} else {
				g2.drawLine(curMz, bottom, curMz, bottom + 4);
			}
		}

		int mzWidth = getStringWidth("M/Z");
		g2.drawString("M/Z", (right + left - mzWidth) / 2, bottom + 10 + 2
				* fontHeight);
	}

	private int getStringWidth(final String str) {
		Rectangle2D bounds = g2.getFont().getStringBounds(str, frc);
		return (int) bounds.getWidth();
	}

	private int calculateXStep() {
		int scale = dimension.width / 800;
		if (scale < 1) {
			scale = 1;
		}
		int result = 10;
		int stepIndex = 0;
		while (stepIndex < steps.length) {
			result = steps[stepIndex];
			if (maxMz / (result * scale) <= 50) {
				break;
			}
			stepIndex++;
		}
		return result;
	}

	private void clearImage() {
		g2.setColor(Color.white);
		g2.fillRect(0, 0, dimension.width, dimension.height);
	}

	private void initImageVariables() {
		fontHeight = g2.getFontMetrics().getHeight();
		frc = g2.getFontRenderContext();

		top = fontHeight;
		left = 50;
		bottom = dimension.height - top;
		right = dimension.width - 20;

		scaleWidth = (double) (right - left) / maxMz;
		scaleHeight = (double) (bottom - top) / maxIntensity;
	}

	public void draw(OutputStream os, Dimension dim, List<IDrawPeak> peaks)
			throws IOException {
		this.dimension = dim;

		initImage(peaks);

		ImageIO.write(image, "png", os);
	}
}
