package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.awt.Color;

import junit.framework.TestCase;

public class IonTypeColorTest extends TestCase {

	/*
	 * Test method for
	 * 'cn.ac.rcpa.msms.image.IonTypeColor.colorToHtmlColor(Color)'
	 */
	public void testColorToHtmlColor() {
		assertEquals("#FF0000", IonTypeColor.colorToHtmlColor(Color.RED));
		assertEquals("#0000FF", IonTypeColor.colorToHtmlColor(Color.BLUE));
		assertEquals("#00FF00", IonTypeColor.colorToHtmlColor(Color.GREEN));
	}

	public void testHtmlColorToColor() throws Exception {
		assertEquals(Color.RED, IonTypeColor.htmlColorToColor("#FF0000"));
		assertEquals(Color.BLUE, IonTypeColor.htmlColorToColor("#0000FF"));
		assertEquals(Color.GREEN, IonTypeColor.htmlColorToColor("#00FF00"));
	}

}
