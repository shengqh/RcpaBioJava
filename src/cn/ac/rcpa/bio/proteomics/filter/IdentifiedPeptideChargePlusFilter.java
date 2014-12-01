package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideChargePlusFilter implements
		IFilter<IIdentifiedPeptide> {
	private int charge;

	public IdentifiedPeptideChargePlusFilter(int charge) {
		this.charge = charge;
	}

	public boolean accept(IIdentifiedPeptide e) {
		return e.getPeakListInfo().getCharge() >= charge;
	}

	public String getType() {
		return "Charge" + charge + "+";
	}
}
