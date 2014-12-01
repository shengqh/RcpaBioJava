package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.PeakList;

public interface IIdentifiedPeptideResult {
	String getPeptide();

	void setPeptide(String value);

	PeakList<MatchedPeak> getExperimentalPeakList();

	void setExperimentalPeakList(PeakList<MatchedPeak> value);

	Map<IonType, List<MatchedPeak>> getTheoreticalIonSeries();
	
	boolean isBy2Matched();
	
	Map<String, String> getScoreMap();
	
	Map<Character, Double> getDynamicModification();
}