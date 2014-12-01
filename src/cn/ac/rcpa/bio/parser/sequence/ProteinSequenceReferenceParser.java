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

public class ProteinSequenceReferenceParser implements
    IParser<Sequence> {

  public String getTitle() {
    return "Reference";
  }

  public String getValue(Sequence obj) {
    return ((String)obj.getAnnotation().getProperty("description_line")).trim();
  }

  public String getDescription() {
    return "Protein reference";
  }

}
