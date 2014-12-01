package cn.ac.rcpa.bio.proteomics.modification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.Pair;

public class SequenceModificationSitePair implements
		Comparable<SequenceModificationSitePair> {
	public static final char MULTI_STATE_SITE = '&';

	public static final char AMBIGUOUS_SITE = 'p';

	private ArrayList<Pair<Character, Character>> sites = new ArrayList<Pair<Character, Character>>();

	private int modifiedCount;

	private int trueModifiedCount;

	private int ambiguousModifiedCount;

	private int multistateModifiedCount;

	private String pureSequence;

	private String modifiedSequence;

	private boolean isCandidateModifiedAminoacid(String modifiedAminoacids,
			char aminoacid) {
		return modifiedAminoacids.indexOf(aminoacid) != -1;
	}

	private Pair<Character, Character> getLastPair() {
		return sites.get(sites.size() - 1);
	}

	public SequenceModificationSitePair(String modifiedAminoacids,
			IIdentifiedPeptideHit<? extends IIdentifiedPeptide> hit, char modifiedSign) {
		initByPeptideHit(modifiedAminoacids, hit, modifiedSign);
	}

	private void initByPeptideHit(String modifiedAminoacids,
			IIdentifiedPeptideHit<? extends IIdentifiedPeptide> hit, char modifiedSign) {
		List<String> peptideSequences = hit.getPeptideSequences();

		this.init(modifiedAminoacids, peptideSequences.get(0), modifiedSign);

		for (int i = 1; i < peptideSequences.size(); i++) {
			SequenceModificationSitePair ambi = new SequenceModificationSitePair(
					modifiedAminoacids, peptideSequences.get(i));
			this.mergeWithAmbiguous(ambi);
		}
	}

	public SequenceModificationSitePair(String modifiedAminoacids,
			IIdentifiedPeptideHit<? extends IIdentifiedPeptide> hit) {
		initByPeptideHit(modifiedAminoacids, hit, ' ');
	}

	private void init(SequenceModificationSitePair source) {
		sites.clear();
		for (int i = 0; i < source.sites.size(); i++) {
			sites.add(new Pair<Character, Character>(source.sites.get(i)));
		}
		this.modifiedCount = source.modifiedCount;
		this.trueModifiedCount = source.trueModifiedCount;
		this.ambiguousModifiedCount = source.ambiguousModifiedCount;
		this.pureSequence = source.pureSequence;
		this.modifiedSequence = source.modifiedSequence;
		this.multistateModifiedCount = source.multistateModifiedCount;
	}

	public SequenceModificationSitePair(SequenceModificationSitePair source) {
		super();
		init(source);
	}

	public SequenceModificationSitePair(String modifiedAminoacids,
			String sequence, char modifiedSign) {
		super();
		init(modifiedAminoacids, sequence, modifiedSign);
	}

	public SequenceModificationSitePair(String modifiedAminoacids, String sequence) {
		super();
		init(modifiedAminoacids, sequence, ' ');
	}

	private void init(String modifiedAminoacids, String sequence,
			char modifiedSign) {
		String matchSequence = PeptideUtils.getMatchPeptideSequence(sequence);
		this.pureSequence = PeptideUtils.getPurePeptideSequence(matchSequence);

		modifiedCount = 0;

		for (int i = 0; i < matchSequence.length(); i++) {
			char c = matchSequence.charAt(i);
			if (Character.isLetter(c) && Character.isUpperCase(c)) {
				sites.add(new Pair<Character, Character>(c, ' '));
			} else if (isCandidateModifiedAminoacid(modifiedAminoacids,
					getLastPair().fst)) {
				if (modifiedSign == ' ') {
					getLastPair().snd = c;
				} else {
					getLastPair().snd = modifiedSign;
				}
				modifiedCount++;
			}
		}
		calculate();

	}

	public void mergeWithAmbiguous(SequenceModificationSitePair another) {
		if (another.sites.size() != sites.size()) {
			throw new IllegalArgumentException(
					"Maybe those pairs are not from same out file : this="
							+ this.toString() + " ; argument=" + another.toString());
		}

		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).snd.equals(another.sites.get(i).snd)) {
				continue;
			} else if (sites.get(i).snd.equals(' ')
					|| another.sites.get(i).snd.equals(' ')) {
				sites.get(i).snd = AMBIGUOUS_SITE;
			}
		}

		calculate();
	}

	private boolean isModified(int index) {
		Character c = this.sites.get(index).snd;
		return !c.equals(' ');
	}

	private boolean isMultipleStateModified(int index) {
		Character c = this.sites.get(index).snd;
		return c.equals(MULTI_STATE_SITE);
	}

	private boolean isPositiveModified(int index) {
		return isModified(index) && !isAmbiguousModified(index);
	}

	private boolean isAmbiguousModified(int index) {
		return this.sites.get(index).snd.equals(AMBIGUOUS_SITE);
	}

	private void checkModificationSiteCount() {
		if (this.modifiedCount < this.trueModifiedCount) {
			this.modifiedCount = this.trueModifiedCount;
		}

		if (this.modifiedCount == this.trueModifiedCount
				&& this.ambiguousModifiedCount != 0) {
			this.removeAmbiguousModifiedSite();
			calculate();
		}
	}

	public void mergeWithSurePeptide(SequenceModificationSitePair another) {
		// 首先将确定的位点全部赋值
		SequenceModificationSitePair tmp2 = new SequenceModificationSitePair(
				another);
		tmp2.mergeTrueModificationSite(this);
		this.mergeTrueModificationSite(another);

		if (tmp2.modifiedCount - tmp2.trueModifiedCount == this.modifiedCount
				- this.trueModifiedCount) {
			if (tmp2.ambiguousModifiedCount > 0 && this.containAllAmbiguousSite(tmp2)) {
				this.replaceAmbiguousSite(tmp2);
			} else if (this.ambiguousModifiedCount > 0
					&& this.allAmbiguousSiteContainedIn(tmp2)) {
				return;
			}
		}
		this.mergeAmbiguousSites(tmp2);
		//this.modifiedCount = Math.max(this.modifiedCount, another.modifiedCount);
	}

	public void mergeAmbiguousSites(SequenceModificationSitePair shorter) {
		int ipos = this.pureSequence.indexOf(shorter.pureSequence);
		if (ipos == -1) {
			throw new IllegalArgumentException(this.pureSequence
					+ " doesn't include " + shorter.pureSequence);
		}

		for (int i = 0; i < shorter.sites.size(); i++) {
			if (shorter.isAmbiguousModified(i) && !this.isModified(i + ipos)) {
				this.sites.get(i + ipos).snd = shorter.sites.get(i).snd;
			}
		}
		
		if(this.modifiedCount < shorter.modifiedCount){
			this.modifiedCount = shorter.modifiedCount;
		}
		calculate();
	}

	public void replaceAmbiguousSite(SequenceModificationSitePair shorter) {
		int ipos = this.pureSequence.indexOf(shorter.pureSequence);
		if (ipos == -1) {
			throw new IllegalArgumentException(this.pureSequence
					+ " doesn't include " + shorter.pureSequence);
		}

		this.removeAmbiguousModifiedSite();

		for (int i = 0; i < shorter.sites.size(); i++) {
			if (shorter.isAmbiguousModified(i) && !this.isModified(i + ipos)) {
				this.sites.get(i + ipos).snd = shorter.sites.get(i).snd;
			}
		}
		calculate();
	}

	public boolean allModificationSiteContainedIn(
			SequenceModificationSitePair longer) {
		if (!this.pureSequence.equals(longer.pureSequence)) {
			throw new IllegalArgumentException(
					"Argument of allModificationSiteContainedIn must has same peptide sequence!");
		}

		for (int i = 0; i < sites.size(); i++) {
			if (this.isModified(i) && !longer.isModified(i)) {
				return false;
			}
		}
		return true;
	}

	public boolean allAmbiguousSiteContainedIn(
			SequenceModificationSitePair shorter) {
		int ipos = this.pureSequence.indexOf(shorter.pureSequence);
		if (ipos == -1) {
			throw new IllegalArgumentException(this.pureSequence
					+ " doesn't include " + shorter.pureSequence);
		}

		for (int i = 0; i < ipos; i++) {
			if (this.isAmbiguousModified(i)) {
				return false;
			}
		}

		int iend = ipos + shorter.sites.size();
		for (int i = ipos; i < iend; i++) {
			if (this.isAmbiguousModified(i) && !shorter.isModified(i - ipos)) {
				return false;
			}
		}

		return true;
	}

	public boolean containAllAmbiguousSite(SequenceModificationSitePair shorter) {
		int ipos = this.pureSequence.indexOf(shorter.pureSequence);
		if (ipos == -1) {
			throw new IllegalArgumentException(this.pureSequence
					+ " doesn't include " + shorter.pureSequence);
		}

		for (int i = 0; i < shorter.sites.size(); i++) {
			if (shorter.isAmbiguousModified(i) && !this.isModified(i + ipos)) {
				return false;
			}
		}

		return true;
	}

	public boolean mergeOverlap(SequenceModificationSitePair another,
			String pureOverlapSequence) {
		SequenceModificationSitePair copy = new SequenceModificationSitePair(
				another);
		int copyPos = copy.pureSequence.indexOf(pureOverlapSequence);
		int curPos = this.pureSequence.indexOf(pureOverlapSequence);

		if (copyPos > curPos) {
			if (copy.mergeOverlap(this, pureOverlapSequence)) {
				init(copy);
				return true;
			}

			return false;
		}

		String overlapSequence = this.pureSequence.substring(curPos - copyPos);
		if (!copy.pureSequence.startsWith(overlapSequence)) {
			return false;
		}

		int startPos = curPos - copyPos;
		for (int i = 0; i < startPos; i++) {
			// if there is modification in the overlap-outside range, merge fail.
			if (!sites.get(i).snd.equals(' ')) {
				return false;
			}
		}
		int endPos = overlapSequence.length();
		for (int i = endPos; i < copy.pureSequence.length(); i++) {
			// if there is modification in the overlap-outside range, merge fail.
			if (!copy.sites.get(i).snd.equals(' ')) {
				return false;
			}
		}

		for (int i = 0; i < endPos; i++) {
			if (copy.isPositiveModified(i)) {
				if (this.isPositiveModified(i + startPos)) {
					if (!this.sites.get(i + startPos).snd.equals(copy.sites.get(i).snd)) {
						this.sites.get(i + startPos).snd = MULTI_STATE_SITE;
					}
					continue;
				}

				if (!this.isModified(i + startPos)) {
					this.modifiedCount++;
				}

				this.sites.get(i + startPos).snd = copy.sites.get(i).snd;
				continue;
			}
		}

		for (int i = endPos; i < copy.pureSequence.length(); i++) {
			this.sites.add(new Pair<Character, Character>(copy.sites.get(i)));
		}

		calculate();

		return true;
	}

	public void mergeTrueModificationSite(SequenceModificationSitePair another) {
		if (pureSequence.length() < another.pureSequence.length()) {
			mergeTrueModificationSiteWithLongerSequence(another);
		} else {
			mergeTrueModificationSiteWithShorterSequence(another);
		}
	}

	public void mergeTrueModificationSiteWithShorterSequence(
			SequenceModificationSitePair shorter) {
		int ipos = this.pureSequence.indexOf(shorter.pureSequence);
		if (ipos == -1) {
			throw new IllegalArgumentException(this.pureSequence
					+ " doesn't include " + shorter.pureSequence);
		}

		for (int i = 0; i < shorter.pureSequence.length(); i++) {
			if (shorter.isPositiveModified(i)) {
				if (this.isPositiveModified(i + ipos)) {
					if (!this.sites.get(i + ipos).snd.equals(shorter.sites.get(i).snd)) {
						this.sites.get(i + ipos).snd = MULTI_STATE_SITE;
					}
					continue;
				}

				if (!this.isModified(i + ipos)) {
					this.modifiedCount++;
				}

				this.sites.get(i + ipos).snd = shorter.sites.get(i).snd;
				continue;
			}
		}
		calculate();
	}

	public void mergeTrueModificationSiteWithLongerSequence(
			SequenceModificationSitePair longer) {
		SequenceModificationSitePair copy = new SequenceModificationSitePair(longer);
		copy.mergeTrueModificationSiteWithShorterSequence(this);
		this.init(copy);
	}

	private void removeAmbiguousModifiedSite() {
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).snd.equals(AMBIGUOUS_SITE)) {
				sites.get(i).snd = ' ';
			}
		}
	}

	private void calculate() {
		trueModifiedCount = 0;
		ambiguousModifiedCount = 0;
		multistateModifiedCount = 0;
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).snd.equals(AMBIGUOUS_SITE)) {
				ambiguousModifiedCount++;
			} else if (!sites.get(i).snd.equals(' ')) {
				trueModifiedCount++;
				if (sites.get(i).snd.equals(MULTI_STATE_SITE)) {
					multistateModifiedCount++;
				}
			}
		}

		StringBuffer sbModifiedSequence = new StringBuffer();
		StringBuffer sbPureSequence = new StringBuffer();
		for (int i = 0; i < sites.size(); i++) {
			sbPureSequence.append(sites.get(i).fst);
			sbModifiedSequence.append(sites.get(i).fst);
			if (sites.get(i).snd != ' ') {
				sbModifiedSequence.append(sites.get(i).snd);
			}
		}
		pureSequence = sbPureSequence.toString();
		modifiedSequence = sbModifiedSequence.toString();

		checkModificationSiteCount();
	}

	@Override
	public String toString() {
		return modifiedSequence;
	}

	public int getModifiedCount() {
		return modifiedCount;
	}

	public int getTrueModifiedCount() {
		return trueModifiedCount;
	}

	public int getAmbiguousModifiedCount() {
		return ambiguousModifiedCount;
	}

	public int getMultistateModifiedCount() {
		return multistateModifiedCount;
	}

	public List<Pair<Character, Character>> getModificationSiteList() {
		List<Pair<Character, Character>> result = new ArrayList<Pair<Character, Character>>();

		for (int i = 0; i < sites.size(); i++) {
			if (this.isModified(i)) {
				result.add(new Pair<Character, Character>(sites.get(i)));
			}
		}
		return result;
	}

	public List<Pair<Character, Character>> getPositiveModificationSiteList() {
		List<Pair<Character, Character>> result = new ArrayList<Pair<Character, Character>>();

		for (int i = 0; i < sites.size(); i++) {
			if (this.isPositiveModified(i)) {
				result.add(new Pair<Character, Character>(sites.get(i)));
			}
		}
		return result;
	}

	public List<Pair<Character, Character>> getAmbiguousModificationSiteList() {
		List<Pair<Character, Character>> result = new ArrayList<Pair<Character, Character>>();

		for (int i = 0; i < sites.size(); i++) {
			if (this.isAmbiguousModified(i)) {
				result.add(new Pair<Character, Character>(sites.get(i)));
			}
		}
		return result;
	}

	public List<Pair<Character, Character>> getMultipleStateModificationSiteList() {
		List<Pair<Character, Character>> result = new ArrayList<Pair<Character, Character>>();

		for (int i = 0; i < sites.size(); i++) {
			if (isMultipleStateModified(i)) {
				result.add(new Pair<Character, Character>(sites.get(i)));
			}
		}
		return result;
	}

	public List<Pair<Character, Character>> getSiteList() {
		return Collections.unmodifiableList(sites);
	}

	public List<Pair<Character, Character>> getSiteListByFilter(
			IFilter<Pair<Character, Character>> filter) {
		List<Pair<Character, Character>> result = new ArrayList<Pair<Character, Character>>();

		for (int i = 0; i < sites.size(); i++) {
			if (filter.accept(sites.get(i))) {
				result.add(new Pair<Character, Character>(sites.get(i)));
			}
		}
		return result;
	}

	public String getModifiedSequence() {
		return modifiedSequence;
	}

	public void setModifiedCount(int modifiedCount) {
		this.modifiedCount = modifiedCount;
	}

	public String getPureSequence() {
		return pureSequence;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(SequenceModificationSitePair object) {
		return new CompareToBuilder().append(this.modifiedSequence,
				object.modifiedSequence).append(this.modifiedCount,
				object.modifiedCount).toComparison();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof SequenceModificationSitePair)) {
			return false;
		}
		SequenceModificationSitePair rhs = (SequenceModificationSitePair) object;
		return new EqualsBuilder().append(this.modifiedSequence,
				rhs.modifiedSequence).append(this.modifiedCount, rhs.modifiedCount)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-517185447, -1644108975).append(
				this.modifiedSequence).append(this.modifiedCount).toHashCode();
	}

}
