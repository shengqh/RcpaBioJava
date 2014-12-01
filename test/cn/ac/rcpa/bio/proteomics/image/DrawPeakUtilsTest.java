package cn.ac.rcpa.bio.proteomics.image;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.Peak;

public class DrawPeakUtilsTest extends TestCase {
	private List<Peak> sourcePeaks;

	@Override
	public void setUp() {
		sourcePeaks = new ArrayList<Peak>();

		Peak peak;
		
		peak = new Peak(1.1, 10.0);
		sourcePeaks.add(peak);
		
		peak = new Peak(2.1, 20.0);
		peak.getAnnotations().add("B1");
		peak.getAnnotations().add("Y9");
		sourcePeaks.add(peak);
	}

	private void assertEqualsPeakMzIntensityLabel(List<IDrawPeak> dPeaks) {
		assertEquals(1.1, dPeaks.get(0).getMz());
		assertEquals(10.0, dPeaks.get(0).getIntensity());
		assertEquals(null, dPeaks.get(0).getLabel());
		
		assertEquals(2.1, dPeaks.get(1).getMz());
		assertEquals(20.0, dPeaks.get(1).getIntensity());
		assertEquals("B1/Y9 (2.1)", dPeaks.get(1).getLabel());
	}

	/*
	 * Test method for
	 * 'cn.ac.rcpa.bio.proteomics.image.DrawPeakUtils.convertToDrawPeak(List<Peak>,
	 * Color,Color)'
	 */
	public void testConvertToDrawPeakListOfPeakColor() {
		List<IDrawPeak> dPeaks = DrawPeakUtils.convertToDrawPeak(sourcePeaks, Color.BLACK, Color.RED);
		
		assertEqualsPeakMzIntensityLabel(dPeaks);
		
		assertEquals(Color.BLACK, dPeaks.get(0).getColor());
		assertEquals(Color.RED, dPeaks.get(1).getColor());
	}

	/*
	 * Test method for
	 * 'cn.ac.rcpa.bio.proteomics.image.DrawPeakUtils.convertToDrawPeak(List<Peak>,Color)'
	 */
	public void testConvertToDrawPeakListOfPeak() {
		List<IDrawPeak> dPeaks = DrawPeakUtils.convertToDrawPeak(sourcePeaks, Color.BLACK);
		
		assertEqualsPeakMzIntensityLabel(dPeaks);
		
		assertEquals(Color.BLACK, dPeaks.get(0).getColor());
		assertEquals(Color.BLACK, dPeaks.get(1).getColor());
	}

}
