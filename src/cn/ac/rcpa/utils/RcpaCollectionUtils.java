package cn.ac.rcpa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RcpaCollectionUtils {
	private RcpaCollectionUtils() {
	}

	public static <T extends Comparable<? super T>> List<T> getSortedKeySet(
			Map<T, ?> map) {
		ArrayList<T> result = new ArrayList<T>(map.keySet());
		Collections.sort(result);
		return result;
	}

	public static <T> int getItemIndex(T value, T[] values) {
		int result = 0;
		for (int i = 0; i < values.length; i++) {
			if (value.equals(values[i])) {
				result = i;
				break;
			}
		}
		return result;
	}

	public static double[] toDoubleArray(List<Double> list) {
		double[] result = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}
}
