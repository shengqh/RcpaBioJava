package cn.ac.rcpa.bio.sequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IPeakListInfo;
import cn.ac.rcpa.bio.proteomics.comparison.PeakListInfoComparator;
import cn.ac.rcpa.utils.RegexValueType;

/**
 * <p>
 * Title: DtaFilename
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: cn.ac.rcpa
 * </p>
 * <p>
 * Create: 2003-12-3
 * </p>
 * 
 * @author Sheng Quanhu(shengqh@gmail.com/shengqh@263.net)
 * @version 1.0
 */
public class SequestFilename implements IPeakListInfo,
		Comparable<SequestFilename> {
	private String experiment = "";
	private int firstScan;
	private int lastScan;
	private int charge;
	private String extension = "";

	// "20030905_ICAT_E6_V1_02.4795.4795.2.out"
	private static String GET_EXPERIMENTAL = RegexValueType.GET_UNBLANK;
	private static String GET_FIRST_SCAN = "\\." + RegexValueType.GET_INT;
	private static String GET_LAST_SCAN = "\\." + RegexValueType.GET_INT;
	private static String GET_CHARGE = "\\." + RegexValueType.GET_INT;
	private static String GET_EXTENSION = "\\." + RegexValueType.GET_UNBLANK;

	private static String FILEINFO_PATTERN = GET_EXPERIMENTAL + GET_FIRST_SCAN
			+ GET_LAST_SCAN + GET_CHARGE + GET_EXTENSION + RegexValueType.ANY;
	private static Pattern fileinfoPattern = null;

	private static Pattern getFileInfoPattern() {
		if (fileinfoPattern == null) {
			// System.out.println(FILEINFO_PATTERN);
			fileinfoPattern = Pattern.compile(FILEINFO_PATTERN);
		}
		return fileinfoPattern;
	}

	public SequestFilename() {
	}

	public SequestFilename(String experimental, int firstScan, int lastScan,
			int charge, String extension) {
		this.experiment = experimental;
		this.firstScan = firstScan;
		this.lastScan = lastScan;
		this.charge = charge;
		this.extension = extension;
	}

	public int getFirstScan() {
		return firstScan;
	}

	public void setFirstScan(int firstScan) {
		this.firstScan = firstScan;
	}

	public int getLastScan() {
		return lastScan;
	}

	public void setLastScan(int lastScan) {
		this.lastScan = lastScan;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public String getExtension() {
		return extension;
	}

	public String getExperiment() {
		return experiment;
	}

	public void setExtension(String Extension) {
		this.extension = Extension;
	}

	public void setExperiment(String experiment) {
		this.experiment = experiment;
	}

	public String getLongFilename() {
		return doGetFilename();
	}

	public String getShortFilename() {
		StringBuffer sb = new StringBuffer();

		sb.append(experiment);
		sb.append(",");
		sb.append(firstScan);
		if (firstScan != lastScan) {
			sb.append(" - " + lastScan);
		}

		return sb.toString();
	}

	public static SequestFilename parse(String Filename)
			throws SequestParseException {
		SequestFilename result = new SequestFilename();
		// System.out.println(FILEINFO_PATTERN);
		Matcher matcher = getFileInfoPattern().matcher(Filename);
		if (matcher.find()) {
			result.experiment = matcher.group(1);
			result.firstScan = Integer.parseInt(matcher.group(2));
			result.lastScan = Integer.parseInt(matcher.group(3));
			result.charge = Integer.parseInt(matcher.group(4));
			result.extension = matcher.group(5);
			return result;
		}

		throw new SequestParseException(Filename
				+ " is not a valid Sequest dta/out file name!");
	}

	public static SequestFilename parseShortFilename(String Filename)
			throws SequestParseException {
		try {
			if (Filename.length() > 2 && Filename.charAt(0) == '"'
					&& Filename.charAt(Filename.length() - 1) == '"') {
				Filename = Filename.substring(1, Filename.length() - 1);
			}

			SequestFilename result = new SequestFilename();
			int ipos = Filename.lastIndexOf(',');
			result.experiment = Filename.substring(0, ipos);
			if (result.experiment.toLowerCase().endsWith(".raw")) {
				result.experiment = result.experiment.substring(0,
						result.experiment.length() - 4);
			}

			final String scans = Filename
					.substring(ipos + 1, Filename.length());
			String[] scan = scans.split("-");
			result.firstScan = Integer.parseInt(scan[0].trim());
			if (scan.length == 2) {
				result.lastScan = Integer.parseInt(scan[1].trim());
			} else {
				result.lastScan = result.firstScan;
			}
			result.extension = "";
			return result;
		} catch (Exception ex) {
			throw new SequestParseException(Filename
					+ " is not a valid Sequest Short Filename!");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SequestFilename)) {
			return false;
		}
		return compareTo((SequestFilename) o) == 0;
	}

	@Override
	public String toString() {
		return doGetFilename();
	}

	private String doGetFilename() {
		StringBuffer buf = new StringBuffer(experiment).append('.').append(
				firstScan).append('.').append(lastScan).append('.').append(
				charge).append('.').append(extension);
		return buf.toString();
	}

	public int compareTo(SequestFilename o) {
		int result = PeakListInfoComparator.getInstance().compare(this, o);

		if (result == 0) {
			final SequestFilename sfn = (SequestFilename) o;
			result = extension == null ? 0 : extension.compareTo(sfn.extension);
		}

		return result;
	}

	public void assign(SequestFilename source) {
		this.experiment = source.experiment;
		this.firstScan = source.firstScan;
		this.lastScan = source.lastScan;
		this.charge = source.charge;
		this.extension = source.extension;
	}
}
