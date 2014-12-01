package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.utils.RcpaObjectUtils;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class BuildSummaryProteinGroup
    extends AbstractIdentifiedProteinGroup<BuildSummaryPeptide,
    BuildSummaryPeptideHit,BuildSummaryProtein, BuildSummaryProteinGroup> {
  private int number;

  public void setNumber(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  public BuildSummaryProteinGroup() {
    number = -1;
  }

  public boolean isIdentificationContains(BuildSummaryProteinGroup group) {
    if (group.getProteinCount() == 0) {
      return true;
    }

    if (getProteinCount() == 0) {
      return false;
    }

    final BuildSummaryProtein curProtein = getProtein(0);
    final BuildSummaryProtein targetProtein = group.getProtein(0);
    return curProtein.isIdentificationContains(targetProtein);
  }

  public boolean isIdentificationEquals(BuildSummaryProteinGroup group) {
    if (group.getProteinCount() == 0 || getProteinCount() == 0) {
      return false;
    }

    final BuildSummaryProtein curProtein = getProtein(0);
    final BuildSummaryProtein targetProtein = group.getProtein(0);
    return curProtein.isIdentificationEquals(targetProtein);
  }

  public int sortCompare(BuildSummaryProteinGroup group) {
    if (getProteinCount() == 0 || group.getProteinCount() == 0) {
      return getProteinCount() - group.getProteinCount();
    }
    else {
      final int thisUnipepCount = getProtein(0).getUniquePeptides().length;
      final int unipepCount = group.getProtein(0).getUniquePeptides().length;
      if (thisUnipepCount == unipepCount) {
        return getProtein(0).getPeptideCount() -
            group.getProtein(0).getPeptideCount();
      }
      else {
        return thisUnipepCount - unipepCount;
      }
    }
  }

  public void mergeWith(BuildSummaryProteinGroup group) {
    for (int i = 0; i < group.getProteinCount(); i++) {
      addProtein(group.getProtein(i));
    }
  }

  @Override
  public String toString() {
    return "Group " + number + " : " + proteinHits.size();
  }

  public void remain(BuildSummaryProtein prohit) {
    proteinHits.clear();
    proteinHits.add(prohit);
  }

  protected void checkProteinClass(IIdentifiedProtein prohit) {
    RcpaObjectUtils.assertInstanceOf(prohit, BuildSummaryProtein.class);
  }

  public List<BuildSummaryPeptideHit> getPeptideHits() {
    LinkedHashMap<String, BuildSummaryPeptideHit> hits = new LinkedHashMap<String, BuildSummaryPeptideHit>();

    for (int i = 0; i < getProtein(0).getPeptideCount(); i++) {
      BuildSummaryPeptide peptide = getProtein(0).getPeptide(i);
      if (!hits.containsKey(peptide.getPeakListInfo().getLongFilename())){
        hits.put(peptide.getPeakListInfo().getLongFilename(), new BuildSummaryPeptideHit());
      }

      BuildSummaryPeptideHit hit = hits.get(peptide.getPeakListInfo().getLongFilename());
      hit.addPeptide(peptide);
    }

    for(int i = 1;i < getProteinCount();i++){
      for(int j = 0;j < getProtein(i).getPeptideCount();j++){
        BuildSummaryPeptide peptide = getProtein(i).getPeptide(j);
        BuildSummaryPeptideHit hit = hits.get(peptide.getPeakListInfo().getLongFilename());

        boolean bFound = false;
        for(int k = 0;k < hit.getPeptideCount();k++){
          if (hit.getPeptide(k).getSequence().equals(peptide.getSequence())){
            bFound = true;
            break;
          }
        }

        if (!bFound){
          hit.addPeptide(peptide);
        }
      }
    }

    return new ArrayList<BuildSummaryPeptideHit>(hits.values());
  }
}
