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
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;
import cn.ac.rcpa.bio.utils.MassCalculator;

public class ProteinSequenceMassWeightParser implements
    IParser<Sequence> {
  private DecimalFormat df = new DecimalFormat("0.0000");

  public String getTitle() {
    return "MW";
  }

  public String getValue(Sequence obj) {
    try {
      return df.format(new MassCalculator(false).getMass(obj.seqString()));
    } catch (SequenceValidateException e) {
      throw new IllegalStateException(e);
    }
  }

  public String getDescription() {
    return "Protein mass weight";
  }

}
