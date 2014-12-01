package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.annotator.IIdentifiedResultAnnotator;
import cn.ac.rcpa.bio.proteomics.io.impl.FastaResultWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideHitReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideHitWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultWriter;
import cn.ac.rcpa.bio.proteomics.results.dtaselect.DTASelectParams;
import cn.ac.rcpa.bio.proteomics.results.dtaselect.io.DtaSelectWriter;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class IdentifiedResultIOFactory {
  private IdentifiedResultIOFactory() {
  }

  public static BuildSummaryPeptideHitReader getBuildSummaryPeptideHitReader(){
    return new BuildSummaryPeptideHitReader();
  }

  public static BuildSummaryResultReader getBuildSummaryResultReader(){
    return getBuildSummaryResultReader(null);
  }

  public static BuildSummaryResultReader getBuildSummaryResultReader(IIdentifiedResultAnnotator<BuildSummaryResult> annotator){
    return new BuildSummaryResultReader(annotator);
  }

  public static BuildSummaryResultWriter getBuildSummaryResultWriter(){
    return BuildSummaryResultWriter.getInstance();
  }

  public static DtaSelectWriter getDtaSelectWriter(DTASelectParams params){
    return new DtaSelectWriter(params);
  }

  public static FastaResultWriter getFastaWriter(){
    return FastaResultWriter.getInstance();
  }

  public static BuildSummaryResult readBuildSummaryResult(String resultFile) throws
      IOException, SequestParseException {
    return new BuildSummaryResultReader().read(resultFile);
  }

  public static BuildSummaryResult readBuildSummaryResult(String resultFile,IIdentifiedResultAnnotator<BuildSummaryResult> annotator) throws
      IOException, SequestParseException {
    return new BuildSummaryResultReader(annotator).read(resultFile);
  }

  public static void writeBuildSummaryResult(String resultFile, BuildSummaryResult ir) throws
      IOException {
    BuildSummaryResultWriter.getInstance().write(resultFile, ir);
  }

  /**
   * @param resultFile String
   * @return List
   * @throws Exception 
   */
  public static List<BuildSummaryPeptideHit> readBuildSummaryPeptideHit(String resultFile)throws
      Exception {
    return new BuildSummaryPeptideHitReader().read(resultFile);
  }

  public static void writeBuildSummaryPeptideHit(String resultFile, List<BuildSummaryPeptideHit> peptides)throws
      IOException {
    new BuildSummaryPeptideHitWriter().write(resultFile, peptides);
  }

}
