package cn.ac.rcpa.bio.proteomics.filter;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.sequest.SequestParams;
import cn.ac.rcpa.bio.sequest.SequestParamsIOFactory;
import cn.ac.rcpa.bio.utils.MassCalculator;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideN15Filter implements IFilter<IIdentifiedPeptide> {
	private SequestParams sequestParams;

	private MassCalculator mcPrecursor;

	public IdentifiedPeptideN15Filter(String sequestParamFile)
			throws FileNotFoundException, IOException {
		sequestParams = SequestParamsIOFactory.loadFromFile(sequestParamFile);
		mcPrecursor = new MassCalculator(sequestParams.isPrecursorMonoisotopic());
		mcPrecursor.addDynamicModifications(sequestParams.getDynamic_modification()
				.getModificationMap());
		mcPrecursor.addStaticModifications(sequestParams.getStatic_modification()
				.getModificationMap(sequestParams.isPrecursorMonoisotopic()));
	}

	private double getPrecursorMass(String peptide) {
		double result = mcPrecursor.getMass(peptide);
		if (sequestParams.isPrecursorMonoisotopic()) {
			result += MassCalculator.Hmono;
		} else {
			result += MassCalculator.Havg;
		}
		return result;
	}

	public boolean accept(IIdentifiedPeptide e) {
		String sequence = PeptideUtils.getMatchPeptideSequence(e.getSequence());
		double pepMass = getPrecursorMass(sequence);
		double gap = Math.abs(pepMass - e.getTheoreticalSingleChargeMass());
		return gap <= 0.5;
	}

	public String getType() {
		return "N15";
	}
}
