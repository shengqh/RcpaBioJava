/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.classification.impl;

import picalculator.pIcalculator;
import cn.ac.rcpa.bio.proteomics.modification.SequenceModificationSitePair;

public class PhosphoPeptideSequencePIClassification extends
		AbstractContinuousClassification<String> {
	private pIcalculator pc;

	public PhosphoPeptideSequencePIClassification(pIcalculator pc,
			double[] ranges) {
		super(ranges);
		this.pc = pc;
	}

	public PhosphoPeptideSequencePIClassification(double[] ranges) {
		super(ranges);
		this.pc = new pIcalculator();
		pc.setAApI("expasy");
		pc.setpKaSet("scansite");
		pc.setMethylatedPeptides(false);
	}

	private String getPhosphoPeptide(String obj) {
		String pepti = new SequenceModificationSitePair("STY", obj, '#')
				.toString();
		pepti = pepti.replaceAll("S#", "pS").replaceAll("T#", "pT").replaceAll(
				"Y#", "pY");
		return pepti;
	}

	public int getPhosphoNumber(String obj) {
		return pc.getPhosphoNumber(getPhosphoPeptide(obj));
	}

	public double getPhosphoPI(String obj) {
		return getValue(obj);
	}

	@Override
	protected double getValue(String obj) {
		String pepti = getPhosphoPeptide(obj);
		int phosphoNumber = pc.getPhosphoNumber(pepti);
		pc.setPhosfoNumber(phosphoNumber);
		double outcome = pc.calculate(pepti);
		if (outcome > 20) {
			return 0.0;
		} else {
			return outcome;
		}
	}

	public String getPrinciple() {
		return "PhosphoIsotopicPoint";
	}
}
