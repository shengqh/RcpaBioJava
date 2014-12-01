package cn.ac.rcpa.bio.aminoacid;

import junit.framework.TestCase;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TestAminoacid extends TestCase {
	/*
	 * Test for boolean equals(Object)
	 */
	public void testEqualsObject() {
		Aminoacid a = new Aminoacid();
		Aminoacid b = new Aminoacid();
		a.setOneLetter('A');
		a.setAverageMass(1111.0);
		b.setOneLetter('A');
		b.setAverageMass(22222.0);
		assertTrue(a.toString() + "==" + b.toString(), a.equals(b));
		b.setOneLetter('B');
		assertFalse(a.toString() + "==" + b.toString(), a.equals(b));
	}

	public void testReset() {
		Aminoacid a = new Aminoacid();
		assertTrue(a.toString() + " ==       0.00 0.0000 0.00  Unvisible", a
				.toString().equals("      0.00 0.0000 0.00  Unvisible"));
		a.reset('A', "Ala", 71.03712, 71.08, 1.8, "Alanine C3H5NO", true);
		assertTrue(a.toString()
				+ " == A Ala 71.08 71.0371 1.80 Alanine C3H5NO Visible", a.toString()
				.equals("A Ala 71.08 71.0371 1.80 Alanine C3H5NO Visible"));
		a.clear();
		assertTrue(a.toString() + " ==       0.00 0.0000 0.00  Unvisible", a
				.toString().equals("      0.00 0.0000 0.00  Unvisible"));
	}

}
