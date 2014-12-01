package cn.ac.rcpa.bio.proteomics.detectability;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.ac.rcpa.utils.RcpaFileUtils;

public class DetectabilityEntry {
	private String protein;

	private String peptide;

	private double detectability;

	private int mDepthCount;

	public static double DEFAULT_SCORE = Double.MAX_VALUE;

	private double score = DEFAULT_SCORE;

	private Set<String> experimentals = new HashSet<String>();

	public DetectabilityEntry() {
		super();
	}

	public double getDetectability() {
		return detectability;
	}

	public void setDetectability(double detectability) {
		this.detectability = detectability;
	}

	public String getPeptide() {
		return peptide;
	}

	public void setPeptide(String peptide) {
		this.peptide = peptide;
	}

	public String getProtein() {
		return protein;
	}

	public void setProtein(String protein) {
		this.protein = protein;
	}

	public boolean isDetected() {
		return experimentals.size() > 0;
	}

	public static List<DetectabilityEntry> readDetectabilityEntryList(String dir)
			throws Exception {
		String[] peptides = RcpaFileUtils.readFile(dir + "\\peppro.txt", true);
		String[] detectability = RcpaFileUtils.readFile(dir
				+ "\\detectabilityres.txt", true);

		List<DetectabilityEntry> deList = new ArrayList<DetectabilityEntry>();
		for (int iPep = 0; iPep < peptides.length; iPep++) {
			double d = Double.parseDouble(detectability[iPep]);
			String pep = peptides[iPep];
			String[] pp = pep.split("\\s+");
			if (pp.length < 2) {
				throw new Exception("Wrong peptide/protein line : " + pep);
			}
			DetectabilityEntry de = new DetectabilityEntry();
			de.setProtein(pp[1]);
			de.setPeptide(pp[0]);
			de.setDetectability(d);
			deList.add(de);
		}
		return deList;
	}

	public static List<DetectabilityEntry> readFromFile(String filename)
			throws Exception {
		List<DetectabilityEntry> result = new ArrayList<DetectabilityEntry>();

		String[] lines = RcpaFileUtils.readFile(filename, true);
		for (int i = 1; i < lines.length; i++) {
			String[] parts = lines[i].split("\t");
			if (3 == parts.length) {
				DetectabilityEntry de = new DetectabilityEntry();
				de.setProtein(parts[0]);
				de.setPeptide(parts[1]);
				de.setDetectability(Double.parseDouble(parts[2]));
				result.add(de);
			}
		}

		return result;
	}

	public static void writeToFile(String filename,
			List<DetectabilityEntry> deList) throws Exception {
		DecimalFormat df4 = new DecimalFormat("0.0000");
		PrintWriter pwResult = new PrintWriter(filename);
		try {
			pwResult.println("Protein\tPeptide\tDetectability");
			for (DetectabilityEntry de : deList) {
				pwResult.println(de.getProtein() + "\t" + de.getPeptide() + "\t"
						+ df4.format(de.getDetectability()));
			}
		} finally {
			pwResult.close();
		}
	}

	public Set<String> getExperimentals() {
		return experimentals;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getMDepthCount() {
		return mDepthCount;
	}

	public void setMDepthCount(int depthCount) {
		mDepthCount = depthCount;
	}

}
