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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IonType;

//Draw additional image without precursor neutral loss peaks
public class MatchedSpectrumPrecursorNeutralLossRemovedImageBuilder extends
		MatchedSpectrumImageBuilder {
	public MatchedSpectrumPrecursorNeutralLossRemovedImageBuilder(
			ImageType aType) {
		super(aType);
	}

	private boolean isPrecursorIon(MatchedPeak peak) {
		if (!peak.isMatched()) {
			return false;
		}

		return peak.getIonType() == IonType.PRECURSOR
				|| peak.getIonType() == IonType.PRECURSOR_NEUTRAL_LOSS
				|| peak.getIonType() == IonType.PRECURSOR_NEUTRAL_LOSS_PHOSPHO;
	}

	private boolean hasPrecursorIon(List<MatchedPeak> pkl) {
		for (MatchedPeak peak : pkl) {
			if (isPrecursorIon(peak)) {
				return true;
			}
		}

		return false;
	}

	public void drawImage(OutputStream os) throws IOException {
		initIonSeries();

		BufferedImage image1 = initImage();

		boolean bIonRemoved = false;
		// remove all precursor/precursor_nertualloss
		while (hasPrecursorIon(getIdentifiedResult().getExperimentalPeakList()
				.getPeaks())) {
			bIonRemoved = true;
			List<MatchedPeak> pkl = getIdentifiedResult()
					.getExperimentalPeakList().getPeaks();
			for (int i = pkl.size() - 1; i >= 0; i--) {
				if (isPrecursorIon(pkl.get(i))) {
					pkl.remove(i);
				}
			}
			initIonSeries();
		}

		BufferedImage image;

		if (bIonRemoved) {
			BufferedImage image2 = initImage();

			image = new BufferedImage(getDimension().width,
					getDimension().height * 2, BufferedImage.TYPE_INT_RGB);

			Graphics2D gTotal = image.createGraphics();
			gTotal.drawImage(image1, 0, 0, null);
			gTotal.drawImage(image2, 0, getDimension().height, null);
			gTotal.dispose();
		} else {
			image = image1;
		}

		drawToFile(os, image);
	}
}
