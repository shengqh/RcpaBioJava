package cn.ac.rcpa.bio.database;

import java.util.HashMap;

public class DatabaseShortLabelMap{
  private HashMap<String, String> shortLabelMap = new HashMap<String, String>();
  private static DatabaseShortLabelMap instance;

  public static DatabaseShortLabelMap getInstance() {
    if (instance == null) {
      instance = new DatabaseShortLabelMap();
    }
    return instance;
  }

  private DatabaseShortLabelMap() {
    super();
    shortLabelMap.put("UniProtKB/Swiss-Prot","uniprot");
    shortLabelMap.put("UniProtKB/TrEMBL","uniprot");
    shortLabelMap.put("UniProt/Swiss-Prot","uniprot");
    shortLabelMap.put("UniProt/TrEMBL","uniprot");
    shortLabelMap.put("SWISSPROT","uniprot");
    shortLabelMap.put("TrEMBL","uniprot");
  }

  public String getShortLabel(String oldName){
    if (shortLabelMap.containsKey(oldName)){
      return shortLabelMap.get(oldName);
    }
    return oldName;
  }


}
