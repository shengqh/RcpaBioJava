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

import java.util.List;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

/**
 * @author sqh
 * 
 */
public class IdentifiedPeptideDecoyDatabaseClassification extends
		AbstractDiscreteClassification<IIdentifiedPeptide, Boolean> {

	private Pattern pattern;

	public IdentifiedPeptideDecoyDatabaseClassification(String reversedDbPattern) {
		super();
		pattern = Pattern.compile(reversedDbPattern);
	}

	public void setReversedDbPattern(String reversedDbPattern) {
		pattern = Pattern.compile(reversedDbPattern);
	}

	@Override
	protected Boolean doGetClassification(IIdentifiedPeptide obj) {
		List<String> proteins = obj.getProteinNames();
		for (String protein : proteins) {
			if (pattern.matcher(protein).find()) {
				return true;
			}
		}
		return false;
	}

	public String getPrinciple() {
		return "DecoyDatabase";
	}
}
