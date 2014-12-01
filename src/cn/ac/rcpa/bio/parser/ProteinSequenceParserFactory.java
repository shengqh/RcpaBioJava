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
package cn.ac.rcpa.bio.parser;

import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.parser.sequence.ProteinSequenceAccessNumberParser;
import cn.ac.rcpa.bio.parser.sequence.ProteinSequenceGravyParser;
import cn.ac.rcpa.bio.parser.sequence.ProteinSequenceMassWeightParser;
import cn.ac.rcpa.bio.parser.sequence.ProteinSequencePIParser;
import cn.ac.rcpa.bio.parser.sequence.ProteinSequenceReferenceParser;

public class ProteinSequenceParserFactory {

  public static List<IParser<Sequence>> getParsers(
      SequenceDatabaseType dbType) {
    List<IParser<Sequence>> result = new ArrayList<IParser<Sequence>>();
    result.add(new ProteinSequenceAccessNumberParser(dbType));
    result.add(new ProteinSequenceReferenceParser());
    result.add(new ProteinSequenceMassWeightParser());
    result.add(new ProteinSequencePIParser());
    result.add(new ProteinSequenceGravyParser());
    return result;
  }
}
