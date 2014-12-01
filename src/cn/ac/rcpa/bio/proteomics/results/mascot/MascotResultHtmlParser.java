package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.File;

import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class MascotResultHtmlParser implements IMascotResultParser {
	private MascotResultHtmlClusterParser oldParser;

	private MascotResultHtmlStandaloneParser newParser;

	public MascotResultHtmlParser(boolean filterByDefaultScoreAndPvalue) {
		super();
		oldParser = new MascotResultHtmlClusterParser(filterByDefaultScoreAndPvalue);
		newParser = new MascotResultHtmlStandaloneParser(
				filterByDefaultScoreAndPvalue);
	}

	public MascotResultHtmlParser(IFilter<MascotPeptide> defaultPeptideFilter,
			boolean filterByDefaultScoreAndPvalue) {
		super();
		oldParser = new MascotResultHtmlClusterParser(defaultPeptideFilter,
				filterByDefaultScoreAndPvalue);
		newParser = new MascotResultHtmlStandaloneParser(defaultPeptideFilter,
				filterByDefaultScoreAndPvalue);
	}

	public MascotResult parseContent(String fileContent) throws Exception {
		MascotResult result = newParser.parseContent(fileContent);

		if (0 == result.size()) {
			result = oldParser.parseContent(fileContent);
		}

		return result;
	}

	public MascotResult parseFile(File file) throws Exception {
		String fileContent = RcpaFileUtils.readFileWithoutLineBreak(file
				.getAbsolutePath(), true);
		return parseContent(fileContent);
	}

}
