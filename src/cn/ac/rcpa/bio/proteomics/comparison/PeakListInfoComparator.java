package cn.ac.rcpa.bio.proteomics.comparison;

import java.util.Comparator;

import cn.ac.rcpa.bio.proteomics.IPeakListInfo;

public class PeakListInfoComparator implements Comparator{
  private static PeakListInfoComparator instance;

  public static PeakListInfoComparator getInstance(){
    if (instance == null){
      instance = new PeakListInfoComparator();
    }
    return instance;
  }

  private PeakListInfoComparator(){
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public int compare(Object o1, Object o2) {
    IPeakListInfo pk1 = (IPeakListInfo)o1;
    IPeakListInfo pk2 = (IPeakListInfo)o2;

    int result = pk2.getExperiment() == null ? 0 :
        pk1.getExperiment().compareTo(pk2.getExperiment());

    if (result == 0) {
      result = pk1.getFirstScan() - pk2.getFirstScan();
    }

    if (result == 0) {
      result = pk1.getLastScan() - pk2.getLastScan();
    }

    if (result == 0) {
      result = pk1.getCharge() - pk2.getCharge();
    }

    return result;
  }
}
