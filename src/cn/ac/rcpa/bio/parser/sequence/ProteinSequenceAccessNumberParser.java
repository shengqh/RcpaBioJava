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
package cn.ac.rcpa.bio.parser.sequence;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class ProteinSequenceAccessNumberParser implements
    IParser<Sequence> {
  IAccessNumberParser parser;
  public ProteinSequenceAccessNumberParser(SequenceDatabaseType dbType){
    parser = AccessNumberParserFactory.getParser(dbType);
  }

  public String getTitle() {
    return "AccessNumber";
  }

  public String getValue(Sequence obj) {
    return parser.getValue(obj.getName());
  }

  public String getDescription() {
    return "Protein access number";
  }

}
