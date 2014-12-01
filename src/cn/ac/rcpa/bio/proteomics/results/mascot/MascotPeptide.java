package cn.ac.rcpa.bio.proteomics.results.mascot;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IPeakListInfo;
import cn.ac.rcpa.bio.sequest.SequestFilename;

public class MascotPeptide extends AbstractIdentifiedPeptide {

	public IPeakListInfo getPeakListInfo() {
		return filename;
	}

	private SequestFilename filename = new SequestFilename();

	private String title;
	
	private int query;

	private int rank;

	private int missCleavage;

	private int score;

	private double pValue;
	
	private double precursorHydrogenMass;
	
	private String modification = "";

	public double getPValue() {
		return pValue;
	}

	public void setPValue(double value) {
		this.pValue = value;
	}

	public int getMissCleavage() {
		return missCleavage;
	}

	public void setMissCleavage(int missCleavage) {
		this.missCleavage = missCleavage;
	}

	public int getQuery() {
		return query;
	}

	public void setQuery(int query) {
		this.query = query;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setModification(String modification) {
		this.modification = modification;
	}

	public String getModification() {
		return modification;
	}
	
	public double getPrecursorHydrogenMass() {
		return precursorHydrogenMass;
	}

	public void setPrecursorHydrogenMass(double precursorHydrogenMass) {
		this.precursorHydrogenMass = precursorHydrogenMass;
	}
}
