package cn.ac.rcpa.bio.annotation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.ac.rcpa.utils.XMLFile;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;

public class GOEntry implements IGOEntry {
  private String accession = "";

  private String name = "";

  private String namespace = "";

  private String definition = "";

  private String synonym = "";

  protected Set<String> parentAccessions = new HashSet<String>();

  protected List<IGOEntry> children = new ArrayList<IGOEntry>();

  public GOEntry(String accession, String name, String definition) {
    this.accession = accession;
    this.name = name;
    this.definition = definition;
  }

  public GOEntry() {
  }

  public String getName() {
    return name;
  }

  public void setAccession(String accession) {
    this.accession = accession;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDefinition(String definition) {
    this.definition = definition;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getAccession() {
    return accession;
  }

  public String getDefinition() {
    return definition;
  }

  public String getNamespace() {
    return namespace;
  }

  public List<IGOEntry> getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return accession + " : " + name;
  }

  public String getSynonym() {
    return synonym;
  }

  public void setSynonym(String synonym) {
    this.synonym = synonym;
  }

  public Set<String> getParentAccessions() {
    return parentAccessions;
  }

  public Map<String, IGOEntry> getGOEntryMap() {
    Map<String, IGOEntry> result = new HashMap<String, IGOEntry>();
    result.put(accession, this);

    for (IGOEntry child : children) {
      result.putAll(child.getGOEntryMap());
    }

    return result;
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) {
      return true;
    }
    if (anObject instanceof GOEntry) {
      GOEntry anotherEntry = (GOEntry) anObject;
      return accession.equals(anotherEntry.getAccession());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return accession.hashCode();
  }

  public int compareTo(IGOEntry o) {
    return accession.compareTo(o.getAccession());
  }

  public int getDeepestLevel() {
    return getDeepestLevel(0);
  }

  private int getDeepestLevel(int parentLevel) {
    int result = parentLevel + 1;
    for (int i = 0; i < children.size(); i++) {
      int childLevel = parentLevel + 1 + children.get(i).getDeepestLevel();
      if (result < childLevel) {
        result = childLevel;
      }
    }
    return result;
  }

  public void loadFromFile(String filename) {
    XStream xs = new XStream();
    setXStreamParameter(xs);
    try {
      FileReader fr = new FileReader(filename);
      try {
        xs.fromXML(fr, this);
      } finally {
        fr.close();
      }
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    } catch (ConversionException ex) {
      loadFromFileByJDom(filename);
    }
  }

  public void saveToFile(String filename) {
    XStream xs = new XStream();
    setXStreamParameter(xs);
    try {
      FileWriter fw = new FileWriter(filename);
      try {
        xs.toXML(this, fw);
      } finally {
        fw.close();
      }
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
  }

  protected void setXStreamParameter(XStream xs) {
    xs.alias("GOEntry", this.getClass());
  }

  // old load from file by jdom
  protected void loadFromFileByJDom(String filename) {
    try {
      children.clear();
      parentAccessions.clear();

      XMLFile optionFile = new XMLFile(filename);
      Element elem = optionFile.getDocument().getRootElement();
      loadElement(optionFile, elem);
    } catch (Exception ex) {
      throw new IllegalStateException(ex.getCause());
    }
  }

  private void loadElement(XMLFile optionFile, Element elem) {
    loadSelf(optionFile, elem);
    loadChildren(optionFile, elem);
  }

  private void loadChildren(XMLFile optionFile, Element elem) {
    Element childrenElem = elem.getChild("Children");
    if (childrenElem != null) {
      List entries = childrenElem.getChildren("GOEntry");
      for (Object child : entries) {
        Element childElem = (Element) child;
        GOEntry childEntry = null;
        try {
          childEntry = this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
          throw new IllegalStateException(ex.getMessage());
        } catch (InstantiationException ex) {
          throw new IllegalStateException(ex.getMessage());
        }
        children.add(childEntry);
        childEntry.loadElement(optionFile, childElem);
      }
    }
  }

  protected void saveToFileByJDom(String filename) {
    try {
      XMLFile optionFile = new XMLFile(filename);
      Element elem = new Element("GOEntry");
      optionFile.getDocument().setRootElement(elem);
      saveElement(optionFile, elem);
      optionFile.saveToFile();
    } catch (Exception ex) {
      throw new IllegalStateException(ex.getMessage());
    }
  }

  private void saveElement(XMLFile optionFile, Element elem) {
    saveSelf(optionFile, elem);
    saveChildren(optionFile, elem);
  }

  private void saveChildren(XMLFile optionFile, Element elem) {
    if (children.size() > 0) {
      Element childrenElem = new Element("Children");
      elem.addContent(childrenElem);
      for (IGOEntry entry : children) {
        Element childElem = new Element("GOEntry");
        childrenElem.addContent(childElem);
        GOEntry go = (GOEntry) entry;
        go.saveElement(optionFile, childElem);
      }
    }
  }

  protected void loadSelf(XMLFile file, Element elem) {
    accession = elem.getChildTextTrim("accession");
    name = elem.getChildTextTrim("name");
    definition = elem.getChildTextTrim("definition");
    namespace = elem.getChildTextTrim("namespace");
    synonym = elem.getChildTextTrim("synonym");
  }

  protected void saveSelf(XMLFile file, Element elem) {
    file.setElementValue(elem, "accession", accession);
    file.setElementValue(elem, "name", name);
    file.setElementValue(elem, "definition", definition);
    file.setElementValue(elem, "namespace", namespace);
    file.setElementValue(elem, "synonym", synonym);
  }
}
