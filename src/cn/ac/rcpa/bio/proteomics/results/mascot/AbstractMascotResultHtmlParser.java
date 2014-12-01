package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IsotopicType;
import cn.ac.rcpa.bio.sequest.SequestFilename;
import cn.ac.rcpa.bio.sequest.SequestParseException;
import cn.ac.rcpa.filter.AndFilter;
import cn.ac.rcpa.filter.FilterNothing;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.Pair;
import cn.ac.rcpa.utils.RcpaFileUtils;

public abstract class AbstractMascotResultHtmlParser implements
		IMascotResultParser {
	private class Offset {
		public Offset(int start, int end, MascotProteinGroup mpg) {
			this.start = start;
			this.end = end;
			this.mpg = mpg;
		}

		private int start;

		private int end;

		private MascotProteinGroup mpg;

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public MascotProteinGroup getMpg() {
			return mpg;
		}

		public void setMpg(MascotProteinGroup mpg) {
			this.mpg = mpg;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}
	}

	private boolean filterByDefaultScoreAndPvalue = true;

	private IFilter<MascotPeptide> defaultPeptideFilter = null;

	private IFilter<MascotPeptide> currentPeptideFilter = new FilterNothing<MascotPeptide>();

	public AbstractMascotResultHtmlParser(boolean filterByDefaultScoreAndPvalue) {
		super();
		this.filterByDefaultScoreAndPvalue = filterByDefaultScoreAndPvalue;
	}

	public AbstractMascotResultHtmlParser(
			IFilter<MascotPeptide> defaultPeptideFilter,
			boolean filterByDefaultScoreAndPvalue) {
		super();
		this.defaultPeptideFilter = defaultPeptideFilter;
		this.filterByDefaultScoreAndPvalue = filterByDefaultScoreAndPvalue;
	}

	private static Pattern pValuePattern;

	protected Pattern getPValuePattern() {
		if (pValuePattern == null) {
			pValuePattern = Pattern
					.compile("(\\d+)\\sindicate\\sidentity\\sor\\sextensive\\shomology\\s\\(p&lt;([\\d.]+)\\)");
		}
		return pValuePattern;
	}

	private static Pattern peakIsotopicTypePattern;

	protected Pattern getPeakIsotopicTypePattern() {
		if (peakIsotopicTypePattern == null) {
			peakIsotopicTypePattern = Pattern
					.compile("Mass\\svalues\\s+:\\s+(Monoisotopic|Average)");
		}
		return peakIsotopicTypePattern;
	}

	private static Pattern peakTolerancePattern;

	protected Pattern getPeakTolerancePattern() {
		if (peakTolerancePattern == null) {
			peakTolerancePattern = Pattern
					.compile("Fragment Mass Tolerance.*?:.+?([\\d.]+)\\s");
		}
		return peakTolerancePattern;
	}

	private static Pattern cmpdPattern;

	protected Pattern getCmpdPattern() {
		if (cmpdPattern == null) {
			cmpdPattern = Pattern.compile("Cmpd\\s+(\\d+)");
		}
		return cmpdPattern;
	}

	private static Pattern sqhCmpdPattern;

	protected Pattern getSqhCmpdPattern() {
		if (sqhCmpdPattern == null) {
			sqhCmpdPattern = Pattern.compile("(.+),\\s+Cmpd\\s+(\\d+)");
		}
		return sqhCmpdPattern;
	}

	public void setFilterByDefaultScoreAndPvalue(boolean value) {
		this.filterByDefaultScoreAndPvalue = value;
	}

	public Pair<Integer, Double> parsePValueScore(String content) {
		Matcher matcher = getPValuePattern().matcher(content);
		if (!matcher.find()) {
			throw new IllegalArgumentException("No pvalue pattern found in "
					+ content);
		}

		return new Pair<Integer, Double>(Integer.parseInt(matcher.group(1)), Double
				.parseDouble(matcher.group(2)));
	}

	public IsotopicType parsePeakIsotopicType(String content) {
		Matcher matcher = getPeakIsotopicTypePattern().matcher(content);
		if (!matcher.find()) {
			throw new IllegalArgumentException("No peak isotopic type pattern found.");
		}

		if (matcher.group(1).equals("Monoisotopic")) {
			return IsotopicType.Monoisotopic;
		} else {
			return IsotopicType.Average;
		}
	}

	public double parsePeakTolerance(String content) {
		Matcher matcher = getPeakTolerancePattern().matcher(content);
		if (!matcher.find()) {
			throw new IllegalArgumentException("No peak tolerance pattern found.");
		}

		return Double.parseDouble(matcher.group(1));
	}

	public MascotResult parseFile(File file) throws Exception {
		String fileContent = RcpaFileUtils.readFileWithoutLineBreak(file
				.getAbsolutePath(), true);
		return parseContent(fileContent);
	}

	public MascotResult parseContent(String fileContent) throws Exception {
		MascotResult result = new MascotResult();

		Pair<Integer, Double> pValueScore = parsePValueScore(fileContent);
		result.setPValueScore(pValueScore.fst);
		result.setPValue(pValueScore.snd);

		List<Offset> offsets = new ArrayList<Offset>();

		try {
			result.setPeakIsotopicType(parsePeakIsotopicType(fileContent));
		} catch (IllegalArgumentException ex) {
		}

		try {
			result.setPeakTolerance(parsePeakTolerance(fileContent));
		} catch (IllegalArgumentException ex) {
		}

		List<IFilter<MascotPeptide>> filters = new ArrayList<IFilter<MascotPeptide>>();
		if (filterByDefaultScoreAndPvalue) {
			filters.add(new MascotPeptideScoreFilter(pValueScore.fst));
			filters.add(new MascotPeptidePValueFilter(pValueScore.snd));
		}
		filters.add(new MascotPeptideRankFilter(1));
		if (null != defaultPeptideFilter) {
			filters.add(defaultPeptideFilter);
		}
		currentPeptideFilter = new AndFilter<MascotPeptide>(filters);

		Matcher proteinMatcher = getProteinPattern().matcher(fileContent);
		while (proteinMatcher.find()) {
			MascotProtein protein = parseProtein(proteinMatcher.group(1));
			MascotProteinGroup group = new MascotProteinGroup();
			group.add(protein);
			result.add(group);
			offsets.add(new Offset(proteinMatcher.start(), proteinMatcher.end(),
					group));
		}

		int endIndex = fileContent
				.indexOf("Peptide matches not assigned to protein hits");
		if (-1 == endIndex) {
			endIndex = fileContent.length();
		}
		for (int i = 0; i < offsets.size(); i++) {
			int start = offsets.get(i).getEnd();
			int end = i == offsets.size() - 1 ? endIndex : offsets.get(i + 1)
					.getStart();
			String redundant = fileContent.substring(start, end);
			if (!redundant.contains("Proteins matching the same set")) {
				continue;
			}

			List<MascotProtein> sameMatchProteins = parseSameMatchProteins(redundant);

			for (MascotProtein mp : sameMatchProteins) {
				mp.getPeptides().addAll(offsets.get(i).getMpg().get(0).getPeptides());
				offsets.get(i).getMpg().add(mp);
			}
		}

		for (int i = result.size() - 1; i >= 0; i--) {
			if (0 == result.get(i).get(0).getPeptideCount()) {
				result.remove(i);
			}
		}

		mergePeptides(result);

		return result;
	}

	protected abstract Pattern getTablePattern();

	protected List<MascotProtein> parseSameMatchProteins(String redundant)
			throws Exception {
		List<MascotProtein> result = new ArrayList<MascotProtein>();

		Matcher tableMatcher = getTablePattern().matcher(redundant);
		while (tableMatcher.find()) {
			try {
				MascotProtein mp = getProtein(tableMatcher.group(1));
				result.add(mp);
			} catch (IllegalArgumentException ex) {
			}
		}

		return result;
	}

	private void mergePeptides(MascotResult result) {
		Map<String, MascotPeptide> peptideMap = new HashMap<String, MascotPeptide>();
		for (MascotProteinGroup group : result) {
			for (MascotProtein protein : group) {
				for (int i = 0; i < protein.getPeptideCount(); i++) {
					MascotPeptide pep = protein.getPeptide(i);
					String pepid = pep.getQuery() + "_" + pep.getCharge() + "_"
							+ pep.getRank();
					if (peptideMap.containsKey(pepid)) {
						MascotPeptide old = peptideMap.get(pepid);
						old.addProteinName(protein.getProteinName());
						protein.setPeptide(i, old);
					} else {
						peptideMap.put(pepid, pep);
					}
				}
			}
		}
	}

	public IFilter<MascotPeptide> getPeptideFilter() {
		return currentPeptideFilter;
	}

	protected MascotProtein parseProtein(String proteinContent)
			throws SequestParseException, UnsupportedEncodingException {
		MascotProtein result = getProtein(proteinContent);

		List<String> peptideInfoContentList = getPeptideInfoContentList(proteinContent);
		for (String peptideInfoContent : peptideInfoContentList) {
			List<String> peptideInfo = getPeptideInfo(peptideInfoContent);
			if (peptideInfo.isEmpty()) {
				continue;
			}

			MascotPeptide peptide = new MascotPeptide();

			// Group 0 : peptide mass from observed m/z
			double experimentalPeptideMass = Double.parseDouble(peptideInfo.get(0));
			// Group 1 : observed m/z
			double observed = Double.parseDouble(peptideInfo.get(1));
			peptide.setObservedMz(observed);

			// Group 2 : charge
			int charge = Integer.parseInt(peptideInfo.get(2));
			peptide.setCharge(charge);

			peptide
					.setPrecursorHydrogenMass((observed * charge - experimentalPeptideMass)
							/ charge);
			double experimentalSingleChargeMass = experimentalPeptideMass
					+ peptide.getPrecursorHydrogenMass();
			peptide.setExperimentalSingleChargeMass(experimentalSingleChargeMass);

			// Group 3 : title
			String title = URLDecoder.decode(peptideInfo.get(3), "UTF-8").trim();
			peptide.setTitle(title);

			if (title.startsWith("Cmpd")) {
				Matcher matcher = getCmpdPattern().matcher(title);
				matcher.find();
				int scan = Integer.parseInt(matcher.group(1));
				peptide.getPeakListInfo().setExperiment("Cmpd");
				peptide.getPeakListInfo().setFirstScan(scan);
				peptide.getPeakListInfo().setLastScan(scan);
			} else if (title.contains("Cmpd")) {
				Matcher matcher = getSqhCmpdPattern().matcher(title);
				matcher.find();
				int scan = Integer.parseInt(matcher.group(2));
				peptide.getPeakListInfo().setExperiment(matcher.group(1));
				peptide.getPeakListInfo().setFirstScan(scan);
				peptide.getPeakListInfo().setLastScan(scan);
			} else {
				String dtaFilename = title + "." + charge + ".dta";
				SequestFilename sf = SequestFilename.parse(dtaFilename);
				peptide.getPeakListInfo().setExperiment(sf.getExperiment());
				peptide.getPeakListInfo().setFirstScan(sf.getFirstScan());
				peptide.getPeakListInfo().setLastScan(sf.getLastScan());
			}
			
			// Group 4 : query
			peptide.setQuery(Integer.parseInt(peptideInfo.get(4)));
			
			// Group 5 equals Group 1
			// Group 6 equals Group 0
			// Group 7 : calculated peptide mass
			peptide.setTheoreticalSingleChargeMass(Double.parseDouble(peptideInfo
					.get(7))
					+ peptide.getPrecursorHydrogenMass());
			// Group 8 : different between observed peptide mass and calculated
			// peptide mass
			// Group 9 : miss cleavage
			peptide.setMissCleavage(Integer.parseInt(peptideInfo.get(9)));
			// Group 10: score
			peptide.setScore(Integer.parseInt(peptideInfo.get(10)));
			// Group 11: expect p value
			peptide.setPValue(Double.parseDouble(peptideInfo.get(11)));
			// Group 12: rank
			peptide.setRank(Integer.parseInt(peptideInfo.get(12)));
			// Group 13: peptide sequence
			// &nbsp;K.YEINVLR<u>.</u>N + Label:18O(2) (C-term)
			String seq = peptideInfo.get(13);
			seq = seq.replace("<u>", "");
			seq = seq.replace("</u>", "*");
			seq = seq.replace("<U>", "");
			seq = seq.replace("</U>", "*");
			seq = seq.replace(".*", ".");
			seq = seq.replace("&nbsp;", "");
			String[] parts = seq.split("\\+");
			peptide.setSequence(parts[0].trim());
			if (parts.length > 1) {
				peptide.setModification(parts[1].trim());
			}

			if (getPeptideFilter().accept(peptide)) {
				result.addPeptide(peptide);
				peptide.addProteinName(result.getProteinName());
			}
		}

		return result;
	}

	private MascotProtein getProtein(String proteinContent) {
		List<String> proteinInfo = getProteinInfo(proteinContent);

		MascotProtein result = new MascotProtein();
		result.setProteinName(proteinInfo.get(0));
		result.setMW(Double.parseDouble(proteinInfo.get(1)));
		result.setReference(proteinInfo.get(2));

		return result;
	}

	// Get a pattern which can match protein content one by one
	protected abstract Pattern getProteinPattern();

	// Parse protein information from protein content
	// First item should be protein name
	// Second item should be protein reference
	protected abstract List<String> getProteinInfo(String proteinContent);

	// Parse and get peptide info content list from protein content
	protected abstract List<String> getPeptideInfoContentList(
			String proteinContent);

	// Parse peptide information from peptide info content
	protected abstract List<String> getPeptideInfo(String peptideInfoContent);
}
