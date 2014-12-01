package cn.ac.rcpa.bio.proteomics.classification.impl;

import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;

public class IdentifiedPeptideMapClassification
    extends IdentifiedPeptideExperimentClassification {
  private Map<String, String> identityMap;

  public IdentifiedPeptideMapClassification(String principle, Map<String, String> identityMap) {
    super(principle);
    
    if (identityMap.isEmpty()) {
      throw new IllegalArgumentException(
          "Parameter identityMap should contains String pair used to classify peptidehits");
    }

    this.identityMap = identityMap;
  }

  @Override
  protected String doGetClassification(IIdentifiedPeptide obj) {
    final String experimentalName = super.doGetClassification(obj);
    final String result = (String) identityMap.get(experimentalName);
    if (result == null) {
      throw new IllegalStateException(experimentalName + " does not match to any classified name defined in map, so classification failed!");
    }

    return result;
  }

}
