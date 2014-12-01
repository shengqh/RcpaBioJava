package cn.ac.rcpa.bio.proteomics.detectability;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import cn.ac.rcpa.utils.RcpaImageUtils;

public class DetectabilityImageBuilder {
	public static void drawImage(String imageFile,
			List<ProteinDetectabilityEntry> proteinProbabilityList) throws Exception {
		Dimension dimension = new Dimension(proteinProbabilityList.size() * 50, 600);
		BufferedImage image = new BufferedImage(dimension.width, dimension.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, dimension.width, dimension.height);

		int fontHeight = g2.getFontMetrics().getHeight();

		int top = fontHeight;
		int left = 50;

		List<String> proteinNameList = getProteinNameList(proteinProbabilityList);
		
		int maxWidth = RcpaImageUtils.getMaxWidth(g2, proteinNameList);
		int bottom = dimension.height - top - maxWidth;
		int right = dimension.width - 20;

		double scaleWidth = (double) (right - left) / (proteinNameList.size() + 1);
		double scaleHeight = (double) (bottom - top);

		for (int i = 0; i < proteinNameList.size(); i++) {
			int xx = (int) (left + (i + 1) * scaleWidth);
			//System.out.println(i + "=" + proteinProbabilityList.get(i).getName());

			g2.setColor(Color.BLACK);
			
			RcpaImageUtils.drawVerticalText(g2, proteinNameList.get(i), xx, bottom + maxWidth);
			
			Collection<DetectabilityEntry> deSet = proteinProbabilityList.get(i).getPeptideMap().values();
			for (DetectabilityEntry de : deSet) {
				Color color;
				
				int x = xx;
				if (de.isDetected()) {
					color = new Color(255, 0, 0);
					x -= 3;
				} else {
					color = Color.BLUE;
					x += 3;
				}

				int y = (int) (bottom - de.getDetectability() * scaleHeight);

				g2.setColor(color);
				g2.fillRect(x - 2, y - 2, 4, 4);
			}
		}

		FileOutputStream os = new FileOutputStream(imageFile);
		try {
			ImageIO.write(image, "png", os);
		} finally {
			os.close();
		}
	}

	private static List<String> getProteinNameList(List<ProteinDetectabilityEntry> proteinProbabilityList) {
		ArrayList<String> result = new ArrayList<String>();

		for(ProteinDetectabilityEntry protein:proteinProbabilityList){
			if(protein.getName().length() > 20){
				result.add(protein.getName().substring(0,19));
			}
			else{
				result.add(protein.getName());
			}
		}
		
		return result;
	}

}
