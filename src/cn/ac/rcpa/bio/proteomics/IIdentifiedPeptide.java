package cn.ac.rcpa.bio.proteomics;

import java.util.List;

public interface IIdentifiedPeptide extends Comparable<IIdentifiedPeptide> {
  int getProteinNameCount();

  List<String> getProteinNames();

  String getProteinName(int index);

  void addProteinName(String proteinName);

  void removeProteinName(String proteinName);

  void clearProteinNames();

  String getSequence();

  void setSequence(String sequence);

  IPeakListInfo getPeakListInfo();
  
  int getCharge();
  
  void setCharge(int charge);

  double getObservedMz();

  void setObservedMz(double observedMz);

  double getTheoreticalSingleChargeMass();

  void setTheoreticalSingleChargeMass(double singleChargeMass);

	double getExperimentalSingleChargeMass();
	
	void setExperimentalSingleChargeMass(double experimentalSingleChargeMass);

	//getExperimentalSingleChargeMass() - getTheoreticalSingleChargeMass()
  double getDiffToExperimentMass();

  double getPI();

  FollowCandidatePeptideList getFollowCandidates();

}
