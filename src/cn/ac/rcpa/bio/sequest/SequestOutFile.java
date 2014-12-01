package cn.ac.rcpa.bio.sequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.utils.RegexValueType;

/**
 * SEQUEST搜库结果out文件的解析类，调用read函数。
 *
 * @author Sheng QuanHu
 * @version 1.0.0.0
 */
public class SequestOutFile {
  private SequestFilename filename;
  private double experimentSingleChargeMass;
  private ArrayList<BuildSummaryPeptide> peptideHitList = new ArrayList<BuildSummaryPeptide>();

  // (M+H)+ mass = 509.8739 ~ 3.0000 (+1), fragment tol = 0.0, AVG/AVG
  private static String MHPATTERN = "\\(M\\+H\\)\\+ mass ="
      + RegexValueType.GET_DOUBLE
      + " ~ .*";
  private static Pattern MHPattern = null;

  //  0  gi|3769362|gb|AAC64498.1| ectoderm
  private static String DUPLICATE_REFERENCE_PATTERN = "\\s+\\d\\s+(\\S+)";
  private static Pattern duplicateRefPattern = null;

  public SequestOutFile() {
    super();
  }

  public SequestFilename getFilename() {
    return filename;
  }

  public void setFilename(SequestFilename value) {
    this.filename = value;
  }

  public double getExperimentSingleChargeMass() {
    return experimentSingleChargeMass;
  }

  public void setExperimentSingleChargeMass(
      double ExperimentSingleChargeMass) {
    this.experimentSingleChargeMass = ExperimentSingleChargeMass;
  }

  public int getPeptideHitCount() {
    return peptideHitList.size();
  }

  public BuildSummaryPeptide getPeptideHit(int index) {
    return (BuildSummaryPeptide) peptideHitList.get(index);
  }

  public BuildSummaryPeptide[] getPeptideHit() {
    return (BuildSummaryPeptide[]) peptideHitList.toArray(new BuildSummaryPeptide[0]);
  }

  private static Pattern getMHPattern() {
    if (MHPattern == null) {
      MHPattern = Pattern.compile(MHPATTERN);
    }
    return MHPattern;
  }

  private static Pattern getDuplicateRefPattern() {
    if (duplicateRefPattern == null) {
      duplicateRefPattern = Pattern.compile(DUPLICATE_REFERENCE_PATTERN);
    }
    return duplicateRefPattern;
  }

  /**
   * 从给定文件读取信息
   *
   * @param outFilename String
   * @param maxRank int
   *   一个out文件往往有多个PeptideHit，设定取前几个rank的PeptideHit，一般取1。
   * @throws IOException
   * @return SequestOutFile
   */
  public static SequestOutFile read(String outFilename, int maxRank) throws
      IOException {
    SequestOutFile result = new SequestOutFile();

    BufferedReader rd = new BufferedReader(new FileReader(outFilename));

    result.parseFileName(rd);

    result.parseExperimentSingleChargeMass(rd);

    result.parsePeptideHit(rd, maxRank);

    return result;
  }

  private void parsePeptideHit(BufferedReader rd, int maxRank) throws
      IOException {
    peptideHitList.clear();

    String line = null;
    BuildSummaryPeptide lasthit = null;
    while ( (line = rd.readLine()) != null) {
      if (line.trim().length() == 0) {
        continue;
      }

      BuildSummaryPeptide hit = BuildSummaryPeptide.parse(line);
      if (hit != null) {
        if (lasthit != null) {
          lasthit.setDeltacn(hit.getDeltacn() - lasthit.getDeltacn());
        }
        if (hit.getRank() > maxRank) {
          break;
        }
        hit.setFilename(filename);

        peptideHitList.add(hit);
        lasthit = hit;
      }
      else if (lasthit != null && lasthit.getDuplicateRefCount() != 0) {
        Matcher matcher = getDuplicateRefPattern().matcher(line);
        if (matcher.find()) {
          lasthit.addProteinName(matcher.group(1));
        }
      }
    }
  }

  private void parseFileName(BufferedReader rd) throws IOException {
//    System.out.println(FILEINFO_PATTERN);
    String line = null;
    while ( (line = rd.readLine()) != null) {
      try {
        filename = SequestFilename.parse(line);
        return;
      }
      catch (SequestParseException ex) {
      }
    }

    throw new IllegalArgumentException("Parameter of parseFileName error :\n\tInput file is not a valid out file cause there is no line include filename.");
  }

  private void parseExperimentSingleChargeMass(BufferedReader rd) throws
      IOException {
//    System.out.println(MHPATTERN);
    String line = null;
    while ( (line = rd.readLine()) != null) {
//      System.out.println(line);
      Matcher matcher = getMHPattern().matcher(line);
      if (matcher.find()) {
        experimentSingleChargeMass = Double.parseDouble(matcher.group(1));
        return;
      }
    }

    throw new IllegalArgumentException("Parameter of parseExperimentSingleChargeMass error :\n\tInput file is not a valid out file cause there is no line include experiment single charge mass.");
  }
}
