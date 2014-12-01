/*
 * Created on 2005-6-28
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;

public class BuildSummaryResultProteinOnlyReader
    implements IIdentifiedResultReader<BuildSummaryResult> {
  private static BuildSummaryResultProteinOnlyReader instance = null;

  public static BuildSummaryResultProteinOnlyReader getInstance() {
    if (instance == null) {
      instance = new BuildSummaryResultProteinOnlyReader();
    }
    return instance;
  }

  private BuildSummaryResultProteinOnlyReader() {
  }

  private final static String proteinPatternStr =
      "\\$(\\d+)-\\d+\\s+(.*?)\\s+\\d+\\s+\\d+\\s+(\\S+)%\\s+(\\S+)\\s+(\\S+)";

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

  public BuildSummaryResult readOnly(String filename) throws IOException {
    BuildSummaryResult result = new BuildSummaryResult();

    System.out.println("Reading " + filename + " ...");
    BufferedReader br = new BufferedReader(new FileReader(filename));
    try {
      String line;
      while ( (line = br.readLine()) != null) {
        if (line.startsWith("$")) {
          break;
        }
      }

      Map<Integer,
          BuildSummaryProteinGroup> indexGroupMap = new HashMap<Integer,
          BuildSummaryProteinGroup> ();
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
            indexGroupMap.put(protein.getGroup(), group);
          }

          group.addProtein(protein);
        }
        else if (line.startsWith("@")) {
          int parentGroupIndex = getParentGroup(line);
          BuildSummaryProteinGroup parentGroup = indexGroupMap.get(
              parentGroupIndex);
          if (parentGroup != null) {
            group.addParent(parentGroup);
          }
        }
        else {
          bParsingProtein = false;
        }

        line = br.readLine();
      }

      if (group.getProteinCount() != 0) {
        result.addProteinGroup(group);
      }
    }
    finally {
      br.close();
    }

    return result;
  }

  private int getParentGroup(String line) {
    Matcher matcher = getContainProteinPattern().matcher(line);
    if (!matcher.find()) {
      throw new IllegalStateException(line +
                                      " is not a valid redundant protein line");
    }

    return Integer.parseInt(matcher.group(1));
  }

  public BuildSummaryResult read(String filename) throws IOException {
    return readOnly(filename);
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
