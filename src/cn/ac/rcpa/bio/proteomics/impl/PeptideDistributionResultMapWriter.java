package cn.ac.rcpa.bio.proteomics.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.DistributionResultMap;

public class PeptideDistributionResultMapWriter
    extends AbstractDistributionResultMapWriter {
  public PeptideDistributionResultMapWriter() {
  }

  @Override
  protected void writeHeader(PrintWriter writer, DistributionResultMap map) throws
      IOException {
    writer.print("Peptide");
    final List<String> classifiedNames = map.getClassifiedNames();
    printClassifiedNames(writer, classifiedNames, false);
  }

  @Override
  protected void writeItem(PrintWriter writer, String key, List<String> classifiedNames,
                           DistributionResultMap map) throws IOException {
    writer.print(key);
    writeCount(writer, classifiedNames, map.get(key));
  }

  @Override
  protected void writeBottom(PrintWriter writer, DistributionResultMap map) throws
      IOException {
    return;
  }
}
