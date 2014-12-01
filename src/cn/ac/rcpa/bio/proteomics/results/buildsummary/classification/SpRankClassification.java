package cn.ac.rcpa.bio.proteomics.results.buildsummary.classification;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.classification.impl.AbstractDiscreteClassification;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;

public class SpRankClassification extends AbstractDiscreteClassification<IIdentifiedPeptide, Integer>{
  public SpRankClassification() {
  }

  @Override
  protected Integer doGetClassification(IIdentifiedPeptide obj) {
    return ((BuildSummaryPeptide)obj).getSpRank();
  }

  public String getPrinciple() {
    return "SpRank";
  }
}
