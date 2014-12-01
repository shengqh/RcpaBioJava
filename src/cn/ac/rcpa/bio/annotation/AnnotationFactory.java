package cn.ac.rcpa.bio.annotation;

import java.sql.SQLException;

import cn.ac.rcpa.bio.annotation.impl.GOAnnotationQueryByGO;
import cn.ac.rcpa.bio.annotation.impl.GOAnnotationQueryByGOEmptyResult;
import cn.ac.rcpa.bio.annotation.impl.GOAnnotationQueryByGOSlim;
import cn.ac.rcpa.bio.annotation.impl.GOAnnotationQueryEmptyResult;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;

public class AnnotationFactory {
  private AnnotationFactory() {
  }

  public static IAnnotationQueryByGO getAnnotationQueryByGO(SequenceDatabaseType dbType) throws
      ClassNotFoundException, SQLException {
    if (dbType.equals(SequenceDatabaseType.IPI)) {
      return new GOAnnotationQueryByGO("synonym");
    }
    else if (dbType.equals(SequenceDatabaseType.SWISSPROT)) {
      return new GOAnnotationQueryByGO("db_object_symbol");
    }
    else {
      return GOAnnotationQueryByGOEmptyResult.getInstance();
    }
  }

  public static IAnnotationQuery getAnnotationQuery(SequenceDatabaseType dbType) throws
      ClassNotFoundException, SQLException {
    if (dbType.equals(SequenceDatabaseType.IPI)) {
      return new GOAnnotationQueryByGOSlim("synonym");
    }
    else if (dbType.equals(SequenceDatabaseType.SWISSPROT)) {
      return new GOAnnotationQueryByGOSlim("db_object_id");
    }
    else {
      return GOAnnotationQueryEmptyResult.getInstance();
    }
  }
}
