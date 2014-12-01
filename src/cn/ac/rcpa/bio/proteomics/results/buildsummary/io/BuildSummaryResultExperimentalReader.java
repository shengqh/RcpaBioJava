package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import cn.ac.rcpa.bio.proteomics.CountMap;
import cn.ac.rcpa.bio.proteomics.DistributionResultMap;
import cn.ac.rcpa.bio.proteomics.StringPeptideMap;

public class BuildSummaryResultExperimentalReader
    extends AbstractBuildSummaryResultExperimentalReader {
  private static BuildSummaryResultExperimentalReader instance;

  public static BuildSummaryResultExperimentalReader getInstance() {
    if (instance == null) {
      instance = new BuildSummaryResultExperimentalReader();
    }
    return instance;
  }

  private BuildSummaryResultExperimentalReader() {
  }

  /**
   * 从Protein文件中读取Protein/Experimental以及该Protein在该Experimental中鉴定的次数
   *
   * @param buildSummaryResultFile String
   * @return DistributionResultMap
   * @throws IOException
   */
  public DistributionResultMap getExperimentalMap(String
                                                  buildSummaryResultFile) throws
      IOException {
    final File resultFile = new File(buildSummaryResultFile);
    if (!resultFile.exists()) {
      throw new RuntimeException("Cannot find file : " +
                                 buildSummaryResultFile);
    }

    System.out.print("Reading experimental map - " + resultFile.getName() +
                     " ...");

    DistributionResultMap result = new DistributionResultMap();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryResultFile));
    try {
      //skip the first two line
      br.readLine();
      br.readLine();

      String lastLine = "";
      List<String> proteinLines = new ArrayList<String> ();
      List<String> peptideLines = new ArrayList<String> ();
      while (true) {
        lastLine = BuildSummaryUtils.getProteinLines(br, proteinLines, lastLine);

        if (proteinLines.size() == 0) {
          break;
        }

        final List<String> proteinReferences = BuildSummaryUtils.getProteinReferences(
            proteinLines);

        lastLine = BuildSummaryUtils.getPeptideLines(br, peptideLines, lastLine);

        final CountMap<String> experimentalMap = getExperimentalMap(
            peptideLines);

        result.put(StringUtils.join(proteinReferences.iterator(), " ! "),
                   experimentalMap);

        if (lastLine == null) {
          break;
        }
      }
    }
    finally {
      br.close();
    }

    System.out.println(" Finished");
    return result;
  }

  private CountMap<String> getExperimentalMap(List<String> peptides) {
    CountMap<String> result = new CountMap<String> ();

    for (String line : peptides) {
      Matcher matcher = BuildSummaryUtils.getExperimentalPattern().matcher(line);
      if (!matcher.find()) {
        throw new RuntimeException(line + " is not a valid peptide line");
      }
      result.increase(matcher.group(1));
    }

    return result;
  }

  public StringPeptideMap getExperimentalDetailMap(String
      buildSummaryResultFile) throws
      IOException {
    final File resultFile = new File(buildSummaryResultFile);
    if (!resultFile.exists()) {
      throw new RuntimeException("Cannot find file : " +
                                 buildSummaryResultFile);
    }

    System.out.print("Reading experimental detail map - " + resultFile.getName() +
                     " ...");

    StringPeptideMap result = new StringPeptideMap();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryResultFile));
    try {
      //skip the first two line
      br.readLine();
      br.readLine();

      String lastLine = "";
      List<String> proteinLines = new ArrayList<String> ();
      List<String> peptideLines = new ArrayList<String> ();
      while (true) {
        lastLine = BuildSummaryUtils.getProteinLines(br, proteinLines, lastLine);

        if (proteinLines.size() == 0) {
          break;
        }

        final List<String> proteinReferences = BuildSummaryUtils.getProteinReferences(
            proteinLines);

        lastLine = BuildSummaryUtils.getPeptideLines(br, peptideLines, lastLine);

        final DistributionResultMap experimentalMap =
            BuildSummaryPeptideHitExperimentalReader.getInstance().
            getPeptideExperimentalMap(peptideLines);

        result.put(StringUtils.join(proteinReferences.iterator(), " ! "),
                   experimentalMap);

        if (lastLine == null) {
          break;
        }
      }
    }
    finally {
      br.close();
    }

    System.out.println(" Finished");
    return result;
  }
}
