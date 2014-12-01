package cn.ac.rcpa.bio.proteomics;

public interface IPeakListInfo {
	public String getExperiment();

	public void setExperiment(String experiment);

	public int getFirstScan();

	public void setFirstScan(int firstScan);

	public int getLastScan();

	public void setLastScan(int lastScan);

	public int getCharge();

	public void setCharge(int charge);
	
	public String getExtension();
	
	public void setExtension(String value);

	public String getLongFilename();

	public String getShortFilename();
}
