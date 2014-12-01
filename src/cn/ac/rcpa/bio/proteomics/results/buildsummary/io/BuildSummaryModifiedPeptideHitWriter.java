package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;

import cn.ac.rcpa.bio.proteomics.FollowCandidatePeptide;
import cn.ac.rcpa.bio.proteomics.FollowCandidatePeptideList;
import cn.ac.rcpa.bio.proteomics.modification.ModificationUtils;
import cn.ac.rcpa.bio.proteomics.modification.SequenceModificationSitePair;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.utils.Pair;

public class BuildSummaryModifiedPeptideHitWriter extends
		BuildSummaryPeptideHitWriter {
	private String modifiedAminoacid;

	public BuildSummaryModifiedPeptideHitWriter(String modifiedAminoacid) {
		super();
		this.modifiedAminoacid = modifiedAminoacid;
	}

	@Override
	public String getPeptideSequence(BuildSummaryPeptideHit pephit) {
		Pair<String, Integer> modifiedInfo = ModificationUtils.getModificationInfo(
				modifiedAminoacid, pephit);
		return modifiedInfo.fst;
	}

	@Override
	public String getProteinInfo(BuildSummaryPeptideHit pephit) {
		return getProtein(pephit.getPeptide(0));
	}

	@Override
	public String getFollowCandidates(BuildSummaryPeptideHit pephit) {
		FollowCandidatePeptideList list = new FollowCandidatePeptideList();
		for (int i = 0; i < pephit.getPeptideCount(); i++) {
			list.add(new FollowCandidatePeptide(pephit.getPeptide(i).getSequence(),
					pephit.getPeptide(i).getXcorr(), pephit.getPeptide(i).getDeltacn()));
		}
		list.addAll(pephit.getFollowCandidates());

		return list.toString();
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
		super.writeFooter(writer, peptideHits);
		writer.println("Unique modified peptides: "
				+ getUniqueModifiedPeptideCount(peptideHits));
	}

	public int getUniqueModifiedPeptideCount(
			Collection<BuildSummaryPeptideHit> peptideHits) {
		HashSet<String> uniqueModified = new HashSet<String>();
		for (BuildSummaryPeptideHit hit : peptideHits) {
			Pair<String, Integer> modifiedInfo = ModificationUtils
					.getModificationInfo(modifiedAminoacid, hit);
			uniqueModified.add(modifiedInfo.fst + "-" + modifiedInfo.snd);
		}
		return uniqueModified.size();
	}

	@Override
	public void writeHeader(PrintWriter writer,
			Collection<BuildSummaryPeptideHit> peptideHits) throws IOException {
		super.writeHeader(writer, peptideHits);
		writer.print("\tModifiedCount");
	}

	@Override
	public void writePeptideHit(PrintWriter writer, BuildSummaryPeptideHit pephit)
			throws IOException {
		super.writePeptideHit(writer, pephit);
		SequenceModificationSitePair pair = new SequenceModificationSitePair(
				modifiedAminoacid, pephit);
		writer.print("\t" + pair.getModifiedCount());
	}

}
