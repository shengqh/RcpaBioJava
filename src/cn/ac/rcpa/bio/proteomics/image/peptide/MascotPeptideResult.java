package cn.ac.rcpa.bio.proteomics.image.peptide;

import cn.ac.rcpa.bio.proteomics.IonType;

public class MascotPeptideResult extends AbstractPeptideResult {
	public MascotPeptideResult(String peptide, IonType[] ionTypes) {
		super(peptide, ionTypes);
	}

	public MascotPeptideResult(String peptide) {
		super(peptide);
	}
}
