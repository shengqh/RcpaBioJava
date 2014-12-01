package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.io.PrintWriter;

public interface IDistributionResultMapWriter {
  void write(String filename, DistributionResultMap map) throws IOException;
  void write(PrintWriter writer, DistributionResultMap map) throws IOException;
}
