package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 *
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 *
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public abstract class AbstractIdentifiedProteinGroup<
    E extends AbstractIdentifiedPeptide,
    F extends AbstractIdentifiedPeptideHit<E>,
    G extends AbstractIdentifiedProtein<E>,
    H extends AbstractIdentifiedProteinGroup<E, F, G, H>>
    implements IIdentifiedProteinGroup<E, F, G, H> {
  protected List<G> proteinHits = new ArrayList<G> ();

  protected List<H> children = new ArrayList<H> ();

  protected List<H> parent = new ArrayList<H> ();

  public AbstractIdentifiedProteinGroup() {
  }

  public Set<String> getProteinNames() {
    final HashSet<String> result = new HashSet<String> ();
    for (int i = 0; i < getProteinCount(); i++) {
      result.add(getProtein(i).getProteinName());
    }
    return result;
  }

  public int getPeptideHitCount() {
    if (proteinHits.isEmpty()) {
      return 0;
    }
    return getProtein(0).getPeptideCount();
  }

  public int getProteinCount() {
    return proteinHits.size();
  }

  public List<G> getProteins() {
    return Collections.unmodifiableList(proteinHits);
  }

  public void addProtein(G hit) {
    proteinHits.add(hit);
  }

  public G getProtein(int index) {
    return proteinHits.get(index);
  }

  public void removeProtein(G hit) {
    proteinHits.remove(hit);
  }

  public void removeProtein(int index) {
    proteinHits.remove(index);
  }

  public void clearProteins() {
    proteinHits.clear();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object clone() {
    H result = null;
    try {
      result = (H)super.clone();
      result.proteinHits = new ArrayList<G> ();
      result.parent = new ArrayList<H> (parent);
      result.children = new ArrayList<H> (children);
      for (G protein : proteinHits) {
        result.addProtein( (G) protein.clone());
      }
    }
    catch (CloneNotSupportedException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
    return result;
  }

  public int getParentCount() {
    return parent.size();
  }

  public List<H> getParents() {
    return Collections.unmodifiableList(parent);
  }

  @SuppressWarnings("unchecked")
  public void addParent(H parentGroup) {
    if (!parent.contains(parentGroup)) {
      parent.add(parentGroup);
      parentGroup.addChild((H)this);
    }
  }

  @SuppressWarnings("unchecked")
  public void removeParent(H group) {
    if (parent.contains(group)) {
      parent.remove(group);
      group.removeChild((H)this);
    }
  }

  @SuppressWarnings("unchecked")
  public void clearParents() {
    for (int i = parent.size() - 1; i >= 0; i--) {
      parent.get(i).removeChild((H)this);
    }
    parent.clear();
  }

  public int getChildrenCount() {
    return children.size();
  }

  public List<H> getChildren() {
    return Collections.unmodifiableList(children);
  }

  @SuppressWarnings("unchecked")
  public void addChild(H childGroup) {
    if (!children.contains(childGroup)) {
      children.add(childGroup);
      childGroup.addParent((H)this);
    }
  }

  @SuppressWarnings("unchecked")
  public void removeChild(H group) {
    if (children.contains(group)) {
      children.remove(group);
      group.removeParent((H)this);
    }
  }

  @SuppressWarnings("unchecked")
  public void clearChildren() {
    for (int i = children.size() - 1; i >= 0; i--) {
      children.get(i).removeParent((H)this);
    }
    children.clear();
  }
}
