package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.biojava.bio.BioException;
import org.biojava.bio.proteomics.Protease;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.utils.RcpaProteaseFactory;

public class BuildSummaryPeptideEnzymeFile {
	public static String DEFAULT_ENZYME_KEY = "DEFAULT_ENZYME";

	public static void write(String filename,
			Collection<BuildSummaryPeptideHit> peptides) throws IOException {
		if (peptides.size() == 0) {
			return;
		}

		Map<Protease, Integer> enzymeNames = new HashMap<Protease, Integer>();
		for (BuildSummaryPeptideHit phit:peptides) {
			if (null == phit.getProtease()) {
				continue;
			}

			if (!enzymeNames.containsKey(phit.getProtease())) {
				enzymeNames.put(phit.getProtease(), 0);
			}

			int count = enzymeNames.get(phit.getProtease()) + 1;
			enzymeNames.put(phit.getProtease(), count);
		}

		if (0 == enzymeNames.size()) {
			return;
		}

		Protease maxEnzyme = enzymeNames.keySet().iterator().next();
		int maxCount = enzymeNames.get(maxEnzyme);
		for (Protease curEnzyme : enzymeNames.keySet()) {
			int curCount = enzymeNames.get(curEnzyme);
			if (maxCount < curCount) {
				maxEnzyme = curEnzyme;
				maxCount = curCount;
			}
		}

		PrintWriter pw = new PrintWriter(filename);
		try {
			for (Protease protease : enzymeNames.keySet()) {
				pw.println(protease.getName()
						+ "\t\t\t\t"
						+ (protease.isEndoProtease() ? 1 : 0)
						+ "\t"
						+ (protease.getCleaveageResidues().length() == 0 ? "-" : protease
								.getCleaveageResidues().seqString())
						+ "\t\t"
						+ (protease.getNotCleaveResidues().length() == 0 ? "-" : protease
								.getNotCleaveResidues().seqString()));
			}
			pw.println();
			pw.println(DEFAULT_ENZYME_KEY + "\t" + maxEnzyme.getName());
		} finally {
			pw.close();
		}
	}

	private static Protease parse(String line) {
		Protease result = null;
		String[] infos = line.split("\t+");
		if (infos.length == 4) {
			try {
				if (infos[2].equals("-")) {
					infos[2] = "";
				}
				if (infos[3].equals("-")) {
					infos[3] = "";
				}
				if (infos[2].length() == 0 && infos[3].length() == 0) {
					return result;
				}

				result = RcpaProteaseFactory.getInstance().createProtease(infos[0],
						infos[1].equals("1"), infos[2], infos[3]);
			} catch (BioException ex) {
				ex.printStackTrace();
				throw new IllegalStateException(line + " is not a valid protease!");
			}
		}

		return result;
	}

	public static Map<String, Protease> read(String filename) throws IOException {
		Map<String, Protease> result = new HashMap<String, Protease>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		Map<String, Protease> enzymeMap = new HashMap<String, Protease>();
		String sline;
		while ((sline = br.readLine()) != null) {
			if (sline.length() == 0) {
				break;
			}

			Protease protease = parse(sline);
			String[] parts = sline.split("\t+");
			enzymeMap.put(parts[0], protease);
		}

		while ((sline = br.readLine()) != null) {
			if (sline.length() == 0) {
				break;
			}

			String[] lines = sline.split("\t+");
			if (lines.length < 2) {
				break;
			}

			result.put(lines[0], enzymeMap.get(lines[1]));
		}

		return result;
	}

	public static void fill(String filename, List<BuildSummaryPeptideHit> peptides)
			throws IOException {
		Map<String, Protease> filenameEnzymeMap = read(filename);
		Protease defaultProtease = filenameEnzymeMap.get(DEFAULT_ENZYME_KEY);

		for (BuildSummaryPeptideHit phit : peptides) {
			phit.getPeakListInfo().setExtension("out");

			String longFilename = phit.getPeakListInfo().getLongFilename();
			if (filenameEnzymeMap.containsKey(longFilename)) {
				phit.setProtease(filenameEnzymeMap.get(longFilename));
			} else {
				phit.setProtease(defaultProtease);
			}
		}
	}

}
