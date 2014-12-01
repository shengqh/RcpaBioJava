package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

public class IdentifiedPeptidePrecursorDiffMzClassification extends
		AbstractContinuousClassification<IIdentifiedPeptide> {
	public IdentifiedPeptidePrecursorDiffMzClassification(double[] ranges) {
		super(ranges);
	}

	@Override
	protected double getValue(IIdentifiedPeptide obj) {
		return obj.getDiffToExperimentMass();
	}

	public String getPrinciple() {
		return "PrecursorDiffMz";
	}
}
