package cn.ac.rcpa.bio.database.gene;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Gene2go implements Serializable {

  /** identifier field */
  private Long id;

  /** nullable persistent field */
  private String evidence;

  /** persistent field */
  private int geneId;

  /** nullable persistent field */
  private String goDescription;

  /** nullable persistent field */
  private String goId;

  /** nullable persistent field */
  private String goQualifier;

  /** nullable persistent field */
  private String pipeSeparatedListOfPubmedId;

  /** persistent field */
  private int taxId;

  /** full constructor */
  public Gene2go(String evidence, int geneId, String goDescription,
      String goId, String goQualifier, String pipeSeparatedListOfPubmedId,
      int taxId) {
    this.evidence = evidence;
    this.geneId = geneId;
    this.goDescription = goDescription;
    this.goId = goId;
    this.goQualifier = goQualifier;
    this.pipeSeparatedListOfPubmedId = pipeSeparatedListOfPubmedId;
    this.taxId = taxId;
  }

  /** default constructor */
  public Gene2go() {
  }

  /** minimal constructor */
  public Gene2go(int geneId, int taxId) {
    this.geneId = geneId;
    this.taxId = taxId;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEvidence() {
    return this.evidence;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public int getGeneId() {
    return this.geneId;
  }

  public void setGeneId(int geneId) {
    this.geneId = geneId;
  }

  public String getGoDescription() {
    return this.goDescription;
  }

  public void setGoDescription(String goDescription) {
    this.goDescription = goDescription;
  }

  public String getGoId() {
    return this.goId;
  }

  public void setGoId(String goId) {
    this.goId = goId;
  }

  public String getGoQualifier() {
    return this.goQualifier;
  }

  public void setGoQualifier(String goQualifier) {
    this.goQualifier = goQualifier;
  }

  public String getPipeSeparatedListOfPubmedId() {
    return this.pipeSeparatedListOfPubmedId;
  }

  public void setPipeSeparatedListOfPubmedId(String pipeSeparatedListOfPubmedId) {
    this.pipeSeparatedListOfPubmedId = pipeSeparatedListOfPubmedId;
  }

  public int getTaxId() {
    return this.taxId;
  }

  public void setTaxId(int taxId) {
    this.taxId = taxId;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }

}
