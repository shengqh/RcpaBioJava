package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildSummaryResultGroupInfoReader {
  public BuildSummaryResultGroupInfoReader() {
  }

  private static final Pattern groupPattern = Pattern.compile(
      "[$|@](\\d+)-");

  private int getGroupIndex(String string) {
    Matcher matcher = groupPattern.matcher(string);
    matcher.find();
    return Integer.parseInt(matcher.group(1));
  }

  public LinkedHashMap<Integer,
      Boolean> read(String buildSummaryResultFile) throws FileNotFoundException,
      IOException {
    final File resultFile = new File(buildSummaryResultFile);
    if (!resultFile.exists()) {
      throw new RuntimeException("Cannot find file : " +
                                 buildSummaryResultFile);
    }

    System.out.print("Reading group info - " + resultFile.getName() + " ...");

    LinkedHashMap<Integer, Boolean> result = new LinkedHashMap<Integer, Boolean> ();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryResultFile));
    try {
      //skip the first two line
      br.readLine();
      br.readLine();

      String line = "";
      while (null != (line = br.readLine())) {
        if (line.trim().length() == 0){
          break;
        }

        boolean isTrueProteinLine = line.startsWith("$");
        boolean isSubProteinLine = line.startsWith("@");
        if (!isTrueProteinLine && !isSubProteinLine){
          continue;
        }

        int getGroupIndex = getGroupIndex(line);
        result.put(getGroupIndex, isTrueProteinLine);
      }
    }
    finally {
      br.close();
    }

    System.out.println(" Finished");
    return result;
  }
}
