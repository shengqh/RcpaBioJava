/*
 * Created on 2005-11-14
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.utils;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.aminoacid.Aminoacids;
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;

public class TestMassCalculator extends TestCase {
	private final static double sty_mod = 79.966;

	private final static double sty_ms3_mod = -18.000;

	private final static double m_mod = 15.995;

	private final static double c_static_mod = 160.1652;

	private final static double nterm_mod = 42.01;

	private static String peptide;

	private static MassCalculator mc;

	static {
		peptide = "SS*S#S";
		mc = new MassCalculator(true);
		mc.addDynamicModification('*', sty_mod);
		mc.addDynamicModification('@', sty_ms3_mod);
		mc.addDynamicModification('#', m_mod);
		mc.addDynamicModification('~', nterm_mod);
		mc.addStaticModification('C', c_static_mod);
	}

	public void testGetAminoacidCount() {
		assertEquals(4, mc.getAminoacidCount("S*S@S#S"));
		assertEquals(4, mc.getAminoacidCount("@S*SS#S"));
	}

	public void testStaticModification() throws SequenceValidateException {
		assertEquals(18.0 + c_static_mod, mc.getMass("C"), 0.1);
	}

	public void testGetMassNtermDynamicModification()
			throws SequenceValidateException {
		assertEquals(mc.getMass("SSSS") + nterm_mod, mc.getMass("~SSSS"), 0.001);
	}

	public void testGetMass() throws SequenceValidateException {
		assertEquals(366.139, mc.getMass("SSSS"), 0.001);
		assertEquals(mc.getMass("SSSS") + sty_mod, mc.getMass("SS*SS"), 0.001);
		assertEquals(mc.getMass("SSSS") + sty_mod + m_mod,
				mc.getMass("SS*S#S"), 0.001);
		assertEquals(1769.993, mc.getMass("LY*CS*ECDKCFTK"), 0.001);

		String proteinSeq = "QVAERYLEIR";
		double expectedReturn = Aminoacids.getStableInstance()
				.getMonoResiduesMass(proteinSeq);
		MassCalculator massCalculator = new MassCalculator(true, 0.0, 0.0);
		double actualReturn = massCalculator.getMass(proteinSeq);
		assertEquals(expectedReturn, actualReturn, 0.0001);

		massCalculator = new MassCalculator(true);
		expectedReturn = Aminoacids.getStableInstance().getMonoResiduesMass(
				proteinSeq)
				+ MassCalculator.Hmono * 2 + MassCalculator.Omono;
		actualReturn = massCalculator.getMass(proteinSeq);
		assertEquals(expectedReturn, actualReturn, 0.0001);

		proteinSeq = "GSNFDC*ELR";
		expectedReturn = Aminoacids.getStableInstance().getMonoResiduesMass(
				proteinSeq) + 9.0;
		massCalculator = new MassCalculator(true, 0.0, 0.0);
		massCalculator.addDynamicModification('*', 9.0);
		actualReturn = massCalculator.getMass(proteinSeq);
		assertEquals(expectedReturn, actualReturn, 0.0001);
	}

	public void testGetBIonsAndGetFullBIons() throws SequenceValidateException {
		double[] bFullIons = mc.getFullBSeries(peptide);
		assertEquals(4, bFullIons.length);
		assertEquals(88.040, bFullIons[0], 0.001);
		assertEquals(255.038, bFullIons[1], 0.001);
		assertEquals(358.065, bFullIons[2], 0.001);
		assertEquals(445.097, bFullIons[3], 0.001);

		double[] bFullIonsWithNtermModification = mc.getFullBSeries("~" + peptide);
		assertEquals(4, bFullIonsWithNtermModification.length);
		assertEquals(bFullIons[0] + nterm_mod, bFullIonsWithNtermModification[0], 0.001);
		assertEquals(bFullIons[1] + nterm_mod, bFullIonsWithNtermModification[1], 0.001);
		assertEquals(bFullIons[2] + nterm_mod, bFullIonsWithNtermModification[2], 0.001);
		assertEquals(bFullIons[3] + nterm_mod, bFullIonsWithNtermModification[3], 0.001);

		double[] bIons = mc.getBSeries(peptide);
		assertEquals(3, bIons.length);
		assertEquals(bFullIons[0], bIons[0], 0.001);
		assertEquals(bFullIons[1], bIons[1], 0.001);
		assertEquals(bFullIons[2], bIons[2], 0.001);

		double[] bIonsWithNtermModification = mc.getBSeries("~" + peptide);
		assertEquals(3, bIonsWithNtermModification.length);
		assertEquals(bIons[0] + nterm_mod, bIonsWithNtermModification[0], 0.001);
		assertEquals(bIons[1] + nterm_mod, bIonsWithNtermModification[1], 0.001);
		assertEquals(bIons[2] + nterm_mod, bIonsWithNtermModification[2], 0.001);
	}

	public void testGetYIonsAndGetFullYIons() throws SequenceValidateException {
		double[] yFullIons = mc.getFullYSeries(peptide);
		assertEquals(4, yFullIons.length);
		assertEquals(106.050, yFullIons[0], 0.001);
		assertEquals(209.077, yFullIons[1], 0.001);
		assertEquals(376.076, yFullIons[2], 0.001);
		assertEquals(463.108, yFullIons[3], 0.001);

		double[] yFullIonsWithNTermModification = mc.getFullYSeries("~" + peptide);
		assertEquals(4, yFullIonsWithNTermModification.length);
		assertEquals(yFullIons[0], yFullIonsWithNTermModification[0], 0.001);
		assertEquals(yFullIons[1], yFullIonsWithNTermModification[1], 0.001);
		assertEquals(yFullIons[2], yFullIonsWithNTermModification[2], 0.001);
		assertEquals(yFullIons[3], yFullIonsWithNTermModification[3], 0.001);

		double[] yIons = mc.getYSeries(peptide);
		assertEquals(3, yIons.length);
		assertEquals(yFullIons[0], yIons[0], 0.001);
		assertEquals(yFullIons[1], yIons[1], 0.001);
		assertEquals(yFullIons[2], yIons[2], 0.001);
	}

	public void testGetBSeries() throws SequenceValidateException {
		String proteinSeq = "QVAERYLEIR";
		MassCalculator massCalculator = new MassCalculator(true);
		double[] actualReturn = massCalculator.getBSeries(proteinSeq);
		for (int i = 0; i < proteinSeq.length() - 1; i++) {
			double expectedReturn = massCalculator.getMass(proteinSeq
					.substring(0, i + 1))
					- massCalculator.getCTermMass()
					- massCalculator.getNTermMass() + MassCalculator.Hmono;
			assertEquals("return value", expectedReturn, actualReturn[i],
					0.0001);
		}

		proteinSeq = "Q*VAERYLEIR";
		massCalculator.addDynamicModification('*', 9.0);
		actualReturn = massCalculator.getBSeries(proteinSeq);

		proteinSeq = "QVAERYLEIR";
		massCalculator.removeDynamicModification('*');
		double[] expectedReturn = massCalculator.getBSeries(proteinSeq);
		for (int i = 0; i < actualReturn.length; i++) {
			assertEquals("return value " + i, expectedReturn[i] + 9.0,
					actualReturn[i], 0.0001);
		}
	}

	public void testGetYSeries() throws SequenceValidateException {
		String proteinSeq = "QVAERYLEIR";
		MassCalculator massCalculator = new MassCalculator(true);
		double[] actualReturn = massCalculator.getYSeries(proteinSeq);
		for (int i = proteinSeq.length() - 1, j = 0; i > 0; i--, j++) {
			double expectedReturn = massCalculator.getMass(proteinSeq
					.substring(i))
					- massCalculator.getNTermMass() + 2 * MassCalculator.Hmono;
			assertEquals("return value", expectedReturn, actualReturn[j],
					0.0001);
		}

		proteinSeq = "Q*VAERYLEIR";
		massCalculator.addDynamicModification('*', 9.0);
		actualReturn = massCalculator.getBSeries(proteinSeq);

		proteinSeq = "QVAERYLEIR";
		massCalculator.removeDynamicModification('*');
		double[] expectedReturn = massCalculator.getBSeries(proteinSeq);
		for (int i = 1; i < actualReturn.length; i++) {
			assertEquals("return value", expectedReturn[i] + 9.0,
					actualReturn[i], 0.0001);
		}
	}

	private void catchSequenceValidateException(MassCalculator massCalculator,
			String sequence, boolean catched) {
		try {
			massCalculator.validateSequence(sequence);
			assertTrue("SequenceValidateException doesn't catched!", !catched);
		} catch (SequenceValidateException ex) {
			assertTrue("SequenceValidateException catched!" + ex.getMessage(),
					catched);
		}
	}

	public void testValidateSequence() {
		MassCalculator massCalculator = new MassCalculator(true);

		String sequence;

		sequence = "MNSTUVWXYZ";
		catchSequenceValidateException(massCalculator, sequence, true);

		sequence = "*QVAERYLEIR";
		catchSequenceValidateException(massCalculator, sequence, true);

		sequence = "Q*VAERYLEIR";
		catchSequenceValidateException(massCalculator, sequence, true);

		sequence = "Q1VAERYLEIR";
		catchSequenceValidateException(massCalculator, sequence, true);

		sequence = "Q.VAERYLEIR";
		catchSequenceValidateException(massCalculator, sequence, true);

		sequence = "Q*VAERYLEIR";
		massCalculator.addDynamicModification('*', 9.0);
		catchSequenceValidateException(massCalculator, sequence, false);
	}

}
