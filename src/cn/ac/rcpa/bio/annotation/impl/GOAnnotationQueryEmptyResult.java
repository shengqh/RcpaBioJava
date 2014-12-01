package cn.ac.rcpa.bio.annotation.impl;

import cn.ac.rcpa.bio.annotation.FunctionClassificationEntry;
import cn.ac.rcpa.bio.annotation.FunctionClassificationEntryMap;
import cn.ac.rcpa.bio.annotation.IAnnotationQuery;

public class GOAnnotationQueryEmptyResult
    implements IAnnotationQuery {

  private static GOAnnotationQueryEmptyResult instance;

  public static GOAnnotationQueryEmptyResult getInstance() {
    if (instance == null) {
      instance = new GOAnnotationQueryEmptyResult();
    }
    return instance;
  }

  private GOAnnotationQueryEmptyResult() {
  }

  /**
   * getAnnotation
   *
   * @param acNumbers String[]
   * @return FunctionClassificationEntryMap
   */
  public FunctionClassificationEntryMap getAnnotation(String[] acNumbers) {
    return new FunctionClassificationEntryMap();
  }

  /**
   * getAnnotation
   *
   * @param acNumber String
   * @return FunctionClassificationEntry[]
   */
  public FunctionClassificationEntry[] getAnnotation(String acNumber) {
    return new FunctionClassificationEntry[0];
  }

}
