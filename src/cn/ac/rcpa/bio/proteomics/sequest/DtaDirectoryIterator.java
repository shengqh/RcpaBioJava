package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.AbstractPeakListIterator;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;
import cn.ac.rcpa.bio.sequest.SequestFilename;
import cn.ac.rcpa.utils.RegexValueType;

public class DtaDirectoryIterator extends AbstractPeakListIterator {
	private Pattern precursorPattern = Pattern.compile(RegexValueType.GET_DOUBLE
			+ RegexValueType.GET_INT);

	private Pattern peakPattern = Pattern.compile(RegexValueType.GET_DOUBLE
			+ RegexValueType.GET_DOUBLE);

	private File[] dtaFiles;

	private int currentIndex;

	public DtaDirectoryIterator(String dir) {
		dtaFiles = new File(dir).listFiles(new FilenameFilter() {

			public boolean accept(File arg0, String arg1) {
				return arg1.toLowerCase().endsWith(".dta");
			}
		});

		morePeakListAvailable = dtaFiles.length > 0;
	}

	@Override
	protected boolean doReadNextPeakList(PeakList<Peak> result) throws Exception {
		if (currentIndex >= dtaFiles.length) {
			throw new IllegalArgumentException("CurrentIndex out of range "
					+ dtaFiles.length + " : " + currentIndex + "; call hasNext() first!");
		}

		File currentFile = dtaFiles[currentIndex];

		result.getPeaks().clear();

		SequestFilename sf = SequestFilename.parse(currentFile.getName());
		result.setExperimental(sf.getExperiment());
		result.setFirstScan(sf.getFirstScan());
		result.setLastScan(sf.getLastScan());

		String line;

		BufferedReader reader = new BufferedReader(new FileReader(currentFile));

		// find first unempty line
		// it should be precursor line
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() != 0) {
				Matcher precursor = precursorPattern.matcher(line);
				if (precursor.find()) {
					result.setPrecursorAndCharge(Double.parseDouble(precursor.group(1)),
							Integer.parseInt(precursor.group(2)));
					break;
				}
			}
		}

		if (line == null) {
			throw new Exception("There is no peaklist entry.");
		}

		while ((line = reader.readLine()) != null) {
			Matcher peak = peakPattern.matcher(line);
			if (!peak.find()) {
				break;
			}

			Peak apeak = new Peak();
			apeak.setMz(Double.parseDouble(peak.group(1)));
			apeak.setIntensity(Double.parseDouble(peak.group(2)));
			result.getPeaks().add(apeak);
		}

		currentIndex++;
		return currentIndex < dtaFiles.length;
	}

	public String getFormatName() {
		return "dta";
	}

}
