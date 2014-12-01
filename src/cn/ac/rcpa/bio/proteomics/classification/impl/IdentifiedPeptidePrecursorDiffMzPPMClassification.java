package cn.ac.rcpa.bio.proteomics.classification.impl;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.PrecursorTolerance;

public class IdentifiedPeptidePrecursorDiffMzPPMClassification extends AbstractContinuousClassification<IIdentifiedPeptide>{
  public IdentifiedPeptidePrecursorDiffMzPPMClassification(double[] ranges) {
    super(ranges);
  }

  @Override
  protected double getValue(IIdentifiedPeptide obj) {
  	double diff =obj.getDiffToExperimentMass() / obj.getCharge();
  	double mz = obj.getExperimentalSingleChargeMass() / obj.getCharge();
  	return PrecursorTolerance.mz2ppm(mz, diff);
  }

  public String getPrinciple() {
    return "PrecursorDiffMzPPM";
  }
}
