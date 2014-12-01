package cn.ac.rcpa.bio.annotation;

public interface IAnnotationQuery {
  FunctionClassificationEntry[] getAnnotation(String acNumber);
  FunctionClassificationEntryMap getAnnotation(String[] acNumbers);
}
