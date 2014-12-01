/*
 * Created on 2005-12-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitChargeParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitDeltaCnParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitDiffMHParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitDiffModifiedCandidatesParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitFileScanParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitGravyParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitIonsParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitMHParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitMWParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitPIParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitRSpParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitRankParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitReferencesParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitSequenceParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitSpParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.peptidehit.BuildSummaryPeptideHitXCorrParser;

public class BuildSummaryPeptideHitParserFactory {
  private static List<IParser<BuildSummaryPeptideHit>> parsers;
  static {
    parsers = new ArrayList<IParser<BuildSummaryPeptideHit>>();
    parsers.add(new BuildSummaryPeptideHitFileScanParser());
    parsers.add(new BuildSummaryPeptideHitSequenceParser());
    parsers.add(new BuildSummaryPeptideHitMHParser());
    parsers.add(new BuildSummaryPeptideHitDiffMHParser());
    parsers.add(new BuildSummaryPeptideHitChargeParser());
    parsers.add(new BuildSummaryPeptideHitRankParser());
    parsers.add(new BuildSummaryPeptideHitXCorrParser());
    parsers.add(new BuildSummaryPeptideHitDeltaCnParser());
    parsers.add(new BuildSummaryPeptideHitSpParser());
    parsers.add(new BuildSummaryPeptideHitRSpParser());
    parsers.add(new BuildSummaryPeptideHitIonsParser());
    parsers.add(new BuildSummaryPeptideHitReferencesParser());
    parsers.add(new BuildSummaryPeptideHitDiffModifiedCandidatesParser());
    parsers.add(new BuildSummaryPeptideHitMWParser());
    parsers.add(new BuildSummaryPeptideHitPIParser());
    parsers.add(new BuildSummaryPeptideHitGravyParser());
  };

  public static List<IParser<BuildSummaryPeptideHit>> getParsers() {
    return Collections.unmodifiableList(parsers);
  }
}
