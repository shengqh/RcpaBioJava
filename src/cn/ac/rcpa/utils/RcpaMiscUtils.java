package cn.ac.rcpa.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class RcpaMiscUtils {
	
	public static List<String> matcherToList(Matcher matcher) {
		List<String> result = new ArrayList<String>();
		for (int i = 1; i <= matcher.groupCount(); i++) {
			result.add(matcher.group(i));
		}
		return result;
	}
}
