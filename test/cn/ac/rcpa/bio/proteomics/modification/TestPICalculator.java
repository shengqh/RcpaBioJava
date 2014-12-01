package cn.ac.rcpa.bio.proteomics.modification;

import junit.framework.Assert;
import junit.framework.TestCase;
import picalculator.pIcalculator;

public class TestPICalculator extends TestCase {
	private String AApI = "expasy";
	private String pKaSet = "scansite";
	private boolean methylated = false;

	public void testCalculate() {
		pIcalculator pc = new pIcalculator();
		pc.setAApI(AApI);
		pc.setpKaSet(pKaSet);
		pc.setMethylatedPeptides(methylated);

		Assert.assertEquals(0, pc.getPhosphoNumber("MLITETFHDVQTSYGTTLR"));
		Assert.assertEquals(1, pc.getPhosphoNumber("MLITpETFHDVQTSYGTTLR"));
		Assert.assertEquals(2, pc.getPhosphoNumber("MLITpETFHDVQTSpYGTTLR"));
		Assert.assertEquals(3, pc.getPhosphoNumber("MLITpETFHDVQTSpYpGTTLR"));
	}
}
