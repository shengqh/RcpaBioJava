package cn.ac.rcpa.bio.proteomics;

import java.util.List;

public interface IIdentifiedProteinGroup<
    E extends IIdentifiedPeptide,
    F extends IIdentifiedPeptideHit<E>,
    G extends IIdentifiedProtein<E>,
    H extends IIdentifiedProteinGroup<E, F, G, H>>
    extends Cloneable {
  public int getProteinCount();

  public List<G> getProteins();

  public G getProtein(int index);

  public void addProtein(G Protein);

  public void removeProtein(G Protein);

  public void removeProtein(int index);

  public void clearProteins();

  public int getPeptideHitCount();

  public List<F> getPeptideHits();

  public int getParentCount();

  public List<H> getParents();

  public void addParent(H parentGroup);

  public void removeParent(H group);

  public void clearParents();

  public int getChildrenCount();

  public List<H> getChildren();

  public void addChild(H ChildGroup);

  public void removeChild(H group);

  public void clearChildren();

  public Object clone();
}
