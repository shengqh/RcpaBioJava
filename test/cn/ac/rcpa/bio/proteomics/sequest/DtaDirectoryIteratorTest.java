package cn.ac.rcpa.bio.proteomics.sequest;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;

public class DtaDirectoryIteratorTest extends TestCase {

	/*
	 * Test method for 'org.bio.proteomics.AbstractPeakListIterator.next()'
	 */
	public void testNext() throws Exception {
		DtaDirectoryIterator ddi = new DtaDirectoryIterator("data/dta");
		assertEquals(true, ddi.hasNext());
		
		PeakList<Peak> pl = ddi.next();
		assertEquals("Mix_A_1_5uL_020906_E19",pl.getExperimental());
		assertEquals(326,pl.getFirstScan());
		assertEquals(326,pl.getLastScan());
		assertEquals(847.750, pl.getPrecursor(), 0.001);
		assertEquals(2, pl.getCharge());
		assertEquals(367, pl.getPeaks().size());
		
		ddi.next();
		ddi.next();
		
		assertEquals(false, ddi.hasNext());
	}

}
