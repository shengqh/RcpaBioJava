package cn.ac.rcpa.utils;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class RcpaMathUtilsTest extends TestCase {

	/*
	 * Test method for 'cn.ac.rcpa.utils.RcpaMathUtils.studentT(List<Integer>,
	 * List<Integer>)'
	 */
	public void testStudentT() {
		List<Integer> values1 = Arrays.asList(new Integer[] { 520, 460, 500, 470 });
		List<Integer> values2 = Arrays.asList(new Integer[] { 230, 270, 250, 280 });

		double t = RcpaMathUtils.studentT(values1, values2);
		assertEquals(13.0, t, 0.1);
	}

	public void testOneTailsProbabilityStudentT() {
		NormalDistribution dex16 = new NormalDistribution(1.51, 1.23, 276);
		DecimalFormat df2 = new DecimalFormat("0.00");
		for (double mean = 0.0; mean < 3.0; mean += 0.1) {
			NormalDistribution value16 = new NormalDistribution(mean, 1.23, 24);
			double t16 = RcpaMathUtils.studentT(value16, dex16);
			double p16 = RcpaMathUtils.oneTailsProbabilityStudentT(t16, value16
					.getSampleCount()
					+ dex16.getSampleCount() - 2);
			System.out.println(df2.format(mean) + "\t" + df2.format(p16));
		}
	}
}
