package cn.ac.rcpa.bio.proteomics.image.peptide;

public interface INeutralLossType {
	public double getMass();

	public String getName();

	public boolean canMultipleLoss();
	
	public int getCount();
}
