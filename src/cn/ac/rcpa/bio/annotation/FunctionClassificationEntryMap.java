package cn.ac.rcpa.bio.annotation;

import java.util.HashMap;
import java.util.Map;

public class FunctionClassificationEntryMap {
  private Map<String, Object> entries = new HashMap<String, Object>();

  public FunctionClassificationEntryMap() {
  }

  public FunctionClassificationEntry[] get(String accessNumber){
    if (!entries.containsKey(accessNumber)){
      return new FunctionClassificationEntry[0];
    }
    else{
      return (FunctionClassificationEntry[]) entries.get(accessNumber);
    }
  }

  public String[] keySet(){
    return (String[])entries.keySet().toArray(new String[0]);
  }

  public boolean containsKey(String accessNumber){
    return entries.containsKey(accessNumber);
  }

  public void put(String accessNumber, FunctionClassificationEntry[] entry){
    entries.put(accessNumber, entry);
  }

  public void clear(){
    entries.clear();
  }

  public void putAll(FunctionClassificationEntryMap map){
    entries.putAll(map.entries);
  }
}
