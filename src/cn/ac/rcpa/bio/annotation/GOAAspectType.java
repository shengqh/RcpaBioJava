package cn.ac.rcpa.bio.annotation;

public class GOAAspectType {
  final private GOEntry root;
  final private GOEntry unknown;
  final private String type;
  final private int defaultLevel;

  private GOAAspectType(String type, String rootAccession, String rootName,
                        String unknownAccession, String unknownName, int defaultLevel) {
    this.type = type;
    this.defaultLevel = defaultLevel;
    root = new GOEntry(rootAccession, rootName, "");
    unknown = new GOEntry(unknownAccession, unknownName, "");
  }

  public static final GOAAspectType MOLECULAR_FUNCTION = new GOAAspectType("F",
      "GO:0003674", "molecular_function", "GO:0005554","molecular_function unknown",4);
  public static final GOAAspectType BIOLOGICAL_PROCESS = new GOAAspectType("P",
      "GO:0008150", "biological_process", "GO:0000004","biological_process unknown",4);
  public static final GOAAspectType CELLULAR_COMPONENT = new GOAAspectType("C",
      "GO:0005575", "cellular_component", "GO:0008372","cellular_component unknown",4);

  public static final GOAAspectType[] GOA_ASPECT_TYPES = {
      MOLECULAR_FUNCTION, BIOLOGICAL_PROCESS, CELLULAR_COMPONENT
  };

  public static GOAAspectType getGOAAspectType(String type) {
    for (int i = 0; i < GOA_ASPECT_TYPES.length; i++) {
      if (GOA_ASPECT_TYPES[i].type.equals(type)) {
        return GOA_ASPECT_TYPES[i];
      }
    }
    throw new IllegalArgumentException(type +
                                       " is not a valid GOA aspect type!");
  }

  @Override
  public String toString() {
    return type;
  }

  public GOEntry getUnknown() {
    return unknown;
  }

  public GOEntry getRoot() {
    return root;
  }

  public int getDefaultLevel() {
    return defaultLevel;
  }
}
