package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;

public class IdentifiedPeptideHitComparator implements
    Comparator<IIdentifiedPeptideHit< ? extends IIdentifiedPeptide>> {
  private static IdentifiedPeptideHitComparator instance;

  public static IdentifiedPeptideHitComparator getInstance() {
    if (instance == null) {
      instance = new IdentifiedPeptideHitComparator();
    }
    return instance;
  }

  private IdentifiedPeptideHitComparator() {
  }

  public int compare(IIdentifiedPeptideHit< ? extends IIdentifiedPeptide> o1,
      IIdentifiedPeptideHit< ? extends IIdentifiedPeptide> o2) {
    int result = o1.getPeptide(0).getSequence().compareTo(
        o2.getPeptide(0).getSequence());
    if (result == 0) {
      result = PeakListInfoComparator.getInstance().compare(
          o1.getPeakListInfo(), o2.getPeakListInfo());
    }

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }
}
