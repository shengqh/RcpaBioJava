package cn.ac.rcpa.bio.proteomics.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.DistributionResultMap;
import cn.ac.rcpa.bio.proteomics.IDistributionResultMapWriter;

public abstract class AbstractDistributionResultMapWriter
    implements IDistributionResultMapWriter {
  public AbstractDistributionResultMapWriter() {
  }

  public void write(String filename, DistributionResultMap map) throws IOException {
    PrintWriter pw = new PrintWriter(new File(filename));
    try {
      write(pw, map);
    }
    finally{
      pw.close();
    }
  }

  protected void printClassifiedNames(PrintWriter writer,
                                      List<String> classifiedNames,
                                      boolean showRank) throws IOException {
    for (int i = 0; i < classifiedNames.size(); i++) {
      writer.print("\t" + classifiedNames.get(i));
      if (showRank) {
        writer.print("\t" + classifiedNames.get(i) + "_rank");
      }
    }
  }

  public void write(PrintWriter writer, DistributionResultMap map) throws
      IOException {
    writeHeader(writer, map);
    writer.println();
    writeItems(writer, map);
    writer.println();
    writeBottom(writer, map);
    writer.println();
  }

  protected void writeCount(PrintWriter writer,
                          List<String> classifiedNames,
                          Map<String, Integer> countMap) throws IOException {
    for (String classifiedName : classifiedNames) {
      Integer count = countMap.get(classifiedName);
      if (count == null){
        writer.print("\t0");
      }
      else{
        writer.print("\t" + countMap.get(classifiedName));
      }
    }
  }

  private void writeItems(PrintWriter writer, DistributionResultMap map) throws
      IOException {
    final List<String> classifiedNames = map.getClassifiedNames();
    final List<String> sortedKeys = map.getSortedKeys();
    for (String key : sortedKeys) {
      writeItem(writer, key, classifiedNames, map);
      writer.println();
    }
  }

  protected abstract void writeHeader(PrintWriter writer, DistributionResultMap map) throws
      IOException;

  protected abstract void writeItem(PrintWriter writer, String key, List<String> classifiedNames, DistributionResultMap map) throws IOException;

  protected abstract void writeBottom(PrintWriter writer, DistributionResultMap map) throws IOException;
}
