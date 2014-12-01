package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.RcpaMiscUtils;

public class MascotResultHtmlStandaloneParser extends
		AbstractMascotResultHtmlParser {
	public MascotResultHtmlStandaloneParser(boolean filterByDefaultScoreAndPvalue) {
		super(filterByDefaultScoreAndPvalue);
	}

	public MascotResultHtmlStandaloneParser(
			IFilter<MascotPeptide> defaultPeptideFilter,
			boolean filterByDefaultScoreAndPvalue) {
		super(defaultPeptideFilter, filterByDefaultScoreAndPvalue);
	}

	private static Pattern proteinPattern;

	@Override
	protected Pattern getProteinPattern() {
		if (null == proteinPattern) {
			proteinPattern = Pattern.compile(
					"(<a\\s+name=(?:\"){0,1}Hit.+?<table.+?Query.+?</table>)",
					Pattern.CASE_INSENSITIVE);
		}
		return proteinPattern;
	}

	private static Pattern proteinInfoPattern = Pattern.compile(
			"<a\\s+href.+?>(.+?)</a>.+?Mass:.+?(\\d+).+?matched.+?<tt>(.*?)</tt>",
			Pattern.CASE_INSENSITIVE);

	private static Pattern proteinNamePattern = Pattern.compile(
			"<a\\s+href.+?>(.+?)</a>", Pattern.CASE_INSENSITIVE);

	private static Pattern peptideInfoPattern = Pattern
			.compile(
					"<tr>.+?value=\"([\\d.]+)\\sfrom\\(([\\d.]+),(\\d+)\\+\\).+?title\\((.+?)\\)\\s+query\\((\\d+)\\).+?<b>\\s*([\\d.]+).+?</b>.+?<b>\\s*([\\d.]+).+?</b>.+?<b>\\s*([\\d.]+).+?</b>.+?<b>\\s*([-\\d.]+).+?</b>.+?<b>\\s*([\\d.]+).+?</b>.+?<b>[\\s(]*([\\d.]+).+?</b>.+?<b>\\s*([\\d.e+-]+).+?</b>.+?<b>\\s*([\\d]+).+?</b>.+<b>\\s*(.+?)</b></font>",
					Pattern.CASE_INSENSITIVE);

	private static Pattern tablePattern = Pattern.compile(
			"([(?:<table)|^].+?</table>)", Pattern.CASE_INSENSITIVE);

	private static Pattern trPattern = Pattern.compile("(<tr.+?</tr>)",
			Pattern.CASE_INSENSITIVE);

	@Override
	protected List<String> getProteinInfo(String proteinContent) {
		Matcher tableMatcher = tablePattern.matcher(proteinContent);
		if (!tableMatcher.find()) {
			throw new IllegalArgumentException(
					"Cannot find protein information table in : " + proteinContent);
		}

		List<String> result = new ArrayList<String>();
		Matcher proteinInfoMatcher = proteinInfoPattern.matcher(tableMatcher
				.group(1));
		if (proteinInfoMatcher.find()) {
			result.add(proteinInfoMatcher.group(1));
			result.add(proteinInfoMatcher.group(2));
			result.add(proteinInfoMatcher.group(3).trim());
		} else {
			Matcher proteinNameMatcher = proteinNamePattern.matcher(tableMatcher
					.group(1));
			if (!proteinNameMatcher.find()) {
				throw new IllegalArgumentException("Cannot find protein name in : "
						+ tableMatcher.group(1));
			}
			result.add(proteinNameMatcher.group(1));
			result.add("0");
			result.add("");
		}

		return result;
	}

	@Override
	protected List<String> getPeptideInfoContentList(String proteinContent) {
		List<String> result = new ArrayList<String>();
		Matcher tableMatcher = tablePattern.matcher(proteinContent);
		// Find the table contains "Mr(expt)"
		while (tableMatcher.find()) {
			if (-1 != tableMatcher.group(1).indexOf("Mr(expt)")) {
				break;
			}
		}

		if (tableMatcher.matches()) {

			Matcher trMatcher = trPattern.matcher(tableMatcher.group(1));
			trMatcher.find(); // excluding first matcher, it's header
			while (trMatcher.find()) {
				result.add(trMatcher.group(1));
			}
		}
		return result;
	}

	@Override
	protected List<String> getPeptideInfo(String peptideInfoContent) {
		Matcher matcher = peptideInfoPattern.matcher(peptideInfoContent);
		if (!matcher.find()) {
			return new ArrayList<String>();
		}

		return RcpaMiscUtils.matcherToList(matcher);
	}

	@Override
	protected Pattern getTablePattern() {
		return tablePattern;
	}
}
