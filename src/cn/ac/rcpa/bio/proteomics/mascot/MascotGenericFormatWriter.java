package cn.ac.rcpa.bio.proteomics.mascot;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;

import cn.ac.rcpa.bio.proteomics.IPeakListWriter;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.PeakList;

public class MascotGenericFormatWriter implements IPeakListWriter<Peak> {
	private static DecimalFormat df = new DecimalFormat("0.####");

	public MascotGenericFormatWriter() {
		super();
	}

	public void write(PrintStream ps, PeakList<? extends Peak> peaks)
			throws IOException {
		ps.println("BEGIN IONS");
		String title = peaks.getExperimental() + "." + peaks.getFirstScan() + "."
				+ peaks.getLastScan() + "." + peaks.getCharge() + ".dta";
		ps.println("TITLE=" + title);
		ps.println("PEPMASS="
				+ df.format(peaks.getPrecursor()));
		ps.println("CHARGE=" + peaks.getCharge() + "+");

		for (Peak peak : peaks.getPeaks()) {
			ps
					.println(df.format(peak.getMz()) + " "
							+ df.format(peak.getIntensity()));
		}

		ps.println("END IONS");
		ps.println();
	}

	public String getFormatName() {
		return "MascotGenericFormat";
	}
}
