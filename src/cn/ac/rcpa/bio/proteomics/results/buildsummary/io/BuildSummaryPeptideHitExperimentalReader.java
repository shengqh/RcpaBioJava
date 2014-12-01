package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import cn.ac.rcpa.bio.proteomics.CountMap;
import cn.ac.rcpa.bio.proteomics.DistributionResultMap;
import cn.ac.rcpa.bio.proteomics.IDistributionReader;
import cn.ac.rcpa.bio.proteomics.StringPeptideMap;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class BuildSummaryPeptideHitExperimentalReader
    implements IDistributionReader {
  private static BuildSummaryPeptideHitExperimentalReader instance;

  public static BuildSummaryPeptideHitExperimentalReader getInstance() {
    if (instance == null) {
      instance = new BuildSummaryPeptideHitExperimentalReader();
    }
    return instance;
  }

  private BuildSummaryPeptideHitExperimentalReader() {
  }

  public Set<String> getExperimental(String buildSummaryPeptideFile) throws
      IOException {
    if (!new File(buildSummaryPeptideFile).exists()) {
      throw new RuntimeException("Cannot find corresponding peptide file : " +
                                 buildSummaryPeptideFile);
    }

    Set<String> result = new HashSet<String> ();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryPeptideFile));
    try {
      //skip the first line
      br.readLine();

      String line;
      while ( (line = br.readLine()) != null) {
        if (line.trim().length() == 0) {
          break;
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

  public DistributionResultMap getPeptideExperimentalMap(List<String> peptides) {
    DistributionResultMap result = new DistributionResultMap();

    for (String line : peptides) {
      Matcher matcher = BuildSummaryUtils.getExperimentalPeptidePattern().matcher(line);
      if (!matcher.find()) {
        throw new RuntimeException(line + " is not a valid peptide line");
      }
      result.increase(PeptideUtils.getPurePeptideSequence(matcher.group(2)),
                      matcher.group(1));
    }

    return result;
  }

  /**
   * 从Peptides文件中读取Peptide/Experimental以及该肽段在该Experimental中鉴定的次数
   *
   * @param buildSummaryPeptideFile String
   * @return Map<PeptidePureSequence, Map<ExperimentalName, Count>>
   * @throws IOException
   */
  public DistributionResultMap getExperimentalMap(String
                                                  buildSummaryPeptideFile) throws
      IOException {
    if (!new File(buildSummaryPeptideFile).exists()) {
      throw new RuntimeException("Cannot find corresponding peptide file : " +
                                 buildSummaryPeptideFile);
    }

    DistributionResultMap result = new DistributionResultMap();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryPeptideFile));
    try {
      //skip the first line
      br.readLine();

      String line;
      while ( (line = br.readLine()) != null) {
        if (line.trim().length() == 0) {
          break;
        }

        Matcher matcher = BuildSummaryUtils.getExperimentalPeptidePattern().matcher(line);
        if (!matcher.find()) {
          throw new RuntimeException(line + " is not a valid peptide line");
        }

        final String pureSeq = PeptideUtils.getPurePeptideSequence(matcher.
            group(2));
        final String experimental = matcher.group(1);
        if (!result.containsKey(pureSeq)) {
          result.put(pureSeq, new CountMap<String> ());
        }

        result.get(pureSeq).increase(experimental);
      }
    }
    finally {
      br.close();
    }
    return result;
  }

  /**
   * 从Peptides文件中读取Peptide/PeptideIsoform/Experimental以及PeptideIsoform在该Experimental中鉴定的次数
   *
   * @param buildSummaryPeptideFile String
   * @return Map<PeptidePureSequence, Map<PeptideIsoform, Map<ExperimentalName, Count>>>
   * @throws IOException
   */
  public StringPeptideMap getExperimentalDetailMap(String buildSummaryPeptideFile) throws
      IOException {
    if (!new File(buildSummaryPeptideFile).exists()) {
      throw new RuntimeException("Cannot find corresponding peptide file : " +
                                 buildSummaryPeptideFile);
    }

    StringPeptideMap result = new StringPeptideMap ();
    BufferedReader br = new BufferedReader(new FileReader(
        buildSummaryPeptideFile));
    try {
      //skip the first line
      br.readLine();

      String line;
      while ( (line = br.readLine()) != null) {
        if (line.trim().length() == 0) {
          break;
        }

        Matcher matcher = BuildSummaryUtils.getExperimentalPeptidePattern().matcher(line);
        if (!matcher.find()) {
          throw new RuntimeException(line + " is not a valid peptide line");
        }

        final String peptideSeq = matcher.group(2);
        final String pureSeq = PeptideUtils.getPurePeptideSequence(peptideSeq);
        final String experimental = matcher.group(1);

        result.increase(pureSeq, peptideSeq, experimental);
      }
    }
    finally {
      br.close();
    }
    return result;
  }
}
