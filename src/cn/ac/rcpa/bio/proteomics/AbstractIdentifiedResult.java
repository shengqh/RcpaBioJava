package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.proteomics.comparison.IdentifiedProteinGroupComparator;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class AbstractIdentifiedResult<
    E extends IIdentifiedPeptide,
    F extends IIdentifiedPeptideHit<E>,
    G extends IIdentifiedProtein<E>,
    H extends IIdentifiedProteinGroup<E, F, G, H>>
    implements IIdentifiedResult<E,F,G,H> {
  protected ArrayList<H> groups = new ArrayList<H> ();

  public AbstractIdentifiedResult() {
  }

  public void addProteinGroup(H proteinGroup) {
    groups.add(proteinGroup);
  }

  public void clearProteinGroups() {
    groups.clear();
  }

  public H getProteinGroup(int index) {
    return groups.get(index);
  }

  public int getProteinGroupCount() {
    return groups.size();
  }

  public void removeProteinGroup(int index) {
    groups.remove(index);
  }

  public void removeProteinGroup(H proteinGroup) {
    groups.remove(proteinGroup);
  }

  public boolean containAnnotation(String key) {
    List<G> prohits = getProteins();
    for (G protein : prohits) {
      if (protein.getAnnotation().containsKey(key)) {
        return true;
      }
    }
    return false;
  }

  public List<H> getProteinGroups() {
    return Collections.unmodifiableList(groups);
  }

  public List<G> getProteins() {
    ArrayList<G> result = new ArrayList<G> ();
    for (H group : groups) {
      result.addAll(group.getProteins());
    }
    return result;
  }

  public List<F> getPeptideHits() {
    final List<F> result = new ArrayList<F> ();

    final HashSet<String> scans = new HashSet<String> ();

    for (int i = 0; i < getProteinGroupCount(); i++) {
      final List<F> hits = groups.get(i).getPeptideHits();
      for (F pephit : hits) {
        if (scans.contains(pephit.getPeakListInfo().getLongFilename())) {
          continue;
        }

        scans.add(pephit.getPeakListInfo().getLongFilename());
        result.add(pephit);
      }
    }

    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object clone() {
    AbstractIdentifiedResult result = null;
    try {
      result = (AbstractIdentifiedResult)super.clone();
      result.groups = new ArrayList<H> ();
      for (H group : groups) {
        result.addProteinGroup( (H) group.clone());
      }
      result.buildGroupRelationship();
    }
    catch (CloneNotSupportedException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
    return result;
  }

  public void buildGroupRelationship() {
    Map<H, Set<String>> groupFilenamesMap =
        new HashMap<H, Set<String>> ();
    for (H group : groups) {
      group.clearParents();
      group.clearChildren();
      groupFilenamesMap.put(group,
                            PeptideUtils.getFilenames(group.getPeptideHits()));
    }

    for (int i = 0; i < groups.size(); i++) {
      Set<String> filenames1 = groupFilenamesMap.get(groups.get(i));
      for (int j = i + 1; j < groups.size(); j++) {
        Set<String> filenames2 = groupFilenamesMap.get(groups.get(j));
        if (filenames1.containsAll(filenames2)) {
          groups.get(i).addChild(groups.get(j));
        }
      }
    }
  }

  public void buildProteinPeptideRelationship() {
    List<F> pephits = getPeptideHits();
    for (F pephit : pephits) {
      List<E> peptides = pephit.getPeptides();
      for (E peptide : peptides) {
        peptide.clearProteinNames();
      }
    }

    List<G> proteins = getProteins();
    for (G protein : proteins) {
      List<E> peptides = protein.getPeptides();
      for (E peptide : peptides) {
        peptide.addProteinName(protein.getProteinName());
      }
    }
  }

  public void sort(){
    Collections.sort(groups,IdentifiedProteinGroupComparator.getInstance());
  }

  public void sort(Comparator<IIdentifiedProteinGroup> comp){
    Collections.sort(groups, comp);
  }

}
