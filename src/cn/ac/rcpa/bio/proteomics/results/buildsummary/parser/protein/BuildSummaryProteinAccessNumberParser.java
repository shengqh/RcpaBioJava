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
package cn.ac.rcpa.bio.proteomics.results.buildsummary.parser.protein;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;

public class BuildSummaryProteinAccessNumberParser implements
    IParser<BuildSummaryProtein> {
  IAccessNumberParser parser;
  public BuildSummaryProteinAccessNumberParser(SequenceDatabaseType dbType){
    parser = AccessNumberParserFactory.getParser(dbType);
  }

  public String getTitle() {
    return "AccessNumber";
  }

  public String getValue(BuildSummaryProtein obj) {
    return parser.getValue(obj.getProteinName());
  }

  public String getDescription() {
    return "Protein access number";
  }

}
