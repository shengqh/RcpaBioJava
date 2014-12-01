package cn.ac.rcpa.bio.annotation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGOEntry extends Comparable<IGOEntry> {
  String getAccession();
  void setAccession(String accession);

  String getName();
  void setName(String name);

  String getDefinition();
  void setDefinition(String definition);

  String getSynonym();
  void setSynonym(String synonym);

  String getNamespace();
  void setNamespace(String namespace);

  Set<String> getParentAccessions();

  List<IGOEntry> getChildren();

  Map<String, IGOEntry> getGOEntryMap();
  
  int getDeepestLevel();

  void loadFromFile(String filename);

  void saveToFile(String filename);
}
