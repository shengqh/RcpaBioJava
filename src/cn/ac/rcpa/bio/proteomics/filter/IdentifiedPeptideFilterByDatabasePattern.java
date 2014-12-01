package cn.ac.rcpa.bio.proteomics.filter;

import java.util.List;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.filter.IFilter;

public class IdentifiedPeptideFilterByDatabasePattern implements
		IFilter<IIdentifiedPeptide> {
  private Pattern dbPattern;

  public IdentifiedPeptideFilterByDatabasePattern(String dbPattern) {
    this.dbPattern = Pattern.compile(dbPattern);
  }

  /**
   * Return true if one of the proteins come from reversed database
   */
  public boolean accept(IIdentifiedPeptide e) {
    List<String> proteins = e.getProteinNames();
    for (String protein : proteins) {
      if (dbPattern.matcher(protein).find()) {
        return true;
      }
    }
    return false;
  }

  public String getType() {
    return "DatabasePattern";
  }
}
