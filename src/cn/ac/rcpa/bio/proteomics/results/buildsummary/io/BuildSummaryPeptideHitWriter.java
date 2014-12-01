package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashSet;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.sequest.SequestFilename;
import cn.ac.rcpa.bio.utils.IsoelectricPointCalculator;

public class BuildSummaryPeptideHitWriter extends
		AbstractBuildSummaryPeptideHitWriter {
	public BuildSummaryPeptideHitWriter() {
		super();
	}

	protected String getProtein(BuildSummaryPeptide pep) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < pep.getProteinNameCount(); j++) {
			if (j != 0) {
				sb.append('/');
			}
			sb.append(pep.getProteinName(j));
		}
		return sb.toString();
	}

	public String getPeptideSequence(BuildSummaryPeptideHit pephit) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pephit.getPeptideCount(); i++) {
			sb.append((i == 0 ? "" : " ! "));
			sb.append(pephit.getPeptide(i).getSequence());
		}

		return sb.toString();
	}

	public String getProteinInfo(BuildSummaryPeptideHit pephit) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pephit.getPeptideCount(); i++) {
			sb.append((i == 0 ? "" : " ! "));
			sb.append(getProtein(pephit.getPeptide(i)));
		}

		return sb.toString();
	}

	public String getFollowCandidates(BuildSummaryPeptideHit pephit) {
		return pephit.getFollowCandidates().toString();
	}

	/**
	 * writePeptideHit
	 * 
	 * @param writer
	 *          Writer
	 * @param peptideHits
	 *          SequestPeptideHit[]
	 * @param iBegin
	 *          int
	 * @param i
	 *          int
	 */
	public void writePeptideHit(PrintWriter writer, BuildSummaryPeptideHit pephit)
			throws IOException {
		if (pephit.getPeptideCount() == 0) {
			throw new IllegalArgumentException(
					"Argument pephit of writePeptideHit is empty!");
		}
		BuildSummaryPeptide peptide = (BuildSummaryPeptide) pephit.getPeptide(0);

		DecimalFormat df5 = new DecimalFormat("#0.00000");
		DecimalFormat df4 = new DecimalFormat("#0.0000");
		DecimalFormat df2 = new DecimalFormat("0.00");

		final SequestFilename sname = (SequestFilename) peptide.getPeakListInfo();
		writer.print("\t" + sname.getShortFilename());

		writer.print("\t" + getPeptideSequence(pephit));

		writer.print("\t" + df5.format(peptide.getTheoreticalSingleChargeMass()));
		if (peptide.getDiffToExperimentMass() < 0.00001
				&& peptide.getDiffToExperimentMass() > -0.00001) {
			writer.print("\t0.00000");
		} else {
			writer.print("\t" + df5.format(peptide.getDiffToExperimentMass()));
		}
		writer.print("\t" + peptide.getCharge() + "\t" + peptide.getRank() + "\t"
				+ df4.format(peptide.getXcorr()) + "\t"
				+ df4.format(peptide.getDeltacn()) + "\t" + peptide.getSp() + "\t"
				+ peptide.getSpRank() + "\t" + peptide.getMatchCount() + "|"
				+ peptide.getTheoreticalCount());

		writer.print("\t" + getProteinInfo(pephit));

		writer.print("\t" + getFollowCandidates(pephit));

		writer.print("\t"
				+ df2.format(IsoelectricPointCalculator.getPI(PeptideUtils
						.getPurePeptideSequence(peptide.getSequence()))));
	}

	/**
	 * writeHeader
	 * 
	 * @param writer
	 *          Writer
	 * @param peptideHits
	 *          List<BuildSummaryPeptideHit>
	 */
	@Override
	public void writeHeader(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException {
		writer
				.print("\t\"File, Scan(s)\"\tSequence\tMH+\tDiff(MH+)\tCharge\tRank\tXC\tDeltaCn\tSp\tRSp\tIons\tReference\tDIFF_MODIFIED_CANDIDATE\tPI");
	}

	/**
	 * writeItems
	 * 
	 * @param writer
	 *          Writer
	 * @param peptideHits
	 *          List<BuildSummaryPeptideHit>
	 */
	@Override
	public void writeItems(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException {
		for (BuildSummaryPeptideHit hit : peptideHits) {
			writePeptideHit(writer, hit);
			writer.println();
		}
	}

	/**
	 * writeFooter
	 * 
	 * @param writer
	 *          Writer
	 * @param peptideHits
	 *          SequestPeptideHit[]
	 */
	@Override
	public void writeFooter(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException {
		writer.println();
		writer.println("----- summary -----");
		writer.println("Total peptides: " + peptideHits.size());
		writer.println("Unique peptides: " + getUniquePeptideCount(peptideHits));
	}

	public int getUniquePeptideCount(Collection<BuildSummaryPeptideHit> peptideHits) {
		HashSet<String> unique = new HashSet<String>();
		for (BuildSummaryPeptideHit hit : peptideHits) {
			unique.add(PeptideUtils.getPurePeptideSequence(hit.getPeptide(0)
					.getSequence()));
		}
		return unique.size();
	}

}
