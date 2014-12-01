package cn.ac.rcpa.bio.proteomics.results.mascot;

import cn.ac.rcpa.filter.IFilter;

public class MascotPeptidePValueFilter implements IFilter<MascotPeptide> {
	private double maxPValue;

	public MascotPeptidePValueFilter(double maxPValue) {
		this.maxPValue = maxPValue;
	}

	public boolean accept(MascotPeptide e) {
		return e.getPValue() <= maxPValue;
	}

	public String getType() {
		return "MascotPeptidePValueFilter";
	}

}
