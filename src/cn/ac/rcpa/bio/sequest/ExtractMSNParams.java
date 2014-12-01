package cn.ac.rcpa.bio.sequest;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jdom.JDOMException;

import cn.ac.rcpa.utils.ShellUtils;
import cn.ac.rcpa.utils.XMLFile;

public class ExtractMSNParams {
  private boolean allScan;
  private int firstScan;
  private int lastScan;
  private double bottomPeptideMW;
  private double topPeptideMW;
  private double precursorMassTolerance;
  private int groupCount;
  private int minGroupCount;
  private double minTIC;
  private int msLevel;
  private String rawFilename;
  private String resultDirectory;

  public boolean isAllScan() {
    return allScan;
  }

  public double getMinTIC() {
    return minTIC;
  }

  public int getGroupCount() {
    return groupCount;
  }

  public int getFirstScan() {
    return firstScan;
  }

  public int getMinGroupCount() {
    return minGroupCount;
  }

  public double getPrecursorMassTolerance() {
    return precursorMassTolerance;
  }

  public double getTopPeptideMW() {
    return topPeptideMW;
  }

  public double getBottomPeptideMW() {
    return bottomPeptideMW;
  }

  public void setLastScan(int lastScan) {
    this.lastScan = lastScan;
  }

  public void setAllScan(boolean allScan) {
    this.allScan = allScan;
  }

  public void setMinTIC(double minTIC) {
    this.minTIC = minTIC;
  }

  public void setGroupCount(int groupCount) {
    this.groupCount = groupCount;
  }

  public void setFirstScan(int firstScan) {
    this.firstScan = firstScan;
  }

  public void setMinGroupCount(int minGroupCount) {
    this.minGroupCount = minGroupCount;
  }

  public void setPrecursorMassTolerance(double precursorMassTolerance) {
    this.precursorMassTolerance = precursorMassTolerance;
  }

  public void setTopPeptideMW(double topPeptideMW) {
    this.topPeptideMW = topPeptideMW;
  }

  public void setBottomPeptideMW(double bottomPeptideMW) {
    this.bottomPeptideMW = bottomPeptideMW;
  }

  public void setRawFilename(String rawFilename) {
    this.rawFilename = rawFilename;
  }

  public void setResultDirectory(String resultDirectory) {
    this.resultDirectory = resultDirectory;
  }

  public int getLastScan() {
    return lastScan;
  }

  public int getMsLevel() {
    return msLevel;
  }

  public void setMsLevel(int msLevel) {
    this.msLevel = msLevel;
  }

  public String getRawFilename() {
    return rawFilename;
  }

  public String getResultDirectory() {
    return resultDirectory;
  }

  public ExtractMSNParams() {
    allScan = true;
    firstScan = 1;
    lastScan = 100000;
    bottomPeptideMW = 400;
    topPeptideMW = 5000;
    precursorMassTolerance = 1.4;
    groupCount = 1;
    minGroupCount = 1;
    minTIC = 100;
    msLevel = 0;
  }

  public static ExtractMSNParams createLTQParams() {
    return new ExtractMSNParams(true,
                                1,
                                100000,
                                400,
                                5000,
                                1.4,
                                1,
                                1,
                                100,
                                0,
                                "",
                                "");
  }

  public static ExtractMSNParams createLCQParams() {
    return new ExtractMSNParams(true,
                                1,
                                100000,
                                500,
                                5000,
                                1.4,
                                3,
                                1,
                                100000,
                                0,
                                "",
                                "");
  }

  public ExtractMSNParams(boolean allScan,
                          int firstScan,
                          int lastScan,
                          double bottomPeptideMW,
                          double topPeptideMW,
                          double precursorMassTolerance,
                          int groupCount,
                          int minGroupCount,
                          double minTIC,
                          int msLevel,
                          String rawFilename,
                          String resultDirectory) {
    this.allScan = allScan;
    this.firstScan = firstScan;
    this.lastScan = lastScan;
    this.bottomPeptideMW = bottomPeptideMW;
    this.topPeptideMW = topPeptideMW;
    this.precursorMassTolerance = precursorMassTolerance;
    this.groupCount = groupCount;
    this.minGroupCount = minGroupCount;
    this.minTIC = minTIC;
    this.msLevel = msLevel;
    this.rawFilename = rawFilename;
    this.resultDirectory = resultDirectory;
  }

  public ExtractMSNParams(ExtractMSNParams source) {
    this.allScan = source.allScan;
    this.firstScan = source.firstScan;
    this.lastScan = source.lastScan;
    this.bottomPeptideMW = source.bottomPeptideMW;
    this.topPeptideMW = source.topPeptideMW;
    this.precursorMassTolerance = source.precursorMassTolerance;
    this.groupCount = source.groupCount;
    this.minGroupCount = source.minGroupCount;
    this.minTIC = source.minTIC;
    this.msLevel = source.msLevel;
    this.rawFilename = source.rawFilename;
    this.resultDirectory = source.resultDirectory;
  }

  public String getParamString() {
    StringBuffer sb = new StringBuffer();
    DecimalFormat df = new DecimalFormat("0.###E0");

    if (!allScan) {
      sb.append(" -F" + firstScan + " -L" + lastScan);
    }
    sb.append(" -B" + bottomPeptideMW + " -T" + topPeptideMW);
    sb.append(" -M" + precursorMassTolerance);
    sb.append(" -S" + groupCount);
    sb.append(" -G" + minGroupCount);
    sb.append(" -E" + df.format(minTIC));
    if (msLevel != 0) {
      sb.append(" -P" + msLevel);
    }
    sb.append(" -D\"" + resultDirectory + "\"");
    sb.append(" " + rawFilename);

    return sb.toString();
  }

  public String[] getExtractMSNCommand() {
    ArrayList<String> result = new ArrayList<String> ();
    DecimalFormat df = new DecimalFormat("0.###E0");

    result.add("extract_msn.exe");
    if (!allScan) {
      result.add("-F" + firstScan);
      result.add("-L" + lastScan);
    }
    result.add("-B" + bottomPeptideMW);
    result.add("-T" + topPeptideMW);
    result.add("-M" + precursorMassTolerance);
    result.add("-S" + groupCount);
    result.add("-G" + minGroupCount);
    result.add("-E" + df.format(minTIC));
    if (msLevel != 0) {
      result.add("-P" + msLevel);
    }
    result.add("-D\"" + resultDirectory + "\"");
    result.add(rawFilename);

    return result.toArray(new String[0]);
  }

  public void loadFromFile(String file) throws IOException {
    try {
      XMLFile optionFile = new XMLFile(file);
      allScan = optionFile.getElementValue("allScan", "true").equals("true");
      firstScan = Integer.parseInt(optionFile.getElementValue(
          "firstScan", "0"));
      lastScan = Integer.parseInt(optionFile.getElementValue(
          "lastScan", "100000"));
      bottomPeptideMW = Double.parseDouble(optionFile.getElementValue(
          "bottomPeptideMW", "500"));
      topPeptideMW = Double.parseDouble(optionFile.getElementValue(
          "topPeptideMW", "5000"));
      precursorMassTolerance = Double.parseDouble(optionFile.getElementValue(
          "precursorMassTolerance", "1.4"));
      groupCount = Integer.parseInt(optionFile.getElementValue(
          "groupCount", "1"));
      minGroupCount = Integer.parseInt(optionFile.getElementValue(
          "minGroupCount", "1"));
      minTIC = Integer.parseInt(optionFile.getElementValue(
          "minTIC", "1000"));
      msLevel = Integer.parseInt(optionFile.getElementValue(
          "msLevel", "0"));
      resultDirectory = optionFile.getElementValue(
          "resultDirectory", "");
      rawFilename = optionFile.getElementValue(
          "rawFilename", "");
    }
    catch (JDOMException ex) {
      throw new IOException(ex.getMessage());
    }
  }

  public void saveToFile(String file) throws IOException {
    try {
      XMLFile optionFile = new XMLFile(file);
      optionFile.setElementValue("allScan",
                                 allScan ? "true" : "false");
      optionFile.setElementValue("firstScan",
                                 Integer.toString(firstScan));
      optionFile.setElementValue("lastScan",
                                 Integer.toString(lastScan));
      optionFile.setElementValue("bottomPeptideMW",
                                 Double.toString(bottomPeptideMW));
      optionFile.setElementValue("topPeptideMW",
                                 Double.toString(topPeptideMW));
      optionFile.setElementValue("precursorMassTolerance",
                                 Double.toString(precursorMassTolerance));
      optionFile.setElementValue("groupCount",
                                 Integer.toString(groupCount));
      optionFile.setElementValue("minGroupCount",
                                 Integer.toString(minGroupCount));
      optionFile.setElementValue("minTIC",
                                 Double.toString(minTIC));
      optionFile.setElementValue("msLevel", Integer.toString(msLevel));
      optionFile.setElementValue("resultDirectory",
                                 resultDirectory);
      optionFile.setElementValue("rawFilename",
                                 rawFilename);
      optionFile.saveToFile();
    }
    catch (JDOMException ex) {
      throw new IOException(ex.getMessage());
    }
  }

  public static void main(String[] args) {
    ExtractMSNParams lcq = ExtractMSNParams.createLCQParams();
    lcq.setRawFilename("F:\\Science\\Data\\jctu\\20021122_2.RAW");
    lcq.setResultDirectory("F:\\Temp\\20021122_2");
    ShellUtils.execute(lcq.getExtractMSNCommand(), false);
  }
}
