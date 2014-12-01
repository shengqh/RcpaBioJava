package cn.ac.rcpa.bio.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;

import cn.ac.rcpa.utils.XMLFile;

public class GOAClassificationEntry extends GOEntry{
  private Map<String, String> annotations = new HashMap<String, String>();
  private Set<String> proteins = new HashSet<String>();

  public Map<String, String> getAnnotations() {
    return annotations;
  }
  
  public Set<String> getProteins() {
    return proteins;
  }

  @Override
  public int compareTo(IGOEntry o) {
    if (o == this){
      return 0;
    }
    
    if (!( o instanceof GOAClassificationEntry) ){
      return super.compareTo(o);
    }

    GOAClassificationEntry target = (GOAClassificationEntry)o;
    return target.getProteins().size() - this.getProteins().size();
  }

  public void sortChildren(){
    Collections.sort(children);
  }

  public GOAClassificationEntry(String accession, String name,
                                String definition) {
    super(accession, name, definition);
  }

  public GOAClassificationEntry() {
    super();
  }

  @Override
  protected void loadSelf(XMLFile file, Element elem) {
    super.loadSelf(file, elem);
    annotations = file.getElementMap(elem, "Annotations","Annotation");
    proteins.clear();
    proteins.addAll(Arrays.asList(file.getElementValues(elem,"Proteins","ProteinID")));
  }

  @Override
  protected void saveSelf(XMLFile file, Element elem) {
    super.saveSelf(file, elem);
    file.setElementMap(elem, "Annotations", "Annotation", annotations);
    file.setElementValues(elem,"Proteins","ProteinID",proteins.toArray(new String[0]));
  }

  public void fillGOAOther() {
    final String suffix = "_0";
    if (proteins.size() > 0 && children.size() > 0) {
      Set<String> childProteins = new HashSet<String> ();
      GOAClassificationEntry other = null;
      for (IGOEntry child : children) {
        if (child.getAccession().endsWith(suffix)){
          other = (GOAClassificationEntry)child;
          continue;
        }
        GOAClassificationEntry goaEntry = (GOAClassificationEntry) child;
        goaEntry.fillGOAOther();
        childProteins.addAll(goaEntry.getProteins());
      }

      if (proteins.size() > childProteins.size() &&  childProteins.size() > 0) {
        Set<String> others = new HashSet<String> (proteins);
        others.removeAll(childProteins);

        if (other == null){
          try {
            other = this.getClass().newInstance();
          }
          catch (IllegalAccessException ex) {
            throw new IllegalStateException(ex.getMessage());
          }
          catch (InstantiationException ex) {
            throw new IllegalStateException(ex.getMessage());
          }
        }
        else {
          children.remove(other);
        }

        other.setAccession(getAccession() + "_0");
        other.setName("other " + getName());
        other.setDefinition("The proteins exist in " + getName() +
                            " but not exist in its children");
        other.getProteins().addAll(others);

        children.add(other);
      }
    }
  }

  @Override
  public String toString() {
    return super.toString() + " : " + proteins.size();
  }

  public void removeEmptyGOEntry(){
    IGOEntry[] list = children.toArray(new IGOEntry[0]);
    for (IGOEntry child : list) {
      GOAClassificationEntry entry = (GOAClassificationEntry)child;
      if (entry.proteins.size() == 0){
        children.remove(child);
      }
      else{
        entry.removeEmptyGOEntry();
      }
    }
  }

}
