package cn.ac.rcpa.chem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AtomCompositionUtils {
	private AtomCompositionUtils() {
	}

	public static String mapToString(Map<Character, Integer> acs) {
		StringBuilder sb = new StringBuilder();
		List<Character> keys = new ArrayList<Character>(acs.keySet());
		Collections.sort(keys);
		for (Character c : keys) {
			sb.append(c);
			int count = acs.get(c);
			if (count > 1) {
				sb.append(count);
			}
		}
		return sb.toString();
	}
}
