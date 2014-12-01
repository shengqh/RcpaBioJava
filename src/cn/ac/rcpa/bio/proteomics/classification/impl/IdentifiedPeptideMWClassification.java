/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author sqh
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.classification.impl;

public class IdentifiedPeptideMWClassification extends
		IdentifiedPeptideClassificationBasedOnSequence {
	public IdentifiedPeptideMWClassification(double[] ranges, boolean monoIsotopic) {
		super(new PeptideSequenceMWClassification(ranges, monoIsotopic));
	}

	public void setMonoIsotopic(boolean value) {
		((PeptideSequenceMWClassification) this.seqClassification)
				.setMonoIsotopic(value);
	}
}
