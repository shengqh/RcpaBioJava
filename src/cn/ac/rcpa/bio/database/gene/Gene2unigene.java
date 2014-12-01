package cn.ac.rcpa.bio.database.gene;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Gene2unigene implements Serializable {

  /** identifier field */
  private Long id;

  /** nullable persistent field */
  private Integer gene;

  /** nullable persistent field */
  private String unigene;

  /** full constructor */
  public Gene2unigene(Integer gene, String unigene) {
    this.gene = gene;
    this.unigene = unigene;
  }

  /** default constructor */
  public Gene2unigene() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getGene() {
    return this.gene;
  }

  public void setGene(Integer gene) {
    this.gene = gene;
  }

  public String getUnigene() {
    return this.unigene;
  }

  public void setUnigene(String unigene) {
    this.unigene = unigene;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }

}
