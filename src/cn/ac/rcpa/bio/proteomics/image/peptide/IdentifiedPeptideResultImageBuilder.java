package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.awt.Dimension;
import java.io.FileOutputStream;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IonType;

public class IdentifiedPeptideResultImageBuilder {
	public IdentifiedPeptideResultImageBuilder(int aScale,
			double aPeakTolerance,
			double precursorNeutralLossMinIntensityScale,
			double byNeutralLossMinIntensityScale, double byMinIntensityScale,
			boolean drawRemoveNeutralLossImage,
			ImageType drawImageType) {
		this.scale = aScale;
		this.peakTolerance = aPeakTolerance;
		this.precursorNeutralLossMinIntensityScale = precursorNeutralLossMinIntensityScale;
		this.byNeutralLossMinIntensityScale = byNeutralLossMinIntensityScale;
		this.byMinIntensityScale = byMinIntensityScale;
		this.drawRemoveNeutralLossImage = drawRemoveNeutralLossImage;
		this.drawImageType = drawImageType;
	}

	private int scale;

	private double peakTolerance;

	private double precursorNeutralLossMinIntensityScale;

	private double byNeutralLossMinIntensityScale;

	private double byMinIntensityScale;

	private boolean drawRemoveNeutralLossImage;
	
	private ImageType drawImageType;

	public void drawImage(String imageFilename, IIdentifiedPeptideResult sr)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(imageFilename);
		try {
			IMatchedPeptideSpectrumImageBuilder builder;
			if (drawRemoveNeutralLossImage) {
				builder = new MatchedSpectrumPrecursorNeutralLossRemovedImageBuilder(drawImageType);
			} else {
				builder = new MatchedSpectrumImageBuilder(drawImageType);
			}

			Map<Character, Double> dynamicModifications = sr
					.getDynamicModification();

			boolean isMS3 = hasSpecialDynamicModification(dynamicModifications,
					-18.0);

			if (!isMS3) {
				builder.addMatcher(new PhosphoNeutralLossMatcher(
						dynamicModifications, peakTolerance,
						precursorNeutralLossMinIntensityScale,
						byNeutralLossMinIntensityScale));
			} else {
				builder.addMatcher(new PrecursorMatcher(peakTolerance,
						precursorNeutralLossMinIntensityScale));
			}

			builder.addMatcher(new PeakIonSeriesMatcher(IonType.Y,
					peakTolerance, byMinIntensityScale));
			builder.addMatcher(new PeakIonSeriesMatcher(IonType.B,
					peakTolerance, byMinIntensityScale));

			if (sr.getExperimentalPeakList().getCharge() >= 2) {
				builder.addMatcher(new PeakIonSeriesMatcher(IonType.Y2,
						peakTolerance, byMinIntensityScale));
				builder.addMatcher(new PeakIonSeriesMatcher(IonType.B2,
						peakTolerance, byMinIntensityScale));
			}

			builder.setIdentifiedResult(sr);
			builder.matchPeaks();

			builder.addDrawer(new YSeriesPeptideDrawer(IonType.Y));
			builder.addDrawer(new BSeriesPeptideDrawer(IonType.B));

			if (sr.isBy2Matched()) {
				builder.addDrawer(new YSeriesPeptideDrawer(IonType.Y2));
				builder.addDrawer(new BSeriesPeptideDrawer(IonType.B2));
			}

			builder.setDimension(new Dimension(800 * scale, 700));

			builder.drawImage(fos);
		} finally {
			fos.close();
		}
	}

	public double getScale(double maxIntensity, double maxBYIntensity) {
		double tolerance = maxIntensity / 5;
		if (maxBYIntensity > tolerance) {
			return 0;
		}

		double result = tolerance / maxBYIntensity;

		result = 5 * ((int) result / 5 + 1);

		return result;
	}

	public boolean hasSpecialDynamicModification(
			Map<Character, Double> modifications, double modifiedValue) {
		for (Double value : modifications.values()) {
			if (Math.abs(value - modifiedValue) < 0.1) {
				return true;
			}
		}
		return false;
	}

}
