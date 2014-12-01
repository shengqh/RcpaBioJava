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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.PeakList;
import cn.ac.rcpa.utils.RcpaStringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;
import com.sun.media.jai.codecimpl.TIFFImageEncoder;

public class MatchedSpectrumImageBuilder implements
		IMatchedPeptideSpectrumImageBuilder {
	public MatchedSpectrumImageBuilder(ImageType atype) {
		this.FImageType = atype;
	}

	private ImageType FImageType;

	private class TextItem implements Comparable<TextItem> {
		public TextItem(int x, int lineX, int y, Color textColor, String text) {
			this.x = x;
			this.lineX = lineX;
			this.y = y;
			this.textColor = textColor;
			this.text = text;
		}

		int x;
		int lineX;
		int y;
		Color textColor;
		String text;

		@Override
		public int compareTo(TextItem arg0) {
			return y - arg0.y;
		}

		@Override
		public String toString() {
			return x + "," + y + "," + text;
		}
	}

	private Dimension dimension = new Dimension(800, 700);

	private IIdentifiedPeptideResult pepResult;

	protected IIdentifiedPeptideResult getIdentifiedPeptideResult() {
		return pepResult;
	}

	private List<IPeakMatcher> matchers = new ArrayList<IPeakMatcher>();

	private List<IPeptideDrawer> drawers = new ArrayList<IPeptideDrawer>();

	private List<TextItem> texts = new ArrayList<TextItem>();

	private List<String> informations = new ArrayList<String>();

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dim) {
		this.dimension = dim;
	}

	public void setIdentifiedResult(IIdentifiedPeptideResult pepResult) {
		this.pepResult = pepResult;
	}

	public IIdentifiedPeptideResult getIdentifiedResult() {
		return pepResult;
	}

	public void addMatcher(IPeakMatcher matcher) {
		this.matchers.add(matcher);
	}

	public void removeMatcher(IPeakMatcher matcher) {
		this.matchers.remove(matcher);
	}

	public void clearMatcher() {
		this.matchers.clear();
	}

	public void addDrawer(IPeptideDrawer drawer) {
		this.drawers.add(drawer);
	}

	public void removeDrawer(IPeptideDrawer drawer) {
		this.drawers.remove(drawer);
	}

	public void clearDrawer() {
		this.drawers.clear();
	}

	private static DecimalFormat df = new DecimalFormat("0.00");

	private static final double NINETY_DEGREES = Math.toRadians(90.0);

	private int maxMz = Integer.MAX_VALUE;

	private int maxIntensity = Integer.MAX_VALUE;

	private Graphics2D g2;

	protected Graphics2D getG2() {
		return g2;
	}

	private int fontHeight;

	protected int getFontHeight() {
		return fontHeight;
	}

	private FontRenderContext frc;

	private int drawBottom;

	protected int getTop() {
		return drawBottom + textGap;
	}

	private int bottom;

	protected int getBottom() {
		return bottom;
	}

	private int left;

	protected int getLeft() {
		return left;
	}

	private int right;

	protected int getRight() {
		return right;
	}

	private double getScaleWidth() {
		return (double) (right - left) / maxMz;
	}

	private double getScaleHeight() {
		return (double) (bottom - getTop()) / maxIntensity;
	}

	private static int[] steps = new int[] { 10, 25, 50 };

	private int textGap;

	protected void initIonSeries() {
		PeakList<MatchedPeak> peaks = pepResult.getExperimentalPeakList();

		maxMz = (int) peaks.getMaxPeak();
		maxMz = (maxMz / 100 + 1) * 100;

		maxIntensity = (int) peaks.getMaxIntensity();

		matchPeaks();
	}

	protected BufferedImage initImage() {
		BufferedImage result = new BufferedImage(dimension.width,
				dimension.height, BufferedImage.TYPE_INT_RGB);

		g2 = result.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // 图片抗锯齿呈现
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, // 颜色呈现偏重质量
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, // 呈现偏重输出质量的合适呈现算法
				RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		initImageVariables();

		initTextGap();

		clearImage();

		drawXScale();

		drawYScale();

		drawPeaks();

		adjustTextPositions();

		drawPeakTexts();

		drawPeptides();

		initInformations();

		drawInformations();

		g2.dispose();

		return result;
	}

	private void adjustTextPositions() {
		Collections.sort(texts);

		int realFontHeight = (int) (fontHeight / 1.5);

		for (int i = 0; i < texts.size(); i++) {
			int righti = texts.get(i).x;
			int lefti = righti - realFontHeight;
			int bottomi = texts.get(i).y;

			for (int j = i + 1; j < texts.size(); j++) {
				int rightj = texts.get(j).x;
				int leftj = rightj - realFontHeight;
				int topj = texts.get(j).y - getStringWidth(texts.get(j).text);

				if (bottomi < topj) {
					continue;
				}

				// 左边overlap
				if (leftj < lefti && lefti < rightj) {
					texts.get(j).x = lefti;
				}
				// 右边overlap
				else if (leftj < righti && righti < rightj) {
					texts.get(j).x = righti + realFontHeight;
				}
			}
		}

		for (TextItem item : texts) {
			int centerX = item.lineX + fontHeight / 6;
			if (Math.abs(item.x - centerX) > fontHeight) {
				item.x = centerX;
			}
		}
	}

	private void drawPeptides() {
		int scale = 1;
		for (IPeptideDrawer drawer : drawers) {
			drawer.draw(this, pepResult, drawBottom - scale * fontHeight);
			scale++;
		}
	}

	public void drawPeptide(List<MatchedPeak> peaks, String peptide,
			int yPosition) {
		final Color oldColor = g2.getColor();
		final int height = fontHeight / 2;

		int lastPos = getTransformedX(0);
		g2.drawLine(lastPos, yPosition - height / 2, lastPos, yPosition
				+ height / 2);

		String ions = peaks.get(0).getIonType() + " Ions";
		g2.drawString(ions, lastPos - getStringWidth(ions) - 5, yPosition
				+ height / 2);

		for (int i = 0, j = 0; i < peaks.size(); i++) {
			MatchedPeak peak = peaks.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(peptide.charAt(j++));
			if (j < peptide.length() && !Character.isLetter(peptide.charAt(j))) {
				sb.append(peptide.charAt(j++));
			}
			String aminoacid = sb.toString();

			Color color = Color.BLACK;
			if (peak.isMatched()) {
				color = IonTypeColor.getColor(peak.getIonType());
			}
			g2.setColor(color);
			int endPos = getTransformedX(peak.getMz());
			int strWidth = getStringWidth(aminoacid);
			final int space = 2;
			int firstEnd = (endPos + lastPos - strWidth - 2 * space) / 2;
			int secondBegin = firstEnd + strWidth + 2 * space;

			g2.drawLine(lastPos, yPosition, firstEnd, yPosition);
			g2.drawString(aminoacid, firstEnd + space, yPosition + height / 2);
			g2.drawLine(secondBegin, yPosition, endPos, yPosition);

			g2.drawLine(endPos, yPosition - height / 2, endPos, yPosition
					+ height / 2);

			lastPos = endPos;
		}
		g2.setColor(oldColor);
	}

	private void drawPeakLine(MatchedPeak peak, int curMz, int curIntensity) {
		if (peak.isMatched() && peak.getIonType() == null) {
			throw new IllegalStateException("Undifined IonType of peak "
					+ peak.getMz() + " : " + peak.getIntensity());
		}

		g2.setColor(getPeakColor(peak));
		g2.drawLine(curMz, bottom, curMz, curIntensity);
	}

	private Color getPeakColor(MatchedPeak peak) {
		return peak.isMatched() ? IonTypeColor.getColor(peak.getIonType())
				: Color.BLACK;
	}

	private void drawPeaks() {
		texts.clear();

		Color oldColor = g2.getColor();
		for (MatchedPeak peak : pepResult.getExperimentalPeakList().getPeaks()) {
			int curMz = getTransformedX(peak.getMz());
			int curIntensity = getTransformedY(peak.getIntensity());

			drawPeakLine(peak, curMz, curIntensity);

			if (peak.isMatched()) {
				String ionName = getIonName(peak);

				texts.add(new TextItem(curMz + fontHeight / 6, curMz,
						curIntensity - 10, getPeakColor(peak), ionName));
			}
		}
		g2.setColor(oldColor);
	}

	private void drawPeakTexts() {
		Color oldColor = g2.getColor();

		for (TextItem item : texts) {
			g2.setColor(item.textColor);
			drawVerticalIonName(item.text, item.x, item.y);
		}

		g2.setColor(oldColor);
	}

	public void matchPeaks() {
		MatchedPeakUtils.unmatchAll(pepResult.getExperimentalPeakList()
				.getPeaks());
		for (IPeakMatcher matcher : matchers) {
			matcher.match(pepResult);
		}
	}

	private void drawYScale() {
		g2.setColor(Color.BLACK);
		g2.drawLine(left, bottom, left, getTop());
		for (int i = 0; i <= 100; i += 2) {
			int curPercent = getPercentY(i);
			if (i % 10 == 0) {
				g2.drawLine(left, curPercent, left - 10, curPercent);
				final String number = Integer.toString(i);
				int strWidth = getStringWidth(number);
				g2.drawString(number, left - 10 - strWidth, curPercent
						+ fontHeight / 3);
			} else {
				g2.drawLine(left, curPercent, left - 5, curPercent);
			}
		}

		drawVerticalTextCenter("Intensity", left - 35, (getTop() + bottom) / 2);
	}

	private void drawVerticalText(String str, int x, int y) {
		g2.translate(x, y);
		g2.rotate(-NINETY_DEGREES);
		g2.drawString(str, 0, 0);
		g2.rotate(NINETY_DEGREES);
		g2.translate(-x, -y);
	}

	private void drawVerticalIonName(String str, int x, int y) {
		g2.translate(x, y);
		g2.rotate(-NINETY_DEGREES);
		Font oldFont = g2.getFont();
		Font newFont = oldFont.deriveFont((float) (oldFont.getSize() / 1.3));
		int curX = 0;
		boolean bIonTypeNumber = true;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				g2.drawString(str.substring(i), curX, 0);
				break;
			}

			String subStr = str.substring(i, i + 1);
			if (Character.isDigit(str.charAt(i))) {
				if (bIonTypeNumber) {
					g2.drawString(subStr, curX, 0);
					curX += getStringWidth(oldFont, subStr);
				} else {
					g2.setFont(newFont);
					g2.drawString(subStr, curX, getFontHeight() / 8);
					g2.setFont(oldFont);
					curX += getStringWidth(newFont, subStr);
				}
			} else {
				if (i > 1) {
					bIonTypeNumber = false;
				}
				g2.drawString(subStr, curX, 0);
				curX += getStringWidth(oldFont, subStr);
			}
		}
		g2.rotate(NINETY_DEGREES);
		g2.translate(-x, -y);
	}

	private void drawVerticalTextCenter(String str, int x, int y) {
		int strWidth = getStringWidth(str);
		drawVerticalText(str, x + fontHeight / 2, y + strWidth / 2);
	}

	private int getTransformedX(double mz) {
		return (int) (left + mz * getScaleWidth());
	}

	private int getTransformedY(double intensity) {
		return (int) (bottom - intensity * getScaleHeight());
	}

	private int getPercentY(int percentY) {
		return bottom - percentY * (bottom - getTop()) / 100;
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
				g2.drawString(number, curMz - strWidth / 2, bottom + 10
						+ fontHeight);
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

	protected int getStringWidth(final String str) {
		return getStringWidth(g2.getFont(), str);
	}

	protected int getStringWidth(final Font font, final String str) {
		Rectangle2D bounds = font.getStringBounds(str, frc);
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

		drawBottom = fontHeight * (drawers.size() + 1);
		left = 50;
		bottom = dimension.height - 50;
		right = dimension.width - 20;

		textGap = 100;
	}

	protected void drawJPEGImage(OutputStream os, BufferedImage image)
			throws IOException {
		JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(os);
		JPEGEncodeParam jpegEncodeParam = jpegEncoder
				.getDefaultJPEGEncodeParam(image);
		jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
		jpegEncodeParam.setXDensity(300);
		jpegEncodeParam.setYDensity(300);
		jpegEncodeParam.setQuality(1.0f, false);
		jpegEncoder.encode(image, jpegEncodeParam);
	}

	protected void drawTIFFImage(OutputStream os, BufferedImage image)
			throws IOException {
		TIFFEncodeParam param = new TIFFEncodeParam();
		param.setCompression(TIFFEncodeParam.COMPRESSION_NONE);
		TIFFField[] extras = new TIFFField[2];
		extras[0] = new TIFFField(282, TIFFField.TIFF_RATIONAL, 1,
				(Object) new long[][] { { 300, 1 }, { 0, 0 } });
		extras[1] = new TIFFField(283, TIFFField.TIFF_RATIONAL, 1,
				(Object) new long[][] { { 300, 1 }, { 0, 0 } });
		param.setExtraFields(extras);
		TIFFImageEncoder encoder = new TIFFImageEncoder(os, param);
		encoder.encode(image);
	}

	protected void drawPNGImage(OutputStream os, BufferedImage image)
			throws IOException {
//		JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(os);
//		JPEGEncodeParam jpegEncodeParam = jpegEncoder
//				.getDefaultJPEGEncodeParam(image);
//		jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
//		jpegEncodeParam.setXDensity(300);
//		jpegEncodeParam.setYDensity(300);
//		jpegEncodeParam.setQuality(1.0f, false);
//		jpegEncoder.encode(image, jpegEncodeParam);
//		// JAI.
		ImageIO.write(image, "png", os);
	}

	public void drawImage(OutputStream os) throws IOException {
		initIonSeries();

		BufferedImage image = initImage();

		drawToFile(os, image);
	}

	protected void drawToFile(OutputStream os, BufferedImage image)
			throws IOException {
		if (FImageType == ImageType.png) {
			drawPNGImage(os, image);
		} else if (FImageType == ImageType.tiff) {
			drawTIFFImage(os, image);
		} else {
			drawJPEGImage(os, image);
		}
	}

	private DecimalFormat df4 = new DecimalFormat("0.####");

	protected void initInformations() {
		informations.clear();

		informations.add("Sequence = "
				+ getIdentifiedPeptideResult().getPeptide());
		informations.add("MH+ = "
				+ df4.format(getIdentifiedPeptideResult()
						.getExperimentalPeakList().getPrecursor()));
		informations.add("Charge = "
				+ getIdentifiedPeptideResult().getExperimentalPeakList()
						.getCharge() + "+");
		for (String key : getIdentifiedPeptideResult().getScoreMap().keySet()) {
			informations.add(key + " = "
					+ getIdentifiedPeptideResult().getScoreMap().get(key));
		}
	}

	public void drawInformations() {
		for (int i = 0; i < informations.size(); i++) {
			int y = getTop() + i * 10 + (i + 1) * getFontHeight();
			getG2().drawString(informations.get(i), getLeft() + 10, y);
		}
	}

	private void initTextGap() {
		for (MatchedPeak peak : pepResult.getExperimentalPeakList().getPeaks()) {
			int curIntensity = getTransformedY(peak.getIntensity());

			if (peak.isMatched()) {
				String ionName = getIonName(peak);

				int totalHeight = curIntensity - 10 - getStringWidth(ionName);

				if (totalHeight < drawBottom) {
					textGap += drawBottom - totalHeight;
				}
			}
		}
	}

	private String getIonName(MatchedPeak peak) {
		String result = "";

		String charge = "";
		if (peak.getCharge() > 1) {
			charge = RcpaStringUtils.getRepeatChar('+', peak.getCharge());
		}

		if (null != peak.getDisplayName()
				&& 0 != peak.getDisplayName().length()) {
			result = peak.getDisplayName() + charge + "   "
					+ df.format(peak.getMz());
		} else if (IonType.PRECURSOR == peak.getIonType()
				|| IonType.PRECURSOR_NEUTRAL_LOSS == peak.getIonType()) {
			result = peak.getIonType() + "-" + df.format(peak.getMz());
		} else {
			String name;
			if (peak.getIonType() == IonType.B2) {
				name = "b";
			} else if (peak.getIonType() == IonType.Y2) {
				name = "y";
			} else {
				name = StringUtils.lowerCase(peak.getIonType().toString());
			}
			result = name + peak.getIonIndex() + charge + "   "
					+ df.format(peak.getMz());
		}
		return result;
	}
}
