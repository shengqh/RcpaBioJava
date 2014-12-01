package cn.ac.rcpa.bio.proteomics.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideHitUtils {
	private IdentifiedPeptideHitUtils() {
	}

	public static <E extends IIdentifiedPeptideHit> List<E> filter(
			List<E> pephits, IFilter<IIdentifiedPeptideHit> filter) {
		ArrayList<E> result = new ArrayList<E>();
		for (E pephit : pephits) {
			if (filter.accept(pephit)) {
				result.add(pephit);
			}
		}
		return result;
	}

	public static void sortByLengthDescendingAndSequenceAscending(
			List<? extends IIdentifiedPeptideHit> peps) {
		Collections.sort(peps, new Comparator<IIdentifiedPeptideHit>() {
			public int compare(IIdentifiedPeptideHit o1, IIdentifiedPeptideHit o2) {
				int result = o2.getPeptide(0).getSequence().length()
						- o1.getPeptide(0).getSequence().length();
				if (result == 0) {
					result = o1.getPeptide(0).getSequence().compareTo(
							o2.getPeptide(0).getSequence());
				}
				return result;
			}
		});
	}

	public static boolean hasDiffModifiedCandidate(
			List<? extends IIdentifiedPeptideHit> peptideHits) {
		boolean hasDiffModifiedCandidate = false;
		for (IIdentifiedPeptideHit hit : peptideHits) {
			if (hit.getFollowCandidates().size() > 0) {
				hasDiffModifiedCandidate = true;
				break;
			}
		}
		return hasDiffModifiedCandidate;
	}

	public static void sortByCandidatePeptideCountAscending(
			List<? extends IIdentifiedPeptideHit> peptideHits) {
		Collections.sort(peptideHits, new Comparator<IIdentifiedPeptideHit>() {
			public int compare(IIdentifiedPeptideHit o1, IIdentifiedPeptideHit o2) {
				return (o1.getPeptideCount() + o1.getFollowCandidates().size())
						- (o2.getPeptideCount() + o2.getFollowCandidates().size());
			}
		});
	}
}
