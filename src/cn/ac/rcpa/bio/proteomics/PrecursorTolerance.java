package cn.ac.rcpa.bio.proteomics;

public class PrecursorTolerance {
	public static double ppm2mz(double precursorMz, double ppmTolerance) {
		return precursorMz * ppmTolerance / 1000000;
	}

	public static double mz2ppm(double precursorMz, double mzTolerance) {
		return mzTolerance * 1000000 / precursorMz;
	}
}
