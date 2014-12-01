package cn.ac.rcpa.bio.proteomics.io;

import java.io.IOException;
import java.util.Collection;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;

public interface IIdentifiedPeptideHitWriter<E extends IIdentifiedPeptideHit> {
  void write(String filename, Collection<E> peptideHits) throws IOException;
}
