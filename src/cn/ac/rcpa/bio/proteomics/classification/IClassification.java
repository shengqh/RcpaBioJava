package cn.ac.rcpa.bio.proteomics.classification;

public interface IClassification<E> {
  String getPrinciple();
  String getClassification(E obj);
  String[] getClassifications();
}
