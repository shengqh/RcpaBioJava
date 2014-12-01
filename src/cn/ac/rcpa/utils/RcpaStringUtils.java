package cn.ac.rcpa.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description: Common String Utils
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
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */
public class RcpaStringUtils {

	/**
	 * Warp string to limited length each line
	 * 
	 * @param line
	 *            String
	 * @param width
	 *            int
	 * @return String
	 */
	static public final String warpString(String line, int width) {
		int ibegin = 0;
		StringBuffer result = new StringBuffer();
		while (true) {
			int iend = ibegin + width;
			if (iend >= line.length()) {
				iend = line.length();
				result.append(line.substring(ibegin, iend));
				break;
			}
			result.append(line.substring(ibegin, iend)).append("\n");
			ibegin = iend;
		}
		return result.toString();
	}

	public static boolean charEquals(String str1, String str2) {
		Set<Character> set1 = new HashSet<Character>();
		for (int i = 0; i < str1.length(); i++) {
			set1.add(str1.charAt(i));
		}
		Set<Character> set2 = new HashSet<Character>();
		for (int i = 0; i < str2.length(); i++) {
			set2.add(str2.charAt(i));
		}
		return set1.equals(set2);
	}

	public static String getXMLCompartibleName(String oldname) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < oldname.length(); i++) {
			if (Character.isLetterOrDigit(oldname.charAt(i))) {
				sb.append(oldname.charAt(i));
			} else {
				sb.append('_');
			}
		}
		return sb.toString();
	}

	public static String intToString(int value, int width) {
		return StringUtils.leftPad(Integer.toString(value), width, '0');
	}

	public static String getRepeatChar(char c, int repeatTimes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < repeatTimes; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

}
