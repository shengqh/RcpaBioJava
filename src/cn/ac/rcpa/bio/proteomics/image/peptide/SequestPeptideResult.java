/*
 * Created on 2005-11-1
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.sequest.SequestOutFileParser;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.bio.sequest.SequestFilename;
import cn.ac.rcpa.bio.sequest.SequestParseException;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class SequestPeptideResult extends AbstractPeptideResult {
	public SequestPeptideResult(String peptide, IonType[] ionTypes) {
		super(peptide, ionTypes);
	}

	public SequestPeptideResult(String peptide) {
		super(peptide);
	}

	public SequestPeptideResult() {
		super("");
	}

	public static Pattern peakPattern = Pattern
			.compile("(\\d+\\.{0,1}\\d*)\\s+(\\d+\\.{0,1}\\d*)");

	public static Pattern massTypePattern = Pattern
			.compile("fragment\\stol.+?(\\S+)/(\\S+)");

	public static Pattern dynamicModificationPattern = Pattern
			.compile("\\(\\S+(\\S)\\s([\\+|-]\\d+\\.{0,1}\\d+)\\)");

	public static Pattern staticModificationPattern = Pattern
			.compile("([A-Z])=(\\d+\\.{0,1}\\d+)");

	public void parseFromFile(String dtaFilename, String outFilename)
			throws IOException, SequestParseException {
		parseDtaFile(dtaFilename);
		parseOutFile(outFilename);
	}

	public void parseDtaFile(String dtaFilename) throws IOException,
			SequestParseException {
		String[] dtaFiles = RcpaFileUtils.readFile(dtaFilename);
		parseDtaFile(dtaFilename, dtaFiles);
	}

	public void parseDtaFile(String dtaFilename, String[] lines)
			throws SequestParseException {
		peaks.getPeaks().clear();

		Matcher matcher = peakPattern.matcher(lines[0]);
		if (!matcher.find()) {
			throw new IllegalArgumentException(
					"Error, parameter is not valid dta file :" + lines[0]);
		}

		peaks.setPrecursorAndCharge(Double.parseDouble(matcher.group(1)),
				Integer.parseInt(matcher.group(2)));

		for (int i = 1; i < lines.length; i++) {
			if (lines[i].trim().length() == 0) {
				break;
			}

			Matcher peakMatcher = peakPattern.matcher(lines[i]);
			if (!peakMatcher.find()) {
				throw new IllegalArgumentException(
						"Error, parameter is not valid dta file :" + lines[i]);
			}

			peaks.getPeaks().add(
					new MatchedPeak(Double.parseDouble(peakMatcher.group(1)),
							Double.parseDouble(peakMatcher.group(2)), 0));
		}

		SequestFilename sf = SequestFilename.parse(dtaFilename);
		peaks.setFirstScan(sf.getFirstScan());
		peaks.setLastScan(sf.getLastScan());
		peaks.setExperimental(sf.getExperiment());
	}

	public void parseOutFile(String outFilename) throws IOException,
			SequestParseException {
		String[] outFiles = RcpaFileUtils.readFile(outFilename);
		parseOutFile(outFiles, outFilename);
	}

	public void parseOutFile(String[] lines, String outFilename)
			throws SequenceValidateException {
		int index = 0;
		while (index < lines.length) {
			if (lines[index].trim().length() != 0) {
				break;
			}
			index++;
		}

		DecimalFormat df = new DecimalFormat("0.####");

		index = parseMassType(lines, index);
		index = parseModification(lines, index);

		if (getPeptide() == null || getPeptide().length() == 0
				|| -1 != getPeptide().indexOf('p')) {

			BuildSummaryPeptide first = null;
			String firsrSequence = null;
			for (int i = 0; i < lines.length; i++) {
				BuildSummaryPeptide current = SequestOutFileParser
						.parsePeptide(lines[i]);
				if (current == null) {
					continue;
				}

				if (first == null) {
					first = current;
					first.setDeltacn(1.0);
					firsrSequence = PeptideUtils.getPurePeptideSequence(first.getSequence());
					continue;
				}

				if (1 == current.getRank()) {
					continue;
				}

				if(PeptideUtils.getPurePeptideSequence(current.getSequence()).equals(firsrSequence)){
					continue;
				}
				
				first.setDeltacn(current.getDeltacn());
				break;
			}

			if (first != null) {
				setPeptide(first.getSequence());
				getScoreMap().put("Xcorr", df.format(first.getXcorr()));
				getScoreMap().put("DeltaCn", df.format(first.getDeltacn()));
			}
		}

		if (getPeptide() != null && getPeptide().length() != 0) {
			initTheoreticalPeaks(precursorMonoMass, peakMonoMass,
					staticModification, dynamicModification);
		}
	}

	private int parseMassType(String[] lines, int index) {
		while (index < lines.length) {
			Matcher matcher = massTypePattern.matcher(lines[index]);
			if (matcher.find()) {
				precursorMonoMass = !matcher.group(1).equals("AVG");
				peakMonoMass = !matcher.group(2).equals("AVG");
				return index + 1;
			}
			index++;
		}

		throw new IllegalArgumentException("Cannot find mass type information");
	}

	public void parseModification(String line) {
		dynamicModification.clear();
		Matcher dMatcher = dynamicModificationPattern.matcher(line);
		while (dMatcher.find()) {
			dynamicModification.put(dMatcher.group(1).charAt(0), Double
					.parseDouble(dMatcher.group(2)));
		}

		staticModification.clear();
		Matcher sMatcher = staticModificationPattern.matcher(line);
		while (sMatcher.find()) {
			staticModification.put(sMatcher.group(1).charAt(0), Double
					.parseDouble(sMatcher.group(2)));
		}
	}

	private int parseModification(String[] lines, int index) {
		dynamicModification.clear();
		staticModification.clear();

		while (index < lines.length) {
			if (lines[index].indexOf("Enzyme:") != -1) {
				parseModification(lines[index]);
				return index + 1;
			}
			index++;
		}

		throw new IllegalArgumentException(
				"Cannot find modification information");
	}
}
