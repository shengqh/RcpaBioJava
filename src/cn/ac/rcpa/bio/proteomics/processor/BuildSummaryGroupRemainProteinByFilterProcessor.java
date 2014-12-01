package cn.ac.rcpa.bio.proteomics.processor;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.filter.IFilter;

public class BuildSummaryGroupRemainProteinByFilterProcessor
    extends
    IdentifiedProteinGroupRemainProteinByFilterProcessor<BuildSummaryPeptide,
    BuildSummaryPeptideHit, BuildSummaryProtein, BuildSummaryProteinGroup> {
  public BuildSummaryGroupRemainProteinByFilterProcessor(
      IFilter<BuildSummaryProtein> proFilter) {
    super(proFilter);
  }
}
