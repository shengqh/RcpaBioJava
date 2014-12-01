package cn.ac.rcpa.bio.proteomics.io;

import java.io.IOException;
import java.io.PrintWriter;

import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;

public interface IIdentifiedResultWriter<E extends IIdentifiedResult> {
  void write(String filename, E identifiedResult) throws IOException;

  void write(PrintWriter writer, E identifiedResult) throws IOException;
}
