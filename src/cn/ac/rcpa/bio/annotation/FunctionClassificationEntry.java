package cn.ac.rcpa.bio.annotation;

public class FunctionClassificationEntry {
  private String access_number;
  private String db_object_name;
  private String aspect;
  private String classification;

  public String getAccess_number() {
    return access_number;
  }

  public String getClassification() {
    return classification;
  }

  public String getAspect() {
    return aspect;
  }

  public void setDb_object_name(String db_object_name) {
    this.db_object_name = db_object_name;
  }

  public void setAccess_number(String access_number) {
    this.access_number = access_number;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public void setAspect(String aspect) {
    this.aspect = aspect;
  }

  public String getDb_object_name() {
    return db_object_name;
  }

  public FunctionClassificationEntry(String access_number,
                                     String db_object_name,
                                     String aspect,
                                     String classification) {
    this.access_number = access_number;
    this.db_object_name = db_object_name;
    this.aspect = aspect;
    this.classification = classification;
  }

  public FunctionClassificationEntry(){
  }

}
