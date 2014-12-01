package edu.csusb.danby.math;

import junit.framework.TestCase;

public class ProbMathTest extends TestCase {

	/*
	 * Test method for 'edu.csusb.danby.math.ProbMath.normalCdf(double)'
	 */
	public void testNormalCdf() {
		assertEquals(0.5, ProbMath.normalCdf(0.0));
		assertEquals(0.6827, ProbMath.normalCdf(1.0) - ProbMath.normalCdf(-1.0), 0.0001);
		assertEquals(0.9545, ProbMath.normalCdf(2.0) - ProbMath.normalCdf(-2.0), 0.0001);
		assertEquals(0.9973, ProbMath.normalCdf(3.0) - ProbMath.normalCdf(-3.0), 0.0001);
		assertEquals(0.9999, ProbMath.normalCdf(4.0) - ProbMath.normalCdf(-4.0), 0.0001);
	}

}
