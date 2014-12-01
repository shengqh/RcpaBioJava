package cn.ac.rcpa.bio.proteomics.detectability;


public interface IProteinProbabilityCalculator {
	double getProbability(ProteinDetectabilityEntry protein);
}
