package cn.ac.rcpa.bio.proteomics.modification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.CountMap;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.Pair;

public class HomologModificationPeptidesList extends
    ArrayList<HomologModificationPeptides> {
  public HomologModificationPeptidesList() {
  }

  public int getPeptideHitCount() {
    HashSet<BuildSummaryPeptideHit> result = new HashSet<BuildSummaryPeptideHit>();
    for (HomologModificationPeptides homo : this) {
      for (DiffStateModificationPeptides diff : homo
          .getModificationPeptidesList()) {
        for (SameModificationPeptides same : diff
            .getSameModificationPeptidesList()) {
          result.addAll(same.getPeptideHits());
        }
      }
    }
    return result.size();
  }

  public int getUniqueModifiedPeptideCount() {
    int result = 0;
    for (HomologModificationPeptides homo : this) {
      result += homo.getModificationPeptidesList().size();
    }
    return result;
  }

  public CountMap<Character> getSiteMap(
      IFilter<Pair<Character, Character>> filter) {
    CountMap<Character> result = new CountMap<Character>();
    for (HomologModificationPeptides homo : this) {
      List<Pair<Character, Character>> modi_sites = homo.getSequence()
          .getSiteListByFilter(filter);
      for (Pair<Character, Character> pair : modi_sites) {
        result.increase(pair.fst);
      }
    }
    return result;
  }

  public CountMap<Integer> getPhosphoCountMap() {
    CountMap<Integer> result = new CountMap<Integer>();
    for (HomologModificationPeptides homo : this) {
      List<DiffStateModificationPeptides> diffs = homo
          .getModificationPeptidesList();
      for (DiffStateModificationPeptides diff : diffs) {
        result.increase(diff.getSequence().getModifiedCount());
      }
    }
    return result;
  }

  public int getModificationSiteCount() {
    int result = 0;
    for (HomologModificationPeptides homo : this) {
      result += homo.getSequence().getModifiedCount();
    }
    return result;
  }
}
