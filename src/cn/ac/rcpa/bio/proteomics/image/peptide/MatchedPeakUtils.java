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

import java.util.Collection;
import java.util.List;

public class MatchedPeakUtils {
	private MatchedPeakUtils() {
	}

	public static void unmatchAll(Collection<? extends MatchedPeak> peaks) {
		for (MatchedPeak peak : peaks) {
			peak.setMatched(false);
		}
	}

	public static void match(List<? extends MatchedPeak> exprimentalPeaks,
			List<? extends MatchedPeak> theoreticalPeaks, double mzTolerance) {
		match(exprimentalPeaks, theoreticalPeaks, mzTolerance, Double.MIN_VALUE);
	}

	public static void match(List<? extends MatchedPeak> exprimentalPeaks,
			List<? extends MatchedPeak> theoreticalPeaks, double mzTolerance,
			double minIntensity) {
		for (MatchedPeak peak : exprimentalPeaks) {
			if (peak.isMatched() || peak.getIntensity() < minIntensity) {
				continue;
			}

			for (MatchedPeak thePeak : theoreticalPeaks) {
				if (Math.abs(peak.getMz() - thePeak.getMz()) <= mzTolerance) {
					peak.setMatched(true);
					peak.setIonType(thePeak.getIonType());
					peak.setCharge(thePeak.getCharge());
					peak.setDisplayName(thePeak.getDisplayName());
					peak.setIonIndex(thePeak.getIonIndex());
					thePeak.setMatched(true);
					break;
				}
			}
		}

		removeDuplicateMatch(exprimentalPeaks);
		removeDuplicateMatch(theoreticalPeaks);
	}

	public static void removeDuplicateMatch(List<? extends MatchedPeak> peaks) {
		for (int i = 0; i < peaks.size(); i++) {
			if (!peaks.get(i).isMatched()) {
				continue;
			}

			for (int j = i + 1; j < peaks.size(); j++) {
				if (!peaks.get(j).isMatched()) {
					break;
				}

				if (peaks.get(j).getIonType() == peaks.get(i).getIonType()
						&& peaks.get(j).getIonIndex() == peaks.get(i).getIonIndex()) {
					if (peaks.get(i).getIntensity() >= peaks.get(j).getIntensity()) {
						peaks.get(j).setMatched(false);
						continue;
					}

					peaks.get(i).setMatched(false);
					break;
				}
			}
		}
	}

}
