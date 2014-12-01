package cn.ac.rcpa.bio.proteomics.image;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.Peak;

public class DrawPeakUtils {
	private static DecimalFormat df4 = new DecimalFormat("0.####");

	private static boolean hasLabel(Peak peak) {
		return !peak.getAnnotations().isEmpty();
	}

	public static List<IDrawPeak> convertToDrawPeak(List<Peak> peaks,
			Color defaultColor, Color labeledColor) {
		List<IDrawPeak> result = new ArrayList<IDrawPeak>();
		for (Peak peak : peaks) {
			DrawPeak dPeak = new DrawPeak(peak);
			if (hasLabel(peak)) {
				String label = "";
				for (String name : peak.getAnnotations()) {
					if (label.length() == 0) {
						label = name;
					} else {
						label = label + "/" + name;
					}
				}
				label = label + " (" + df4.format(peak.getMz()) + ")";
				dPeak.setLabel(label);
				dPeak.setColor(labeledColor);
			} else {
				dPeak.setColor(defaultColor);
			}
			result.add(dPeak);
		}
		return result;
	}

	public static List<IDrawPeak> convertToDrawPeak(List<Peak> peaks,
			Color defaultColor) {
		return convertToDrawPeak(peaks, defaultColor, defaultColor);
	}

}
