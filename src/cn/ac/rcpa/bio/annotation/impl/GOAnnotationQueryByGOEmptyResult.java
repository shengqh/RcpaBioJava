package cn.ac.rcpa.bio.annotation.impl;

import java.sql.SQLException;

import cn.ac.rcpa.bio.annotation.GOAClassificationEntry;
import cn.ac.rcpa.bio.annotation.IAnnotationQueryByGO;

public class GOAnnotationQueryByGOEmptyResult
    implements IAnnotationQueryByGO {
  private static GOAnnotationQueryByGOEmptyResult instance;

  public static GOAnnotationQueryByGOEmptyResult getInstance() {
    if (instance == null) {
      instance = new GOAnnotationQueryByGOEmptyResult();
    }
    return instance;
  }

  private GOAnnotationQueryByGOEmptyResult() {
  }

  public GOAClassificationEntry getAnnotation(String rootGO, int level,
                                              String[] acNumbers) throws
      SQLException {
    GOAClassificationEntry result = new GOAClassificationEntry();

    result.setAccession(rootGO);

    return result;
  }

  public void fillAnnotation(GOAClassificationEntry rootEntry,
                             String[] acNumbers) throws SQLException {
    return;
  }
}
