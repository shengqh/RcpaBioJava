package cn.ac.rcpa.bio.proteomics.results.dtaselect.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.comparison.IdentifiedProteinGroupCoverageCompartor;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.proteomics.results.dtaselect.DTASelectParams;
import cn.ac.rcpa.bio.proteomics.results.dtaselect.DTASelectParamsUtils;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class DtaSelectWriter implements
		IIdentifiedResultWriter<BuildSummaryResult> {
	private DTASelectParams params;

	public DtaSelectWriter(DTASelectParams params) {
		this.params = params;
	}

	public void write(String filename, BuildSummaryResult ir) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(filename));

		write(pw, ir);

		pw.close();
	}

	/**
	 * writeFooter
	 * 
	 * @param pw
	 *          PrintWriter
	 * @param sequestResult
	 *          SequestResult
	 */
	private void writeFooter(PrintWriter pw, BuildSummaryResult sequestResult)
			throws IOException {
		pw.println("\tProteins\tPeptide IDs\tCopies");
		pw.println("Unfiltered\t1\t1");
		pw.println("Redundant\t1\t1");
		pw.println("Nonredundant\t1\t1");
		pw.println();
		pw.println("Classification\tNonredundant\tProteins\tRedundant Proteins");
		pw.println("Unclassified\t0\t0");
	}

	/**
	 * writeItems
	 * 
	 * @param pw
	 *          PrintWriter
	 * @param sequestResult
	 *          SequestResult
	 */
	private void writeItems(PrintWriter pw, BuildSummaryResult sequestResult)
			throws IOException {
		DecimalFormat df = new DecimalFormat("0.0");
		DecimalFormat df2 = new DecimalFormat("0.00");
		DecimalFormat df3 = new DecimalFormat("0.0000");

		sequestResult.sort(IdentifiedProteinGroupCoverageCompartor.getInstance());

		pw
				.println("Locus	Sequence Count	Spectrum Count	Sequence Coverage	Length	MolWt	pI	Validation Status	Descriptive Name");
		pw
				.println("Unique	FileName	XCorr	DeltCN	M+H+	CalcM+H+	TotalIntensity	SpRank	SpScore	IonProportion	Redundancy	Sequence");
		for (int i = 0; i < sequestResult.getProteinGroupCount(); i++) {
			List prohits = sequestResult.getProteinGroup(i).getProteins();
			for (Iterator iter = prohits.iterator(); iter.hasNext();) {
				final BuildSummaryProtein protein = (BuildSummaryProtein) iter.next();
				pw.println(protein.getProteinName()
						+ "\t"
						+ protein.getPeptideCount()
						+ "\t"
						+ protein.getPeptideCount()
						+ "\t"
						+ df.format(protein.getCoverage())
						+ "%\t"
						+ (protein.getSequence() == null ? 0 : protein.getSequence()
								.length()) + "\t" + df2.format(protein.getMW()) + "\t"
						+ df2.format(protein.getPI()) + "\t" + "U" + "\t"
						+ protein.getReference());
			}

			List<BuildSummaryPeptideHit> pephits = sequestResult.getProteinGroup(i)
					.getPeptideHits();
			for (BuildSummaryPeptideHit pephit : pephits) {
				BuildSummaryPeptide peptide = pephit.getPeptide(0);
				pw.println("\t"
						+ RcpaFileUtils.changeExtension(peptide.getPeakListInfo()
								.getLongFilename(), "")
						+ "\t"
						+ df3.format(peptide.getXcorr())
						+ "\t"
						+ df3.format(peptide.getDeltacn())
						+ "\t"
						+ df2.format(peptide.getTheoreticalSingleChargeMass()
								- peptide.getDiffToExperimentMass())
						+ "\t"
						+ df2.format(peptide.getTheoreticalSingleChargeMass())
						+ "\t"
						+ "0.0"
						+ "\t"
						+ peptide.getSpRank()
						+ "\t"
						+ df.format(peptide.getSp())
						+ "\t"
						+ df.format((double) peptide.getMatchCount() * 100
								/ (double) peptide.getTheoreticalCount()) + "\t" + "1" + "\t"
						+ peptide.getSequence());
			}
		}
	}

	/**
	 * writeHeader
	 * 
	 * @param pw
	 *          PrintWriter
	 * @param sequestResult
	 *          SequestResult
	 */
	private void writeHeader(PrintWriter pw, BuildSummaryResult sequestResult)
			throws IOException {
		pw.println("DTASelect v1.9");
		pw.println(params.getDirectory());
		pw.println(params.getDatabase());
		pw.println("TurboSEQUEST - in OUT format.");
		pw.println(DTASelectParamsUtils.getParamString(params));
		pw.println();
		pw.println("true	Use criteria");
		pw.println(params.getXcorrCharge1() + "\tMinimum +1 XCorr");
		pw.println(params.getXcorrCharge2() + "\tMinimum +2 XCorr");
		pw.println(params.getXcorrCharge3() + "\tMinimum +3 XCorr");
		pw.println(params.getDeltacn() + "\tMinimum DeltCN");
		pw.println("1\tMinimum charge state");
		pw.println("3\tMaximum charge state");
		pw.println("0.0\tMinimum ion proportion");
		pw.println(params.getMaxSpRank() + "\tMaximum Sp rank");
		pw.println(params.getMinSpScore() + "\tMinimum Sp score");
		pw.println("Include\tModified peptide inclusion");
		pw.println("Any\tTryptic status requirement");
		pw.println("true\tMultiple, ambiguous IDs allowed");
		pw.println("Ignore\tPeptide validation handling");
		pw.println(params.getPurgeDuplicatePeptide()
				+ "\tPurge duplicate peptides by protein");
		pw.println("false\tInclude only loci with unique peptide");
		pw.println(params.getRemoveSubsetProteins() + "\tRemove subset proteins");
		pw.println("Ignore\tLocus validation handling");
		pw.println("0\tMinimum modified peptides per locus");
		pw.println(params.getMinPeptideCount()
				+ "\tMinimum redundancy for low coverage loci");
		pw.println(params.getMinUniquePeptideCount()
				+ "\tMinimum peptides per locus");
		pw.println();
	}

	/**
	 * write
	 * 
	 * @param writer
	 *          Writer
	 * @param identifiedResult
	 *          IIdentifiedResult
	 */
	public void write(PrintWriter writer, BuildSummaryResult sequestResult)
			throws IOException {
		writeHeader(writer, sequestResult);
		writeItems(writer, sequestResult);
		writeFooter(writer, sequestResult);
	}
}
