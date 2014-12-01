package cn.ac.rcpa.filter.impl;

import java.util.regex.Pattern;

import cn.ac.rcpa.filter.IFilter;

public class StringPatternFilter implements IFilter<String> {
	private Pattern pattern;

	public StringPatternFilter(String patternStr) {
		pattern = Pattern.compile(patternStr);
	}

	public boolean accept(String e) {
		return pattern.matcher(e).find();
	}

	public String getType() {
		return "PatternFilter=" + pattern.pattern();
	}

}
