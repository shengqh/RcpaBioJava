package cn.ac.rcpa.bio.proteomics.annotator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;

public interface IIdentifiedResultAnnotator<E extends IIdentifiedResult> {
  public void addAnnotation(E ir);
}
