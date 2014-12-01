package cn.ac.rcpa.bio.proteomics.mascot;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.AbstractPeakListIterator;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;
import cn.ac.rcpa.bio.sequest.SequestFilename;

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
public class MascotGenericFormatIterator extends AbstractPeakListIterator {
	private BufferedReader reader;

	protected static final String BEGIN_PEAK_LIST_TAG = "BEGIN IONS";

	protected static final String END_PEAK_LIST_TAG = "END IONS";

	protected static final String TITLE_TAG = "TITLE=";

	protected static final String PEPMASS_TAG = "PEPMASS=";

	protected static final String CHARGE_TAG = "CHARGE=";

	private Pattern peakPattern = Pattern.compile("([\\d.]+)\\s+([\\d.]+)");

	private Pattern cmpdPattern = Pattern
			.compile("(.*)Cmpd\\s+(\\d+).+?([\\d.]+)\\s+min");
	
	private Pattern maxQuantPattern = Pattern.compile("RawFile: (.+)\\.raw (?:FinneganScanNumber|Index):\\s(\\d+)");

	private boolean bParseScanFromComment = false;

	private int firstScanFromComment = 0;

	private int lastScanFromComment = 0;

	private Pattern firstScanFromCommentPattern = Pattern.compile("#(\\d+)");

	private Pattern lastScanFromCommentPattern = Pattern.compile("(\\d+)$");

	public MascotGenericFormatIterator(BufferedReader br) {
		super();
		this.reader = br;
	}

	@Override
	protected boolean doReadNextPeakList(PeakList<Peak> result)
			throws Exception {
		result.getPeaks().clear();

		String line;
		boolean startPeakValue = false;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(BEGIN_PEAK_LIST_TAG)) {
				startPeakValue = true;
				continue;
			}

			if (line.startsWith(END_PEAK_LIST_TAG)) {
				while (true) {
					boolean hasAnotherPeakList = false;
					reader.mark(100000);
					while ((line = reader.readLine()) != null) {
						if (line.startsWith(BEGIN_PEAK_LIST_TAG)) {
							hasAnotherPeakList = true;
							break;
						}
					}
					reader.reset();
					return hasAnotherPeakList;
				}
			}

			if (!startPeakValue) {
				if (line.startsWith("###FILENAME:")) {
					result.setExperimental(line.substring(12).trim());
				} else if (line.startsWith("###MSMS:")) {
					bParseScanFromComment = true;
					Matcher firstMatch = firstScanFromCommentPattern
							.matcher(line);
					if (!firstMatch.find()) {
						bParseScanFromComment = false;
					} else {
						firstScanFromComment = Integer.parseInt(firstMatch
								.group(1));
					}

					Matcher lastMatch = lastScanFromCommentPattern
							.matcher(line);
					if (!lastMatch.find()) {
						bParseScanFromComment = false;
					} else {
						lastScanFromComment = Integer.parseInt(lastMatch
								.group(1));
					}
				}

				continue;
			}

			if (line.startsWith(TITLE_TAG)) {
				if (-1 != line.indexOf("Cmpd")) {
					// TITLE= Cmpd 3, +MSn(579.9), 8.3 min
					Matcher matcher = cmpdPattern.matcher(line);
					matcher.find();
					result.setRetentionTime(Double
							.parseDouble(matcher.group(3)));
					if (bParseScanFromComment) {
						result.setFirstScan(firstScanFromComment);
						result.setLastScan(lastScanFromComment);
					} else {
						result.setFirstScan(Integer.parseInt(matcher.group(2)));
						result.setLastScan(result.getFirstScan());
					}
					bParseScanFromComment = false;

					if (result.getExperimental() == null) {
						String experimental = matcher.group(1).substring(TITLE_TAG.length()).trim();
						if (experimental.length() > 0) {
							result.setExperimental(experimental.substring(0,
									experimental.length() - 1).trim());
						}
					}
				} else if (-1 != line.indexOf("RawFile:")){
					//MaxQuant format
					//TITLE=RawFile: LQR_SCS_Nu_CA_SAX_Online_071226_06.raw FinneganScanNumber: 20 _nix_
					Matcher matcher = maxQuantPattern.matcher(line);
					matcher.find();
					result.setExperimental(matcher.group(1));
					result.setFirstScan(Integer.parseInt(matcher.group(2)));
					result.setLastScan(result.getFirstScan());
				}else{
					// TITLE=20030428_4_29L_15.2002.2002.2.
					String filename = line.substring(TITLE_TAG.length()).trim();
					String lower = filename.toLowerCase();
					if(lower.endsWith(".dta")){
					}
					else if(filename.endsWith(".")){
						filename = filename + "dta";
					}
					else {
						filename = filename + ".dta";
					}
					
					try {
						SequestFilename sf = SequestFilename.parse(filename);
						result.setExperimental(sf.getExperiment());
						result.setFirstScan(sf.getFirstScan());
						result.setLastScan(sf.getLastScan());
						result.setCharge(sf.getCharge());
					} catch (Exception ex) {
					}
				}
				continue;
			}

			if (line.startsWith(PEPMASS_TAG)) {
				// parse precursor information
				String[] values = line.substring(PEPMASS_TAG.length()).split(
						"\\s");
				result.setPrecursorMZ(Double.parseDouble(values[0]));
				if (values.length > 1) {
					result.setIntensity(Double.parseDouble(values[1]));
				}
				continue;
			}

			if (line.startsWith(CHARGE_TAG)) {
				// parse precursor information
				String[] values = line.substring(CHARGE_TAG.length()).split(
						"\\s");
				if(values.length > 1){
					result.setCharge(0);
				}
				else{
					result.setCharge(Integer.parseInt(values[0].substring(0,values[0].length()-1 )));
				}
				continue;
			}

			Matcher matcher = peakPattern.matcher(line);
			if (!matcher.find()) {
				continue;
			}

			Peak peak = new Peak(Double.valueOf(matcher.group(1)), Double
					.valueOf(matcher.group(2)));
			result.getPeaks().add(peak);
		}

		return false;
	}

	public String getFormatName() {
		return "MascotGenericFormat";
	}

}
