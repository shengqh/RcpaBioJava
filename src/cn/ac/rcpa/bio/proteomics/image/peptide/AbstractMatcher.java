package cn.ac.rcpa.bio.proteomics.image.peptide;

import cn.ac.rcpa.chem.Atom;

public abstract class AbstractMatcher implements IPeakMatcher {
	protected static final double massH = Atom.H.getMono_isotopic().getMass();

	protected static final double massO = Atom.O.getMono_isotopic().getMass();

	protected static final double massN = Atom.N.getMono_isotopic().getMass();

	protected static final double massP = Atom.P.getMono_isotopic().getMass();

	protected static final double massH2O = massH * 2 + massO;

	protected static final double massNH3 = massH * 3 + massN;

	protected static final double massH3PO4 = massH * 3 + massP + massO * 4;

	protected static final double massHPO3 = massH + massP + massO * 3;

	protected static NeutralLossType NL_AMMONIA = new NeutralLossType(massNH3,
			"NH3", false);

	protected static NeutralLossType NL_WATER = new NeutralLossType(massH2O,
			"H2O", false);

	protected static NeutralLossType NL_H3PO4 = new NeutralLossType(massH3PO4,
			"H3PO4", true);

	protected static NeutralLossType NL_HPO3 = new NeutralLossType(massHPO3,
			"HPO3", true);

	public abstract void match(IIdentifiedPeptideResult sr);
}
