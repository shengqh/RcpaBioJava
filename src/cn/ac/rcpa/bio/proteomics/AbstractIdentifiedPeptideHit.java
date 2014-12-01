package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractIdentifiedPeptideHit<E extends AbstractIdentifiedPeptide>
		implements IIdentifiedPeptideHit<E> {
	protected List<E> peptides = new ArrayList<E>();

	public AbstractIdentifiedPeptideHit() {
	}

	public void addPeptide(E peptide) {
		peptides.add(peptide);
	}

	public int getPeptideCount() {
		return peptides.size();
	}

	public List<E> getPeptides() {
		return Collections.unmodifiableList(peptides);
	}

	public E getPeptide(int index) {
		return peptides.get(index);
	}

	public void removePeptide(E value) {
		peptides.remove(value);
	}

	public void removePeptide(int index) {
		peptides.remove(index);
	}

	public void clearPeptides() {
		peptides.clear();
	}

	public IPeakListInfo getPeakListInfo() {
		if (peptides.size() == 0) {
			throw new IllegalStateException("IIdentifiedPeptideHit is empty!");
		}

		return peptides.get(0).getPeakListInfo();
	}

	public FollowCandidatePeptideList getFollowCandidates() {
		if (peptides.size() == 0) {
			throw new IllegalStateException("IIdentifiedPeptideHit is empty!");
		}

		return peptides.get(0).getFollowCandidates();
	}

	public int compareTo(IIdentifiedPeptideHit<E> arg0) {
		return this.getPeptide(0).compareTo(arg0.getPeptide(0));
	}

	public List<String> getPeptideSequences() {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < getPeptideCount(); i++) {
			result.add(getPeptide(i).getSequence());
		}

		for (int i = 0; i < getFollowCandidates().size(); i++) {
			result.add(getFollowCandidates().get(i).getSequence());
		}
		return result;
	}

}
