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

import org.biojava.bio.proteomics.Protease;

public class IdentifiedPeptideMissedCleavagesClassification extends
		IdentifiedPeptideClassificationBasedOnSequence {

	public IdentifiedPeptideMissedCleavagesClassification(Protease protease) {
		super(new PeptideSequenceMissedCleavagesClassification(protease));
	}

	public void setProtease(Protease value) {
		super.seqClassification = new PeptideSequenceMissedCleavagesClassification(
				value);
	}
}
