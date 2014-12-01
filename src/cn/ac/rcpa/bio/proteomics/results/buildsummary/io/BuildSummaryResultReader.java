package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.annotator.IIdentifiedResultAnnotator;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.proteomics.utils.IdentifiedResultUtils;
import cn.ac.rcpa.bio.sequest.SequestParseException;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class BuildSummaryResultReader implements
		IIdentifiedResultReader<BuildSummaryResult> {
	private IIdentifiedResultAnnotator<BuildSummaryResult> annotator;

	private final static String proteinPatternStr = "\\$(\\d+)-\\d+\\s+(.*?)\\s+\\d+\\s+\\d+\\s+(\\S+)%\\s+(\\S+)\\s+(\\S+)";

	private static Pattern proteinPattern = null;

	private static Pattern getProteinPattern() {
		if (proteinPattern == null) {
			proteinPattern = Pattern.compile(proteinPatternStr);
		}
		return proteinPattern;
	}

	private final static String containProteinPatternStr = "@(\\d+)-\\d+";

	private static Pattern containProteinPattern = null;

	private static Pattern getContainProteinPattern() {
		if (containProteinPattern == null) {
			containProteinPattern = Pattern.compile(containProteinPatternStr);
		}
		return containProteinPattern;
	}

	public BuildSummaryResultReader() {
		annotator = null;
	}

	public BuildSummaryResultReader(
			IIdentifiedResultAnnotator<BuildSummaryResult> annotator) {
		this.annotator = annotator;
	}

	public BuildSummaryResult readOnly(String filename) throws IOException,
			SequestParseException, IllegalStateException {
		BuildSummaryResult result = new BuildSummaryResult();

		System.out.print("Reading " + filename + " ...");
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("$")) {
					break;
				}
			}

			Map<String, BuildSummaryPeptideHit> scanPeptideMap = new HashMap<String, BuildSummaryPeptideHit>();
			Map<Integer, BuildSummaryProteinGroup> indexGroupMap = new HashMap<Integer, BuildSummaryProteinGroup>();
			boolean bParsingProtein = false;
			BuildSummaryProteinGroup group = new BuildSummaryProteinGroup();
			while (line != null) {
				line = line.trim();

				if (line.length() == 0) {
					break;
				}

				if (line.startsWith("$")) {
					BuildSummaryProtein protein = parseProtein(line);
					if (protein == null) {
						throw new IllegalStateException(line
								+ " is not a valid protein line");
					}

					if (!bParsingProtein) {
						if (group.getProteinCount() != 0) {
							result.addProteinGroup(group);
						}
						bParsingProtein = true;
						group = new BuildSummaryProteinGroup();
						group.setNumber(protein.getGroup());
						indexGroupMap.put(protein.getGroup(), group);
					}

					group.addProtein(protein);
				} else if (line.startsWith("@")) {
					int parentGroupIndex = getParentGroup(line);
					BuildSummaryProteinGroup parentGroup = indexGroupMap
							.get(parentGroupIndex);
					if (parentGroup != null) {
						group.addParent(parentGroup);
					}
				} else {
					bParsingProtein = false;
					BuildSummaryPeptideHit pephit = BuildSummaryPeptideHitReader
							.parse(line);
					if (!scanPeptideMap.containsKey(pephit.getPeakListInfo()
							.getLongFilename())) {
						scanPeptideMap.put(pephit.getPeakListInfo().getLongFilename(),
								pephit);
					} else {
						pephit = scanPeptideMap.get(pephit.getPeakListInfo()
								.getLongFilename());
					}
					linkPeptideHitToProteinHit(group, pephit);
				}

				line = br.readLine();
			}

			if (group.getProteinCount() != 0) {
				result.addProteinGroup(group);
			}
		} finally {
			br.close();
		}

		String enzymeFile = RcpaFileUtils.changeExtension(filename,
				".peptides.enzyme");
		if (new File(enzymeFile).exists()) {
			BuildSummaryPeptideEnzymeFile.fill(enzymeFile, result.getPeptideHits());
		}

		System.out.println(" finished.");
		return result;
	}

	private int getParentGroup(String line) {
		Matcher matcher = getContainProteinPattern().matcher(line);
		if (!matcher.find()) {
			throw new IllegalStateException(line
					+ " is not a valid redundant protein line");
		}

		return Integer.parseInt(matcher.group(1));
	}

	public BuildSummaryResult read(String filename) throws IOException,
			SequestParseException {
		BuildSummaryResult result = readOnly(filename);

		String fastaFilename = filename + ".fasta";
		if (new File(fastaFilename).exists()) {
			System.out.println("Filling sequence from fasta file ...");
			IdentifiedResultUtils.fillSequenceByName(result.getProteins(),
					fastaFilename);
		}

		if (annotator != null) {
			System.out.println("Adding annotation ...");
			annotator.addAnnotation(result);
		}

		// System.out.println("Building group relationship ...");
		// result.buildGroupRelationship();

		return result;
	}

	private void linkPeptideHitToProteinHit(
			BuildSummaryProteinGroup nextProteinGroup, BuildSummaryPeptideHit pephit) {
		for (int proIndex = 0; proIndex < nextProteinGroup.getProteinCount(); proIndex++) {

			final BuildSummaryProtein prohit = nextProteinGroup.getProtein(proIndex);

			boolean bFound = false;
			for (int pepIndex = 0; pepIndex < pephit.getPeptideCount(); pepIndex++) {
				final BuildSummaryPeptide peptide = pephit.getPeptide(pepIndex);
				for (int proNameIndex = 0; proNameIndex < peptide.getProteinNameCount(); proNameIndex++) {
					if (peptide.getProteinName(proNameIndex).indexOf(
							prohit.getProteinName()) != -1) {
						prohit.addPeptide(peptide);
						bFound = true;
						break;
					}
				}
			}
			if (!bFound) {
				prohit.addPeptide(pephit.getPeptide(0));
			}
		}
	}

	public static BuildSummaryProtein parseProtein(String line) {
		Matcher matcher = getProteinPattern().matcher(line);
		if (!matcher.find()) {
			return null;
		}

		BuildSummaryProtein result = new BuildSummaryProtein();

		result.setGroup(Integer.parseInt(matcher.group(1)));
		result.setReference(matcher.group(2));

		String[] names = matcher.group(2).split("\\s");
		result.setProteinName(names.length > 0 ? names[0] : "");

		result.setCoverage(Double.parseDouble(matcher.group(3)));
		result.setMW(Double.parseDouble(matcher.group(4)));
		result.setPI(Double.parseDouble(matcher.group(5)));

		return result;
	}
}
