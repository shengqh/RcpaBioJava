package cn.ac.rcpa.bio.proteomics.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.ac.rcpa.bio.sequest.SequestFilename;

public class SlimIdentifiedPeptide {
	private String sequence;

	public String getSequence() {
		return sequence;
	}
	
	public void setSequence(String value){
		this.sequence = value;
	}

	public SequestFilename getFileName() {
		return fileName;
	}

	private SequestFilename fileName;

	public int getCharge() {
		return fileName.getCharge();
	}

	private Map<String, String> scoreMap = new LinkedHashMap<String, String>();

	public Map<String, String> getScoreMap() {
		return scoreMap;
	}

	public SlimIdentifiedPeptide(String sequence, SequestFilename sf) {
		this.sequence = sequence;
		this.fileName = sf;
	}
	
	public void setModification(String modification) {
		this.modification = modification;
	}

	public String getModification() {
		return modification;
	}

	private String modification;
	
}
