package cn.ac.rcpa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPatternParser implements IParser<String> {
	private String title;

	private String description;

	private Pattern fractionPattern;

	public StringPatternParser(String title, String description, String patternStr) {
		this.title = title;
		this.description = description;
		this.fractionPattern = Pattern.compile(patternStr);
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getValue(String obj) {
		Matcher matcher = fractionPattern.matcher(obj);
		if (!matcher.find()) {
			throw new IllegalArgumentException("Cannot get fraction info from " + obj
					+ " based on pattern " + fractionPattern.pattern());
		}

		return matcher.group(1);
	}
}
