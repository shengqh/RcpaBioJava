package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;

public interface ISequestProteinGroupReader {
  BuildSummaryProteinGroup[] read(String filename) throws Exception;
}
