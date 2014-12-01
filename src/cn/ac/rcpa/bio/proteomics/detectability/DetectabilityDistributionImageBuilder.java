package cn.ac.rcpa.bio.proteomics.detectability;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.imageio.ImageIO;

import cn.ac.rcpa.utils.Pair;
import cn.ac.rcpa.utils.RcpaImageUtils;

public class DetectabilityDistributionImageBuilder {
	public static void drawImage(String imageFile,
			LinkedHashMap<Double, LinkedHashMap<Double, Pair<Integer, Integer>>> map)
			throws Exception {
		Dimension dimension = new Dimension(map.size() * 50, 600);
		BufferedImage image = new BufferedImage(dimension.width, dimension.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, dimension.width, dimension.height);

		int fontHeight = g2.getFontMetrics().getHeight();

		int top = fontHeight;
		int left = 50;
		int right = dimension.width - 20;

		int bottom = dimension.height - top - 50;

		double scaleWidth = (double) (right - left) / (map.size() + 1);
		double scaleHeight = (double) (bottom - top);

		List<Double> coverages = new ArrayList<Double>(map.keySet());
		List<Double> detectabilities = new ArrayList<Double>(map.values()
				.iterator().next().keySet());

		g2.setColor(Color.BLACK);
		g2.drawLine(left, bottom, right, bottom);
		RcpaImageUtils.drawTextCenter(g2, "Protein Coverage", left + (right - left) / 2,
				bottom + 40);
		g2.drawLine(left, bottom, left, top);
		RcpaImageUtils.drawVerticalTextCenter(g2, "Peptide Detectability", left - 40, top
				+ (bottom - top) / 2);
		
		for (int i = 0; i < coverages.size(); i++) {
			int xx = (int) (left + (i + 1) * scaleWidth);
			g2.setColor(Color.BLACK);
			RcpaImageUtils.drawTextCenter(g2, coverages.get(i).toString(), xx,
					bottom + 20);
		}

		for (int i = 0; i < detectabilities.size(); i++) {
			int yy = (int) (bottom - (detectabilities.get(i) + 0.1) * scaleHeight);
			g2.setColor(Color.BLACK);
			RcpaImageUtils.drawTextCenter(g2, detectabilities.get(i).toString(),
					left - 15, yy);
		}

		int iWidthIndex = 0;
		for (Double coverage : map.keySet()) {
			iWidthIndex++;
			int xx = (int) (left + iWidthIndex * scaleWidth);

			g2.setColor(Color.BLACK);

			RcpaImageUtils.drawTextCenter(g2, coverage.toString(), xx, bottom + 20);

			LinkedHashMap<Double, Pair<Integer, Integer>> detectabilityMap = map
					.get(coverage);
			for (Double detectability : detectabilityMap.keySet()) {
				Pair<Integer, Integer> pair = detectabilityMap.get(detectability);
				if (pair.fst == 0 && pair.snd == 0) {
					continue;
				}

				int red = pair.fst * 255 / (pair.fst + pair.snd);
				int blue = pair.snd * 255 / (pair.fst + pair.snd);

				int yy = (int) (bottom - (detectability + 0.1) * scaleHeight);

				g2.setColor(new Color(red, 0, blue));
				g2.fillRect(xx - 10, yy - 10, 20, 20);
			}
		}

		FileOutputStream os = new FileOutputStream(imageFile);
		try {
			ImageIO.write(image, "png", os);
		} finally {
			os.close();
		}
	}
}
