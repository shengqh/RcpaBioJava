package cn.ac.rcpa.bio.annotation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;

import cn.ac.rcpa.utils.RcpaObjectUtils;
import cn.ac.rcpa.utils.XMLFile;

public class GOEntryMap {
  private Map<String, IGOEntry> entryMap = new HashMap<String, IGOEntry> ();

  public GOEntryMap(Map<String, IGOEntry> map) {
    entryMap = map;

    buildParentAccessions();
  }

  private GOEntryMap() {
  }

  public String[] getAccessions() {
    String[] result = (String[]) entryMap.keySet().toArray(new String[0]);
    Arrays.sort(result);
    return result;
  }

  public IGOEntry[] getEntries() {
    return (IGOEntry[]) entryMap.values().toArray(new IGOEntry[0]);
  }

  public IGOEntry getEntry(String accession) {
    return entryMap.get(accession);
  }

  public boolean isParent(String childAccession, String parentAccession) {
    IGOEntry child = getEntry(childAccession);

    if (child != null) {
      Set<String> parents = child.getParentAccessions();
      for (String accession : parents) {
        IGOEntry parent = getEntry(accession);

        if (parent == null) {
          throw new IllegalStateException("Parent not exists?" + accession +
                                          " child:" + child.getName());
        }

        if (parent.getAccession().equals(parentAccession)) {
          return true;
        }

        if (isParent(parent.getAccession(), parentAccession)) {
          return true;
        }
      }
    }
    return false;
  }

  private void buildChildrenTree() {
    for (IGOEntry child : entryMap.values()) {
      Set<String> parentAccessions = child.getParentAccessions();
      for (String accession : parentAccessions) {
        IGOEntry parent = (GOEntry) entryMap.get(accession);
        if (parent == null) {
          throw new IllegalStateException("Parent node " + accession +
                                          " cannot find ");
        }

        parent.getChildren().add(child);
      }
    }
  }

  private void buildParentAccessions() {
    for (IGOEntry child : entryMap.values()) {
      Set<String> parentAccessions = child.getParentAccessions();
      for (String accession : parentAccessions) {
        IGOEntry parent = (GOEntry) entryMap.get(accession);
        if (parent == null) {
          throw new IllegalStateException("Parent node " + accession +
                                          " cannot find ");
        }

        parent.getChildren().add(child);
      }
    }
  }

  public static GOEntryMap getGOEntryMapFromXMLFile(String filename) throws
      Exception {
    XMLFile dbfile = new XMLFile(filename);
    Element root = dbfile.getDocument().getRootElement();
    Namespace goNamespace = root.getNamespace();

    Element rdf = (Element) root.getChildren().get(0);
    Namespace rdfNamespace = rdf.getNamespace();

    GOEntryMap result = new GOEntryMap();
    for (int i = 0; i < rdf.getChildren().size(); i++) {
      Element goterm = (Element) rdf.getChildren().get(i);

      Element accession = goterm.getChild("accession", goNamespace);
      Element name = goterm.getChild("name", goNamespace);

      Element definition = goterm.getChild("definition", goNamespace);
      if (definition != null && definition.getTextTrim().startsWith("OBSOLETE")) {
        continue;
      }

      GOEntry entry = new GOEntry(accession.getTextTrim(),
                                  name.getTextTrim(),
                                  definition == null ? "" :
                                  definition.getTextTrim());

      final List part_of = goterm.getChildren("part_of", goNamespace);
      final List is_a = goterm.getChildren("is_a", goNamespace);
      List<Element>
          parents = new ArrayList<Element> (RcpaObjectUtils.asList(part_of,
          new Element[0]));
      parents.addAll(RcpaObjectUtils.asList(is_a, new Element[0]));

      for (Iterator iter = parents.iterator(); iter.hasNext(); ) {
        Element parent = (Element) iter.next();
        String goParentString = parent.getAttribute("resource", rdfNamespace).
            getValue();
        int ipos = goParentString.indexOf("#");
        String goParent = goParentString.substring(ipos + 1);
        if (goParent.startsWith("GO:")) {
          entry.getParentAccessions().add(goParent);
        }
      }

      result.entryMap.put(entry.getAccession(), entry);
    }

    result.buildChildrenTree();

    return result;
  }

  public static GOEntryMap getGOEntryMapFromOBOFile(String filename) throws
      FileNotFoundException, IOException {
    BufferedReader br = new BufferedReader(new FileReader(filename));
    String sline;
    GOEntryMap result = new GOEntryMap();
    while ( (sline = br.readLine()) != null) {
      if (!sline.equals("[Term]")) {
        continue;
      }

      GOEntry entry = new GOEntry("", "", "");
      while ( (sline = br.readLine()) != null) {
        if (sline.trim().length() == 0) {
          break;
        }

        if (sline.startsWith("id:")) {
          entry.setAccession(sline.substring(4).trim());
        }
        else if (sline.startsWith("name:")) {
          entry.setName(sline.substring(6).trim());
        }
        else if (sline.startsWith("namespace:")) {
          entry.setNamespace(sline.substring(11).trim());
        }
        else if (sline.startsWith("is_a:")) {
          entry.getParentAccessions().add(sline.substring(6).trim());
        }
        else if (sline.startsWith("relationship: part_of")) {
          entry.getParentAccessions().add(sline.substring(22).trim());
        }
      }

      result.entryMap.put(entry.getAccession(), entry);
    }

    result.buildChildrenTree();

    return result;
  }

}
