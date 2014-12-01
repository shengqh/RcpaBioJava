package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.sequest.SequestFilename;

public class BuildSummaryResult
    extends AbstractIdentifiedResult<
    BuildSummaryPeptide,
    BuildSummaryPeptideHit,
    BuildSummaryProtein,
    BuildSummaryProteinGroup> {
  public BuildSummaryResult() {
  }

  private BuildSummaryProteinGroup getSequestProteinGroup(int index) {
    return (BuildSummaryProteinGroup) groups.get(index);
  }

  /**
   * Get duplicated peptide hit. Duplicated means the peaklist identified two
   * almost same peptides (such as K.EFGEPIK.T and K.EFGEPLK.T).
   *
   * @return SequestPeptideHit[]
   */
  public BuildSummaryPeptide[] getDuplicatedPeptideHit() {
    HashMap<String, BuildSummaryPeptide> result = new HashMap<String,
        BuildSummaryPeptide> ();
    List proteins = getProteins();
    for (Iterator iter = proteins.iterator(); iter.hasNext(); ) {
      IIdentifiedProtein protein = (IIdentifiedProtein) iter.next();
      List peptides = protein.getPeptides();
      for (Iterator iter2 = peptides.iterator(); iter2.hasNext(); ) {
        BuildSummaryPeptide peptide = (BuildSummaryPeptide) iter2.next();
        SequestFilename fname = (SequestFilename) peptide.getPeakListInfo();
        final String key = fname.getLongFilename() + "_" + peptide.getSequence();
        if (!result.containsKey(key)) {
          result.put(key, peptide);
        }
      }
    }
    return (BuildSummaryPeptide[]) result.values().toArray(new
        BuildSummaryPeptide[0]);
  }

  public void rebuildGroupIndex() {
    for (int i = 0; i < getProteinGroupCount(); i++) {
      getSequestProteinGroup(i).setNumber(i + 1);
      for (int j = 0; j < getSequestProteinGroup(i).getProteinCount(); j++) {
        BuildSummaryProtein prohit = (BuildSummaryProtein)
            getSequestProteinGroup(i).getProtein(j);
        prohit.setGroup(i + 1);
      }
    }
  }

  public void mergeGroups() {
    sort();
    for (int iBegin = 0; iBegin < getProteinGroupCount(); iBegin++) {
      for (int iMerge = getProteinGroupCount() - 1; iMerge > iBegin; iMerge--) {
        if (getSequestProteinGroup(iBegin).isIdentificationEquals(
            getSequestProteinGroup(iMerge))) {
          getSequestProteinGroup(iBegin).mergeWith(getSequestProteinGroup(
              iMerge));
          System.out.println("Merge " +
                             getSequestProteinGroup(iMerge).getNumber() +
                             " into " +
                             getSequestProteinGroup(iBegin).getNumber());
          removeProteinGroup(iMerge);
        }
      }
    }
  }

  public void removeDuplicates() {
    sort();
    for (int iBegin = 0; iBegin < getProteinGroupCount(); iBegin++) {
      for (int iDuplicate = getProteinGroupCount() - 1; iDuplicate > iBegin;
           iDuplicate--) {
        if (getSequestProteinGroup(iBegin).isIdentificationContains(
            getSequestProteinGroup(iDuplicate))) {
          System.out.println("Remove " +
                             getSequestProteinGroup(iDuplicate).getNumber() +
                             " contained in " +
                             getSequestProteinGroup(iBegin).getNumber());
          removeProteinGroup(iDuplicate);
        }
      }
    }
  }
}
