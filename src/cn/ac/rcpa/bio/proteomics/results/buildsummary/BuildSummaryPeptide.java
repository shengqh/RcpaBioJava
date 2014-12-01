package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IPeakListInfo;
import cn.ac.rcpa.bio.proteomics.comparison.IdentifiedPeptideComparator;
import cn.ac.rcpa.bio.sequest.SequestFilename;
import cn.ac.rcpa.bio.sequest.SequestParseException;
import cn.ac.rcpa.chem.Atom;
import cn.ac.rcpa.utils.RegexValueType;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class BuildSummaryPeptide extends AbstractIdentifiedPeptide {
	private SequestFilename filename = new SequestFilename();

	private int rank;

	private int spRank;

	private double deltacn;

	private double xcorr;

	private double sp;

	private int matchCount;

	private int theoreticalCount;

	private int duplicateRefCount;

	// " 1. 1 / 19 15861 1786.8390 0.0000 0.8720 114.7 10/64
	// gi|5541865|emb|CAB51072.1| +3 -.EARGNSSETSHSVPEAK.-";
	// " 2. 2 /101 169491 971.2229 0.0061 1.2752 128.9 10/14
	// gi|21754832|dbj|BAC04571.1| K.KNLLLITR.I"

	private static String GET_RANK = RegexValueType.GET_INT;

	private static String GET_SP_RANK = RegexValueType.GET_INT;

	private static String GET_SINGLE_CHARGE_MASS = RegexValueType.GET_DOUBLE;

	private static String GET_DELTACN = RegexValueType.GET_DOUBLE;

	private static String GET_XCORR = RegexValueType.GET_DOUBLE;

	private static String GET_SP = RegexValueType.GET_DOUBLE;

	private static String GET_MATCH_COUNT = RegexValueType.GET_INT;

	private static String GET_THEORETICAL_COUNT = RegexValueType.GET_INT;

	private static String GET_PROTEIN = RegexValueType.GET_UNBLANK;

	private static String GET_DUPLICATE_REF_COUNT = "\\s*\\+?"
			+ RegexValueType.GET_INT + "?";

	private static String GET_SEQUENCE = RegexValueType.GET_UNBLANK;

	private static String PATTERN_SEQUENCE = RegexValueType.ANY + GET_RANK
			+ RegexValueType.BLANK + "/" + GET_SP_RANK + RegexValueType.BLANK
			+ RegexValueType.INT + GET_SINGLE_CHARGE_MASS + GET_DELTACN + GET_XCORR
			+ GET_SP + GET_MATCH_COUNT + RegexValueType.BLANK + "/"
			+ GET_THEORETICAL_COUNT + GET_PROTEIN + GET_DUPLICATE_REF_COUNT
			+ GET_SEQUENCE;

	private static Pattern outLinePattern = null;

	public BuildSummaryPeptide() {
		super();
	}

	public double getDeltacn() {
		return deltacn;
	}

	public void setDeltacn(double deltacn) {
		this.deltacn = deltacn;
	}

	public int getDuplicateRefCount() {
		return duplicateRefCount;
	}

	public void setDuplicateRefCount(int duplicateRefCount) {
		this.duplicateRefCount = duplicateRefCount;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public double getSp() {
		return sp;
	}

	public void setSp(double sp) {
		this.sp = sp;
	}

	public int getSpRank() {
		return spRank;
	}

	public void setSpRank(int spRank) {
		this.spRank = spRank;
	}

	public int getTheoreticalCount() {
		return theoreticalCount;
	}

	public void setTheoreticalCount(int theoreticalCount) {
		this.theoreticalCount = theoreticalCount;
	}

	public double getXcorr() {
		return xcorr;
	}

	public void setXcorr(double xcorr) {
		this.xcorr = xcorr;
	}

	public void setFilename(SequestFilename filename) {
		if (filename == null) {
			this.filename = new SequestFilename();
		} else {
			this.filename.assign(filename);
		}
	}

	public void setFilename(String filename) throws SequestParseException {
		this.filename = SequestFilename.parseShortFilename(filename);
	}

	private static Pattern getOutLinePattern() {
		if (outLinePattern == null) {
			outLinePattern = Pattern.compile(PATTERN_SEQUENCE);
		}
		return outLinePattern;
	}

	public int getCharge() {
		return filename.getCharge();
	}

	public void setCharge(int charge) {
		filename.setCharge(charge);
	}

	public static boolean isPeptideHit(String outFileLine) {
		return getOutLinePattern().matcher(outFileLine).find();
	}

	public static BuildSummaryPeptide parse(String outFileLine) {
		Matcher matcher = getOutLinePattern().matcher(outFileLine);
		// System.out.println(PATTERN_SEQUENCE);
		if (matcher.find()) {
			BuildSummaryPeptide result = new BuildSummaryPeptide();
			result.rank = Integer.parseInt(matcher.group(1));
			result.spRank = Integer.parseInt(matcher.group(2));
			result.setTheoreticalSingleChargeMass(Double
					.parseDouble(matcher.group(3)));
			result.deltacn = Double.parseDouble(matcher.group(4));
			result.xcorr = Double.parseDouble(matcher.group(5));
			result.sp = Double.parseDouble(matcher.group(6));
			result.matchCount = Integer.parseInt(matcher.group(7));
			result.theoreticalCount = Integer.parseInt(matcher.group(8));
			result.clearProteinNames();
			result.addProteinName(matcher.group(9));
			if (matcher.group(10) == null || matcher.group(10).length() == 0) {
				result.duplicateRefCount = 0;
			} else {
				result.duplicateRefCount = Integer.parseInt(matcher.group(10));
			}
			result.setSequence(matcher.group(11));
			return result;
		}

		return null;
	}

	public boolean isIdentificationEquals(BuildSummaryPeptide pephit) {
		if (pephit == null) {
			throw new IllegalArgumentException(
					"Parameter pephit of SequestPeptideHit.isIdentificationEquals cannot be null");
		}

		return filename.equals(pephit.filename) && rank == pephit.rank;
	}

	@Override
	public String toString() {
		return "BuildSummaryPeptide-" + filename + "-" + getSequence();
	}

	public static void sort(BuildSummaryPeptide[] pephits) {
		List<BuildSummaryPeptide> oldhits = Arrays.asList(pephits);
		Collections.sort(oldhits,
				new IdentifiedPeptideComparator<BuildSummaryPeptide>());
		Set<String> filenames = new HashSet<String>();
		List<BuildSummaryPeptide> hits = new ArrayList<BuildSummaryPeptide>();

		for (int i = 0; i < pephits.length; i++) {
			if (!filenames.contains(pephits[i].getPeakListInfo().toString())) {
				filenames.add(pephits[i].getPeakListInfo().toString());
				hits.add(pephits[i]);
				for (int j = i + 1; j < pephits.length; j++) {
					if (pephits[j].getPeakListInfo().toString().equals(
							pephits[i].getPeakListInfo().toString())) {
						hits.add(pephits[j]);
					}
				}
			}
		}

		for (int i = 0; i < pephits.length; i++) {
			pephits[i] = hits.get(i);
		}
	}

	/**
	 * getPeakListInfo
	 * 
	 * @return IPeakListInfo
	 */
	public IPeakListInfo getPeakListInfo() {
		return filename;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1589755657, 326352287).append(this.filename)
				.append(this.getSequence()).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BuildSummaryPeptide)) {
			return false;
		}
		BuildSummaryPeptide rhs = (BuildSummaryPeptide) object;
		return new EqualsBuilder().append(this.sp, rhs.sp).append(this.filename,
				rhs.filename).append(this.getSequence(), rhs.getSequence()).isEquals();
	}

	public double getObservedMz() {
		return (getExperimentalSingleChargeMass() + Atom.H.getMono_isotopic()
				.getMass()
				* (getCharge() - 1))
				/ getCharge();
	}

	public void setObservedMz(double observedMz) {
		if (0 == getCharge()) {
			throw new IllegalStateException(
					"Assigning charge before assign observed MZ.");
		}
		setExperimentalSingleChargeMass(observedMz * getCharge()
				- Atom.H.getMono_isotopic().getMass() * (getCharge() - 1));
	}
}
