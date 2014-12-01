package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountMap<T>
    extends HashMap<T, Integer> {
  public CountMap() {
    super();
  }

  public CountMap(Collection<T> keys, int initValue) {
    super();
    for (T key : keys) {
      this.put(key, initValue);
    }
  }

  public void increase(T key, int count) {
    if (!this.containsKey(key)) {
      this.put(key, count);
    }
    else {
      this.put(key, this.get(key) + count);
    }
  }

  public void increase(T key) {
    increase(key, 1);
  }

  public int getTotalCount() {
    int result = 0;
    for (Integer value : this.values()) {
      result += value;
    }
    return result;
  }

  /**
   * 根据指定的classifiedNameMap，将原来的Key映射到新的ClassifiedKey上。
   *
   * @param classfiedNameMap<T,T> Map
   * @return CountMap<T>
   */
  public CountMap<T> getClassifiedCountMap(Map<T, T> classfiedNameMap) {
    final CountMap<T> result = new CountMap<T> (classfiedNameMap.
        values(), 0);

    for (T key : this.keySet()) {
      final T classifiedName = classfiedNameMap.get(key);
      result.increase(classifiedName, this.get(key));
    }

    return result;
  }

  public List<T> getKeyListByDecendingCount(){
    final List<T> result = new ArrayList<T>(this.keySet());
    final CountMap<T> thisMap = this;

    Collections.sort(result, new Comparator<T> () {
      public int compare(T key1, T key2) {
        return thisMap.get(key2) - thisMap.get(key1);
      }

      @Override
      public boolean equals(Object obj) {
        return false;
      }
    });

    return result;
  }

  public List<T> getKeyListByAscendingCount(){
    final List<T> result = new ArrayList<T>(this.keySet());
    final CountMap<T> thisMap = this;

    Collections.sort(result, new Comparator<T> () {
      public int compare(T key1, T key2) {
        return thisMap.get(key1) - thisMap.get(key2);
      }

      @Override
      public boolean equals(Object obj) {
        return false;
      }
    });

    return result;
  }
}
