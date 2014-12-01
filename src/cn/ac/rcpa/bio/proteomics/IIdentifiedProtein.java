package cn.ac.rcpa.bio.proteomics;

import java.util.List;
import java.util.Map;

public interface IIdentifiedProtein<E extends IIdentifiedPeptide>
    extends Cloneable {
  public String getProteinName();

  public void setProteinName(String proteinName);

  public String getReference();

  public void setReference(String reference);

  public String getSequence();

  public void setSequence(String sequence);

  public int getPeptideCount();

  public List<E> getPeptides();

  public void addPeptide(E peptide);

  public void removePeptide(E peptide);

  public void removePeptide(int index);

  public E getPeptide(int index);

  public void clearPeptides();

  public String[] getUniquePeptides();

  public Map<String, Object> getAnnotation();

  public double getPI();

  public void setPI(double PI);

  public double getMW();

  public void setMW(double MW);

  public double getCoverage();

  public void setCoverage(double coverage);

  public Object clone();
}
