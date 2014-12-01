package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

public class IdentifiedPeptideComparator<T extends IIdentifiedPeptide> implements Comparator<T>{
  public IdentifiedPeptideComparator(){
  }

  public int compare(T o1, T o2) {
    int result = o1.getSequence().compareTo(o2.getSequence());
    if (result == 0){
      result = PeakListInfoComparator.getInstance().compare(o1.getPeakListInfo(), o2.getPeakListInfo());
    }

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }
}
