/*
 * Created on 2006-2-16
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.classification.impl;

import org.biojava.bio.proteomics.Protease;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.utils.TerminalCleavageCalculator;

public class IdentifiedPeptideNumberOfProteaseTerminalClassification extends
		AbstractDiscreteClassification<IIdentifiedPeptide, Integer> {
	private TerminalCleavageCalculator calc;

	public IdentifiedPeptideNumberOfProteaseTerminalClassification(
			Protease protease) {
		super();
		this.calc = new TerminalCleavageCalculator(protease);
	}

	public String getPrinciple() {
		return "Number of Protease Terminal";
	}

	public void setProtease(Protease value) {
		this.calc = new TerminalCleavageCalculator(value);
	}

	@Override
	protected Integer doGetClassification(IIdentifiedPeptide obj) {
		String seq = PeptideUtils.removeModification(obj.getSequence());
		return calc.getCount(seq);
	}
}
