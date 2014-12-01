package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;

public class DtasIteratorTest extends TestCase {

	/*
	 * Test method for 'cn.ac.rcpa.bio.proteomics.AbstractPeakListIterator.next()'
	 */
	public void testNext() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(
				"data/20030428_4_29L_15.dtas"));
		DtasIterator iter = new DtasIterator(br);

		PeakList<Peak> actualReturn = iter.next();
		assertEquals("20030428_4_29L_15", actualReturn.getExperimental());
		assertEquals(2002, actualReturn.getFirstScan());
		assertEquals(1148.77, actualReturn.getPrecursor(), 0.01);
		assertEquals(2, actualReturn.getCharge());

		List<Peak> peaks = actualReturn.getPeaks();
		assertEquals(89, peaks.size());
		assertEquals(191.29, peaks.get(0).getMz(), 0.01);
		assertEquals(7976.0, peaks.get(0).getIntensity());
		assertEquals(1296.49, peaks.get(88).getMz(), 0.01);
		assertEquals(2230.0, peaks.get(88).getIntensity());

		while (iter.hasNext()) {
			actualReturn = iter.next();
		}

		assertEquals("20030428_4_29L_15", actualReturn.getExperimental());
		assertEquals(2995, actualReturn.getFirstScan());
		assertEquals(464.24, actualReturn.getPrecursor(), 0.01);
		assertEquals(1, actualReturn.getCharge());

		peaks = actualReturn.getPeaks();
		assertEquals(93, peaks.size());
		assertEquals(143.02, peaks.get(0).getMz(), 0.01);
		assertEquals(5549.0, peaks.get(0).getIntensity());
		assertEquals(788.70, peaks.get(92).getMz(), 0.01);
		assertEquals(12082.0, peaks.get(92).getIntensity(), 0.01);
	}

}
