package cn.ac.rcpa.utils;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class RcpaImageUtils {
	private static final double NINETY_DEGREES = Math.toRadians(90.0);

	public static int getStringWidth(Graphics2D g2, final String str) {
		Rectangle2D bounds = g2.getFont().getStringBounds(str,
				g2.getFontRenderContext());
		return (int) bounds.getWidth();
	}

	public static int getMaxWidth(Graphics2D g2, List<String> proteinNames) {
		int result = 0;
		for (String proteinName : proteinNames) {
			int width = getStringWidth(g2, proteinName);
			if (width > result) {
				result = width;
			}
		}
		return result;
	}

	public static void drawVerticalText(Graphics2D g2, String str, int x, int y) {
		g2.translate(x, y);
		g2.rotate(-NINETY_DEGREES);
		g2.drawString(str, 0, 0);
		g2.rotate(NINETY_DEGREES);
		g2.translate(-x, -y);
	}

	public static void drawVerticalTextCenter(Graphics2D g2, String str, int x,
			int y) {
		int strWidth = getStringWidth(g2, str);
		drawVerticalText(g2, str, x + g2.getFontMetrics().getHeight() / 2, y
				+ strWidth / 2);
	}

	public static void drawText(Graphics2D g2, String str, int x, int y) {
		g2.translate(x, y);
		g2.drawString(str, 0, 0);
		g2.translate(-x, -y);
	}

	public static void drawTextCenter(Graphics2D g2, String str, int x, int y) {
		int strWidth = getStringWidth(g2, str);
		drawText(g2, str, x - strWidth / 2, y + g2.getFontMetrics().getHeight() / 2);
	}

}
