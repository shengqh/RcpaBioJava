package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;

import cn.ac.rcpa.bio.proteomics.AbstractPeakListIterator;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;
import cn.ac.rcpa.bio.sequest.SequestFilename;

public class DtasIterator extends AbstractPeakListIterator {
	private BufferedReader reader;

	public DtasIterator(BufferedReader br) {
		super();
		this.reader = br;
	}

	private boolean isDtaStart(String line) {
		return line.endsWith(".dta");
	}

	@Override
	protected boolean doReadNextPeakList(PeakList<Peak> peakList)
			throws Exception {
		peakList.getPeaks().clear();

		String line;
		boolean startPeakValue = false;
		boolean hasPeak = false;
		boolean result = false;
		while ((line = reader.readLine()) != null) {
			if (startPeakValue) {
				if (line.trim().length() == 0) {
					if (hasPeak) {
						startPeakValue = false;
						reader.mark(100000);
						while ((line = reader.readLine()) != null) {
							if (isDtaStart(line)) {
								result = true;
								break;
							}
						}
						reader.reset();
						break;
					}
				} else {
					String[] values = line.split("\\s");
					Peak peak = new Peak(Double.valueOf(values[0]), Double
							.valueOf(values[1]));
					peakList.getPeaks().add(peak);
					hasPeak = true;
				}
			} else if (isDtaStart(line)) {
				SequestFilename sfn = SequestFilename.parse(line);
				peakList.setExperimental(sfn.getExperiment());
				peakList.setCharge(sfn.getCharge());
				peakList.setFirstScan(sfn.getFirstScan());
				peakList.setLastScan(sfn.getLastScan());
				line = reader.readLine();
				String[] values = line.split("\\s");
				peakList.setPrecursorAndCharge(Double.parseDouble(values[0]), Integer
						.parseInt(values[1]));
				startPeakValue = true;
			}
		}

		return result;
	}

	public String getFormatName() {
		return "Merged Dta Format (dtas)";
	}

}
