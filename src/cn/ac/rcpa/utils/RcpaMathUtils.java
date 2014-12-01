package cn.ac.rcpa.utils;

import java.util.List;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;

/**
 * @author Sheng Quanhu (shengqh@gmail.com)
 */
public class RcpaMathUtils {

	static public final double exp10(double x) {
		return Math.exp(x * Math.log(10));
	}

	public static double[] toDoubleArray(List<Double> list) {
		double[] result = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public static DoubleArrayList getDoubleArrayList(
			List<? extends Number> orders16) {
		DoubleArrayList o16 = new DoubleArrayList();
		for (Number i : orders16) {
			o16.add(i.doubleValue());
		}
		return o16;
	}

	static public final double studentT(List<? extends Number> orders16,
			List<? extends Number> orders14) {
		final DoubleArrayList o16 = getDoubleArrayList(orders16);
		final DoubleArrayList o14 = getDoubleArrayList(orders14);

		return studentT(o16, o14);
	}

	public static double studentT(final DoubleArrayList o16,
			final DoubleArrayList o14) {
		final int n1 = o16.size();
		final int n2 = o14.size();

		final double mean16 = Descriptive.mean(o16);
		final double mean14 = Descriptive.mean(o14);
		final double sv16 = Descriptive.sampleVariance(o16, mean16);
		final double sv14 = Descriptive.sampleVariance(o14, mean14);

		return studentT(new NormalDistribution(mean16, sv16, n1),
				new NormalDistribution(mean14, sv14, n2));
	}

	public static final double studentT(NormalDistribution one,
			NormalDistribution two) {
		final double sdg = Math.sqrt(one.getSampleVariance() / one.getSampleCount()
				+ two.getSampleVariance() / two.getSampleCount());
		final double t = (one.getMean() - two.getMean()) / sdg;

		return t;
	}

	public static final double oneTailsProbabilityStudentT(double t, int freedem) {
		return Probability.studentT(freedem, t);
	}

	public static final double twoTailsProbabilityStudentT(
			List<Integer> orders16, List<Integer> orders14) {
		final double t = studentT(orders16, orders14);
		final int freedem = orders16.size() + orders14.size() - 2;
		double p1 = Probability.studentT(freedem, t);
		double p2 = Probability.studentT(freedem, -t);

		return Math.min(p1, p2) * 2;
	}
}
