package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import cn.ac.rcpa.bio.proteomics.IDistributionReader;

public abstract class AbstractBuildSummaryResultExperimentalReader
    implements IDistributionReader {
  public AbstractBuildSummaryResultExperimentalReader() {
  }

  public Set<String> getExperimental(String buildSummaryResultFile) throws
      IOException {
    if (!new File(buildSummaryResultFile).exists()) {
      throw new RuntimeException("Cannot find file : " +
                                 buildSummaryResultFile);
    }

    Set<String> result = new HashSet<String> ();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryResultFile));
    try {
      //skip the first two line
      br.readLine();
      br.readLine();

      String line;
      while ( (line = br.readLine()) != null) {
        line = line.trim();
        if (line.length() == 0) {
          break;
        }

        if (line.startsWith("$") || line.startsWith("@")) {
          continue;
        }

        Matcher matcher = BuildSummaryUtils.getExperimentalPattern().matcher(line);
        if (!matcher.find()) {
          throw new RuntimeException(line + " is not a valid peptide line");
        }

        result.add(matcher.group(1));
      }
    }
    finally {
      br.close();
    }
    return result;
  }
}
