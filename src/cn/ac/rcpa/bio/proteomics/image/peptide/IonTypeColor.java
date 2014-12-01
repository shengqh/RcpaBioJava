/*
 * Created on 2005-11-14
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.utils.RcpaFileUtils;
import cn.ac.rcpa.utils.XMLFile;

public class IonTypeColor {
	private static HashMap<IonType, Color> colorMap = new HashMap<IonType, Color>();

	static {
		colorMap.put(IonType.Y, Color.RED);
		colorMap.put(IonType.B, Color.BLUE);
		colorMap.put(IonType.Y2, new Color(255, 0, 255));
		colorMap.put(IonType.B2, new Color(0, 128, 192));
		colorMap.put(IonType.NEUTRAL_LOSS_PHOSPHO, new Color(128, 64, 64));
		colorMap.put(IonType.NEUTRAL_LOSS, new Color(128, 64, 64));
		colorMap.put(IonType.PRECURSOR, Color.GREEN);
		colorMap.put(IonType.PRECURSOR_NEUTRAL_LOSS_PHOSPHO, Color.GREEN);
		colorMap.put(IonType.PRECURSOR_NEUTRAL_LOSS, Color.GREEN);
		colorMap.put(IonType.C, Color.YELLOW);

		String optionFile = RcpaFileUtils.getConfigFile("IonTypeColor");
		try {
			XMLFile xml = new XMLFile(optionFile);
			if (new File(optionFile).exists()) {
				loadColorMap(colorMap, xml);
			} else {
				saveColorMap(colorMap, xml);
				xml.saveToFile();
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	public static Color getColor(IonType ionType) {
		if (colorMap.containsKey(ionType)) {
			return colorMap.get(ionType);
		}

		return Color.BLACK;
	}

	private static void saveColorMap(HashMap<IonType, Color> colorMap2,
			XMLFile xml) {
		for (IonType iType : colorMap2.keySet()) {
			String oldValue = colorToHtmlColor(colorMap2.get(iType));
			xml.setElementValue(iType.toString(), oldValue);
		}
	}

	private static void loadColorMap(HashMap<IonType, Color> colorMap2,
			XMLFile xml) throws Exception {
		List<IonType> types = new ArrayList<IonType>(colorMap2.keySet());
		for (IonType iType : types) {
			String oldValue = colorToHtmlColor(colorMap2.get(iType));
			String value = xml.getElementValue(iType.toString(), oldValue);
			colorMap2.put(iType, htmlColorToColor(value));
		}
	}

	public static String colorToHtmlColor(Color color) {
		return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
	}

	public static Color htmlColorToColor(String color) throws Exception {
		return Color.decode(color);
	}

	public static String getHtmlColor(IonType ionType) {
		Color color = colorMap.get(ionType);
		if (null == color) {
			return "#000000";
		}

		return colorToHtmlColor(color);
	}
}
