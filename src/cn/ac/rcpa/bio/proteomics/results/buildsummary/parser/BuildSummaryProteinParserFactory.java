/*
 * Created on 2005-12-30
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
import java.util.List;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein.BuildSummaryProteinAccessNumberParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein.BuildSummaryProteinGravyParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein.BuildSummaryProteinGroupIndexParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein.BuildSummaryProteinMassWeightParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein.BuildSummaryProteinPIParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein.BuildSummaryProteinReferenceParser;

public class BuildSummaryProteinParserFactory {

  public static List<IParser<BuildSummaryProtein>> getParsers(
      SequenceDatabaseType dbType) {
    List<IParser<BuildSummaryProtein>> result = new ArrayList<IParser<BuildSummaryProtein>>();
    result.add(new BuildSummaryProteinGroupIndexParser());
    result.add(new BuildSummaryProteinAccessNumberParser(dbType));
    result.add(new BuildSummaryProteinReferenceParser());
    result.add(new BuildSummaryProteinMassWeightParser());
    result.add(new BuildSummaryProteinPIParser());
    result.add(new BuildSummaryProteinGravyParser());
    return result;
  }
}
