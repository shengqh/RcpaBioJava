package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.RcpaMiscUtils;

public class MascotResultHtmlClusterParser extends
		AbstractMascotResultHtmlParser {
	public MascotResultHtmlClusterParser(boolean filterByDefaultScoreAndPvalue) {
		super(filterByDefaultScoreAndPvalue);
	}

	public MascotResultHtmlClusterParser(
			IFilter<MascotPeptide> defaultPeptideFilter,
			boolean filterByDefaultScoreAndPvalue) {
		super(defaultPeptideFilter,filterByDefaultScoreAndPvalue);
	}

	private static Pattern proteinPattern;

	@Override
	protected Pattern getProteinPattern() {
		if (null == proteinPattern) {
			proteinPattern = Pattern.compile("(<A\\sname=Hit.+?<TABLE.+?</TABLE>)");
		}
		return proteinPattern;
	}
	
	@Override 
	protected Pattern getTablePattern(){
		return tablePattern;
	}

	private static Pattern proteinInfoPattern = Pattern
			.compile("<A\\shref=.+?>(.+?)</A>.+?Mass:.+?(\\d+).+?matched.+?\\d+\\s+(.+?)<INPUT");

	private static Pattern proteinNamePattern = Pattern
			.compile("<A\\shref=.+?>(.+?)</A>");

	private static Pattern peptideInfoPattern = Pattern
			.compile("<TR>.+?value=\"([\\d.]+)\\sfrom\\(([\\d.]+),(\\d+)\\+\\)\\stitle\\((.+?)\\)\\squery\\((\\d+)\\).+?<B>([\\d.]+)(?:&nbsp;)+([\\d.]+)(?:&nbsp;)+([\\d.]+)(?:&nbsp;)+([\\d.-]+)(?:&nbsp;)+([\\d.]+)(?:&nbsp;)+([\\d.]+)(?:&nbsp;)+([\\d.]+)(?:&nbsp;)+(\\S+)</B>.+?</TR>");

	private static Pattern tablePattern = Pattern.compile("(<TABLE.+?</TABLE>)");

	private static Pattern trPattern = Pattern.compile("(<TR.+?</TR>)");

	@Override
	protected List<String> getProteinInfo(String proteinContent) {
		List<String> result = new ArrayList<String>();
		Matcher proteinInfoMatcher = proteinInfoPattern.matcher(proteinContent);
		if (proteinInfoMatcher.find()) {
			result.add(proteinInfoMatcher.group(1));
			result.add(proteinInfoMatcher.group(2));
			result.add(proteinInfoMatcher.group(3).trim());
		} else {
			Matcher proteinNameMatcher = proteinNamePattern.matcher(proteinContent);
			if (!proteinNameMatcher.find()) {
				throw new IllegalArgumentException("Cannot find protein name in : "
						+ proteinContent);
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
		boolean bFound = false;
		while (tableMatcher.find()) {
			if (-1 != tableMatcher.group(1).indexOf("Mr(expt)")) {
				bFound = true;
				break;
			}
		}

		if (bFound) {
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

		List<String> result = RcpaMiscUtils.matcherToList(matcher);
		result.add(11, "0.0");
		return result;
	}
}
