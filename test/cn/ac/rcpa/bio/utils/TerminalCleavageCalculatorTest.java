package cn.ac.rcpa.bio.utils;

import org.biojava.bio.BioException;

import junit.framework.TestCase;

public class TerminalCleavageCalculatorTest extends TestCase {
	private TerminalCleavageCalculator calc;

	public TerminalCleavageCalculatorTest() throws BioException{
		calc = new TerminalCleavageCalculator(RcpaProteaseFactory.getInstance().getProteaseByName("Trypsin"));
	}
	/*
	 * Test method for 'cn.ac.rcpa.bio.utils.TerminalCleavageCalculator.isCleavage(char, char)'
	 */
	public void testIsCleavage() {
		assertTrue(calc.isCleavage('-','N'));
		assertTrue(calc.isCleavage('N','-'));
		assertTrue(calc.isCleavage('K','N'));
		assertTrue(calc.isCleavage('R','N'));
		assertFalse(calc.isCleavage('N','K'));
		assertFalse(calc.isCleavage('N','R'));
	}

	/*
	 * Test method for 'cn.ac.rcpa.bio.utils.TerminalCleavageCalculator.getCount(String)'
	 */
	public void testGetCount() {
		assertEquals(2, calc.getCount("R.AAAAAK.N"));
		assertEquals(2, calc.getCount("R.AAAAAN.-"));
		assertEquals(2, calc.getCount("-.AAAAAK.N"));
		assertEquals(1, calc.getCount("R.AAAAAN.N"));
		assertEquals(1, calc.getCount("N.AAAAAK.N"));
		assertEquals(1, calc.getCount("-.AAAAAN.N"));
		assertEquals(1, calc.getCount("N.AAAAAN.-"));
		assertEquals(0, calc.getCount("N.AAAAAN.N"));
	}

}
