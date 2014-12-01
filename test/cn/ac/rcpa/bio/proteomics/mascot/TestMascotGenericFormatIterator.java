package cn.ac.rcpa.bio.proteomics.mascot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class TestMascotGenericFormatIterator extends TestCase {
	public void testNextPeakList() throws Exception {
		MascotGenericFormatIterator iter = new MascotGenericFormatIterator(
				new BufferedReader(new FileReader("data/BSA1h.mgf")));
		PeakList<Peak> actualReturn = iter.next();
		assertEquals("BSA1hh", actualReturn.getExperimental());

		assertEquals(896.071, actualReturn.getPrecursor());
		assertEquals(71238.0, actualReturn.getIntensity());
		assertEquals(570, actualReturn.getFirstScan());
		assertEquals(570, actualReturn.getLastScan());

		List<Peak> peaks = actualReturn.getPeaks();
		assertEquals(peaks.size(), 50);
		assertEquals(peaks.get(0).getMz(), 297.392);
		assertEquals(peaks.get(0).getIntensity(), 729.0);
		assertEquals(peaks.get(49).getMz(), 1472.523);
		assertEquals(peaks.get(49).getIntensity(), 643.0);

		actualReturn = iter.next();
		assertEquals("MYTEST", actualReturn.getExperimental());

		assertEquals(actualReturn.getPrecursor(), 619.727);
		assertEquals(actualReturn.getIntensity(), 61753.0);
		assertEquals(actualReturn.getFirstScan(), 1811);
		assertEquals(actualReturn.getLastScan(), 1812);

		peaks = actualReturn.getPeaks();
		assertEquals(peaks.size(), 50);
		assertEquals(peaks.get(0).getMz(), 259.470);
		assertEquals(peaks.get(0).getIntensity(), 187.0);
		assertEquals(peaks.get(49).getMz(), 1092.637);
		assertEquals(peaks.get(49).getIntensity(), 115.0);
	}

}
