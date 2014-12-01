package cn.ac.rcpa.bio.proteomics.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.bio.proteomics.DistributionResultMap;

public class ProteinDistributionResultMapWriter
    extends AbstractDistributionResultMapWriter {
  private IAccessNumberParser acParser;

  public ProteinDistributionResultMapWriter(SequenceDatabaseType dbType) {
    this.acParser = AccessNumberParserFactory.getParser(dbType);
  }

  @Override
  protected void writeHeader(PrintWriter writer, DistributionResultMap map) throws
      IOException {
    writer.print("Protein\tReference");
    final List<String> classifiedNames = map.getClassifiedNames();
    printClassifiedNames(writer, classifiedNames, false);
  }

  private String getAccessNumbers(String key) {
    String result = null;

    final String[] proteins = key.split(" ! ");
    for(String protein : proteins){
      final String acNumber = acParser.getValue(protein);
      if (result == null){
        result = acNumber;
      }
      else {
        result = result + " ! " + acNumber;
      }
    }

    return result;
  }

  @Override
  protected void writeItem(PrintWriter writer, String key, List<String> classifiedNames,
                           DistributionResultMap map) throws IOException {
    final String acNumbers = getAccessNumbers(key);
    writer.print(acNumbers + "\t" + key);
    writeCount(writer, classifiedNames, map.get(key));
  }

  @Override
  protected void writeBottom(PrintWriter writer, DistributionResultMap map) throws
      IOException {
    return;
  }
}
