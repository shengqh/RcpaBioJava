package cn.ac.rcpa.bio.proteomics;

import java.util.List;

public interface IIdentifiedResult<
    E extends IIdentifiedPeptide,
    F extends IIdentifiedPeptideHit<E>,
    G extends IIdentifiedProtein<E>,
    H extends IIdentifiedProteinGroup<E, F, G, H>>
    extends Cloneable {
  public int getProteinGroupCount();

  public List<H> getProteinGroups();

  public void addProteinGroup(H proteinGroup);

  public void removeProteinGroup(H proteinGroup);

  public void removeProteinGroup(int index);

  public H getProteinGroup(int index);

  public void clearProteinGroups();

  public List<G> getProteins();

  public List<F> getPeptideHits();

  public boolean containAnnotation(String key);

  public void buildGroupRelationship();

  public void buildProteinPeptideRelationship();

  public void sort();

  public Object clone();
}
