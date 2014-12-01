package cn.ac.rcpa.bio.annotation;

public class GOAnnotationEntry {
  private String db;
  private String db_object_id;
  private String db_object_symbol;
  private String qualifier;
  private String goid;
  private String db_reference;
  private String evidence;
  private String with;
  private String aspect;
  private String db_object_name;
  private String synonym;
  private String db_object_type;
  private String taxon_ID;
  private String date;
  private String assigned_By;

  public String getQualifier() {
    return qualifier;
  }

  public String getDb_object_type() {
    return db_object_type;
  }

  public String getAspect() {
    return aspect;
  }

  public String getDb() {
    return db;
  }

  public String getTaxon_ID() {
    return taxon_ID;
  }

  public String getDb_object_name() {
    return db_object_name;
  }

  public String getAssigned_By() {
    return assigned_By;
  }

  public String getSynonym() {
    return synonym;
  }

  public String getDb_reference() {
    return db_reference;
  }

  public String getGoid() {
    return goid;
  }

  public String getDb_object_symbol() {
    return db_object_symbol;
  }

  public String getDate() {
    return date;
  }

  public String getWith() {
    return with;
  }

  public String getEvidence() {
    return evidence;
  }

  public void setDb_object_id(String db_object_id) {
    this.db_object_id = db_object_id;
  }

  public void setQualifier(String qualifier) {
    this.qualifier = qualifier;
  }

  public void setDb_object_type(String db_object_type) {
    this.db_object_type = db_object_type;
  }

  public void setAspect(String aspect) {
    this.aspect = aspect;
  }

  public void setDb(String db) {
    this.db = db;
  }

  public void setTaxon_ID(String taxon_ID) {
    this.taxon_ID = taxon_ID;
  }

  public void setDb_object_name(String db_object_name) {
    this.db_object_name = db_object_name;
  }

  public void setAssigned_By(String assigned_By) {
    this.assigned_By = assigned_By;
  }

  public void setSynonym(String synonym) {
    this.synonym = synonym;
  }

  public void setDb_reference(String db_reference) {
    this.db_reference = db_reference;
  }

  public void setGoid(String goid) {
    this.goid = goid;
  }

  public void setDb_object_symbol(String db_object_symbol) {
    this.db_object_symbol = db_object_symbol;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setWith(String with) {
    this.with = with;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public String getDb_object_id() {
    return db_object_id;
  }

  public GOAnnotationEntry() {
  }

  public static GOAnnotationEntry parse(String line) {
    String[] lines = line.split("\\t");
    if (lines.length < 15) {
      return null;
    }

    GOAnnotationEntry result = new GOAnnotationEntry();
    result.db = lines[0];
    result.db_object_id = lines[1];
    result.db_object_symbol = lines[2];
    result.qualifier = lines[3];
    result.goid = lines[4];
    result.db_reference = lines[5];
    result.evidence = lines[6];
    result.with = lines[7];
    result.aspect = lines[8];
    result.db_object_name = lines[9];
    result.synonym = lines[10];
    result.db_object_type = lines[11];
    String[] taxon_ids = lines[12].split(":");
    if (taxon_ids.length < 2){
      result.taxon_ID = "";
    }
    else{
      result.taxon_ID = taxon_ids[1];
    }
    result.date = lines[13];
    result.assigned_By = lines[14];

    return result;
  }
}
