package cn.ac.rcpa.bio.proteomics.results.buildsummary;

import cn.ac.rcpa.bio.proteomics.AbstractIdentifiedProtein;

final public class BuildSummaryProtein
    extends AbstractIdentifiedProtein<BuildSummaryPeptide> {
  public static final int UN_GROUPED = -1;
  private String database;
  private int group;

  public BuildSummaryProtein() {
    group = UN_GROUPED;
  }

  public String getDatabase() {
    return database;
  }

  public int getGroup() {
    return group;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public void setGroup(int group) {
    this.group = group;
  }

  public boolean isGrouped() {
    return group != UN_GROUPED;
  }

  public int findPeptide(BuildSummaryPeptide pephit) {
    for (int i = 0; i < getPeptideCount(); i++) {
      if ( ( getPeptide(i).isIdentificationEquals(pephit))) {
        return i;
      }
    }
    return -1;
  }

  public boolean isIdentificationContains(BuildSummaryProtein prohit) {
    if (getPeptideCount() <= prohit.getPeptideCount()) {
      return false;
    }

    for (int i = 0; i < prohit.getPeptideCount(); i++) {
      if (findPeptide(prohit.getPeptide(i)) == -1) {
        return false;
      }
    }

    return true;
  }

  public boolean isIdentificationEquals(BuildSummaryProtein prohit) {
    if (prohit == null) {
      throw new IllegalArgumentException(
          "Parameter prohit of SequestProteinHit.isIdentificationEquals cannot be null");
    }

    if (getPeptideCount() != prohit.getPeptideCount()) {
      return false;
    }

    for (int i = 0; i < getPeptideCount(); i++) {
      if (prohit.findPeptide(getPeptide(i)) == -1) {
        return false;
      }
    }

    return true;
  }
}
