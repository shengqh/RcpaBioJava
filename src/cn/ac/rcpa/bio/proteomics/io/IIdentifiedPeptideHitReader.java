package cn.ac.rcpa.bio.proteomics.io;

import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;

public interface IIdentifiedPeptideHitReader<E extends IIdentifiedPeptideHit> {
  List<E> read(String filename) throws Exception;
}
