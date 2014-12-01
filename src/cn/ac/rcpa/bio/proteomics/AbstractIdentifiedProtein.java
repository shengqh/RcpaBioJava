package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class AbstractIdentifiedProtein<E extends AbstractIdentifiedPeptide>
    implements IIdentifiedProtein<E> {
  protected String proteinName;

  protected String reference;

  protected String sequence;

  protected double MW;

  protected double PI;

  protected double coverage;

  protected Map<String, Object> annotation = new HashMap<String, Object>();

  protected List<E> peptides = new ArrayList<E>();

  public AbstractIdentifiedProtein() {
  }

  public double getPI() {
    return PI;
  }

  public void setPI(double PI) {
    this.PI = PI;
  }

  public double getMW() {
    return MW;
  }

  public void setMW(double MW) {
    this.MW = MW;
  }

  public double getCoverage() {
    return coverage;
  }

  public void setCoverage(double coverage) {
    this.coverage = coverage;
  }

  @Override
  public String toString() {
    return "Protein " + proteinName + " : " + peptides.size();
  }

  public Map<String, Object> getAnnotation() {
    return annotation;
  }

  public int getPeptideCount() {
    return peptides.size();
  }

  public List<E> getPeptides() {
    return peptides;
  }

  public E getPeptide(int index) {
    return peptides.get(index);
  }

  public void removePeptide(E value) {
    peptides.remove(value);
  }

  public void removePeptide(int index) {
    peptides.remove(index);
  }

  public void removePeptide(String longfilename) {
    for (int i = peptides.size() - 1; i >= 0; i--) {
      if (longfilename.equals(peptides.get(i).getPeakListInfo()
          .getLongFilename())) {
        peptides.remove(i);
      }
    }
  }

  public String getProteinName() {
    return proteinName;
  }

  public void setProteinName(String proteinName) {
    this.proteinName = proteinName;
  }

  public String getSequence() {
    return sequence;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public void clearPeptides() {
    peptides.clear();
  }

  public String[] getUniquePeptides() {
    Set<String> result = new HashSet<String>();
    for (Iterator iter = peptides.iterator(); iter.hasNext();) {
      IIdentifiedPeptide item = (IIdentifiedPeptide) iter.next();
      result.add(PeptideUtils.getPurePeptideSequence(item.getSequence()));
    }

    return (String[]) result.toArray(new String[0]);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object clone() {
    AbstractIdentifiedProtein<E> result = null;
    try {
      result = (AbstractIdentifiedProtein<E>) super.clone();
      result.annotation = new HashMap<String, Object>(annotation);
      result.peptides = new ArrayList<E>(peptides);
    } catch (CloneNotSupportedException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
    return result;
  }

  public void setPeptide(int index, E peptide) {
    peptides.set(index, peptide);
  }
  
  public void addPeptide(E peptide) {
    peptides.add(peptide);
  }
  /*
   * public void assign(AbstractIdentifiedProtein source) { this.proteinName =
   * source.getProteinName(); this.reference = source.getReference();
   * this.sequence = source.getSequence(); this.MW = source.getMW(); this.PI =
   * source.getPI(); this.coverage = source.getCoverage(); this.annotation = new
   * HashMap<String, Object> (source.getAnnotation()); this.peptides = new
   * ArrayList<E> (source.getPeptides()); }
   */
}
