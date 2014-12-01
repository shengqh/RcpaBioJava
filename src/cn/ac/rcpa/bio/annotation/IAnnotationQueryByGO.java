package cn.ac.rcpa.bio.annotation;

import java.sql.SQLException;

public interface IAnnotationQueryByGO {
  GOAClassificationEntry getAnnotation(String rootGO, int level, String[] acNumbers)throws SQLException ;
  void fillAnnotation(GOAClassificationEntry rootEntry, String[] acNumbers)throws SQLException ;
}
