package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;

public interface ISequestProteinGroupWriter {
  void write(String filename, BuildSummaryProteinGroup[] proteins) throws Exception;
}
