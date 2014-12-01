package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.sequest.SequestOutFile;
import cn.ac.rcpa.filter.IFilter;

public class BuildSummaryUtils {
  private static Pattern experimentalPattern;
  private static Pattern experimentalPeptidePattern;
  
  private BuildSummaryUtils() {
  }
  
  public static Pattern getExperimentalPattern() {
    if (experimentalPattern == null) {
      experimentalPattern = Pattern.compile("\\t*\"{0,1}(.+?),\\d+\"{0,1}");
    }
    return experimentalPattern;
  }
  
  public static Pattern getExperimentalPeptidePattern() {
    if (experimentalPeptidePattern == null) {
      experimentalPeptidePattern = Pattern.compile(
          "\\t*\"{0,1}(.+),[\\d|\\x20|-]+\"{0,1}\\t([(?=\\S\\.\\S+\\.\\S)|\\x20|!]+)\\t");
    }
    return experimentalPeptidePattern;
  }
  
  public static BuildSummaryPeptide[] getPeptideHit(String[] dirList,
                                                  IFilter<IIdentifiedPeptide> pepFilter) throws
      IOException {
    List<BuildSummaryPeptide> result = new ArrayList<BuildSummaryPeptide>();

    for (int i = 0; i < dirList.length; i++) {
      final File dir = new File(dirList[i]);
      if (!dir.exists() || !dir.isDirectory()){
        throw new FileNotFoundException("Cannot find directory " + dirList[i]);
      }

      File[] outFiles = dir.listFiles(new FileFilter(){
        public boolean accept(File pathname) {
          return pathname.getAbsolutePath().toLowerCase().endsWith(".out");
        }
      });

      for (int j = 0; j < outFiles.length; j++) {
        SequestOutFile outfile = SequestOutFile.read(outFiles[j].getAbsolutePath(),1);
        if (outfile.getPeptideHitCount() > 0 && pepFilter.accept(outfile.getPeptideHit(0))){
          result.add(outfile.getPeptideHit(0));
        }
      }
    }

    return result.toArray(new BuildSummaryPeptide[0]);
  }

  public static boolean isTrueProteinLine(String line) {
    final String trimLine = line.trim();
    return trimLine.startsWith("$");
  }

  public static boolean isSubProteinLine(String line) {
    final String trimLine = line.trim();
    return trimLine.startsWith("@");
  }

  public static String getProteinLines(BufferedReader br, List<String> proteins,
                                       String lastLine) throws IOException {
    proteins.clear();
    if (BuildSummaryUtils.isTrueProteinLine(lastLine)) {
      proteins.add(lastLine);
    }
    String result;
    while ( (result = br.readLine()) != null) {
      if (BuildSummaryUtils.isTrueProteinLine(result)) {
        proteins.add(result);
      }
      else if (BuildSummaryUtils.isSubProteinLine(result)) {
        continue;
      }
      else {
        break;
      }
    }
    return result;
  }

  public static String getPeptideLines(BufferedReader br, List<String> peptides,
                                       String lastLine) throws IOException {
    peptides.clear();
    if (!isTrueProteinLine(lastLine) && !isSubProteinLine(lastLine) &&
        (lastLine.length() > 0)) {
      peptides.add(lastLine);
    }

    String result;
    while ( (result = br.readLine()) != null) {
      if (result.trim().length() == 0) {
        break;
      }

      if (!isTrueProteinLine(result) && !isSubProteinLine(result)) {
        peptides.add(result);
      }
      else {
        break;
      }
    }
    return result;
  }

  public static  List<String> getProteinReferences(List<String> proteinLines) {
    List<String> result = new ArrayList<String> ();
    for (String protein : proteinLines) {
      String[] lines = protein.split("\t");
      if (lines.length < 2) {
        throw new RuntimeException(protein + " is not a valid protein line");
      }

      result.add(lines[1]);
    }
    return result;
  }
}

