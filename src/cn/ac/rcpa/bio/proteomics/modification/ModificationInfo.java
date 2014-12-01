package cn.ac.rcpa.bio.proteomics.modification;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.utils.Pair;

public class ModificationInfo implements Comparable<ModificationInfo> {
	private String modifiedFullSequence;

	private SequenceModificationSitePair pair;

	public ModificationInfo(String modifiedAminoacids, BuildSummaryPeptideHit hit) {
		Pair<String, Integer> pair = ModificationUtils.getModificationInfo(
				modifiedAminoacids, hit);

		this.pair = new SequenceModificationSitePair(modifiedAminoacids, hit);

		this.modifiedFullSequence = pair.fst;
	}

	public String getModifiedFullSequence() {
		return modifiedFullSequence;
	}

	public SequenceModificationSitePair asPair() {
		return pair;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(ModificationInfo object) {
		return new CompareToBuilder().append(this.pair , object.pair).append(
				this.modifiedFullSequence.length(),
				object.modifiedFullSequence.length()).toComparison();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof ModificationInfo)) {
			return false;
		}

		ModificationInfo rhs = (ModificationInfo) object;
		return this.compareTo(rhs) == 0;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-517185447, -1644108975).append(this.pair)
				.append(this.modifiedFullSequence.length()).toHashCode();
	}

	public static final Comparator<ModificationInfo> MODIFIED_COUNT_ORDER = new Comparator<ModificationInfo>() {
		public int compare(ModificationInfo o1, ModificationInfo o2) {
			return new CompareToBuilder().append(-o1.pair.getModifiedCount(),
					-o2.pair.getModifiedCount()).append(o1.pair.getModifiedSequence(),
					o2.pair.getModifiedSequence()).append(
					o1.modifiedFullSequence.length(), o2.modifiedFullSequence.length())
					.toComparison();
		}
	};

}
