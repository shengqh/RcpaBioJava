package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;

import cn.ac.rcpa.bio.proteomics.IPeakListWriter;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;

/**
 * <p>
 * Title:
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
 * @version 1.0.0
 */
public class DTAWriter implements IPeakListWriter<Peak> {
	private static DecimalFormat df = new DecimalFormat("0.####");

	public DTAWriter() {
	}

	public void write(PrintStream ps, PeakList<? extends Peak> peaklist)
			throws IOException {
		ps.println(df.format(peaklist.getPrecursor()) + " " + peaklist.getCharge());
		for (Peak peak : peaklist.getPeaks()) {
			ps
					.println(df.format(peak.getMz()) + " "
							+ df.format(peak.getIntensity()));
		}
		ps.println();
	}

	public String getFormatName() {
		return "Sequest Dta Format";
	}
}
