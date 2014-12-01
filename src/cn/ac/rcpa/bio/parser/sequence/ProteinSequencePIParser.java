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

import java.text.DecimalFormat;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.utils.IsoelectricPointCalculator;

public class ProteinSequencePIParser implements IParser<Sequence> {
  private DecimalFormat df = new DecimalFormat("0.0000");

  public String getTitle() {
    return "PI";
  }

  public String getValue(Sequence obj) {
    return df.format(IsoelectricPointCalculator.getPI(obj.seqString()));
  }

  public String getDescription() {
    return "Protein isoelectric point";
  }

}
