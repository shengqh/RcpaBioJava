package cn.ac.rcpa.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;

public class RcpaObjectUtils {
  private RcpaObjectUtils() {
  }

  static public final void assertInstanceOf(Object obj, Class classType) {
    if (obj == null) {
      throw new IllegalArgumentException("Argument is null!");
    }

    if (!classType.isInstance(obj)) {
      throw new IllegalArgumentException("Argument should be instance of "
          + classType.getName() + ", but now is " + obj.getClass().getName());
    }
  }

  static public <E> E[] asArray(Collection collection, E[] a) {
    return asList(collection).toArray(a);
  }

  @SuppressWarnings("unchecked")
	static public <E> List<E> asList(Collection collection) {
    List<E> result = new ArrayList<E>();
    for (Object obj : collection) {
      result.add((E) obj);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
	static public <E> List<E> asList(Collection collection, E[] a) {
    List<E> result = new ArrayList<E>();
    for (Object obj : collection) {
      result.add((E) obj);
    }
    return result;
  }

  static public <E extends Comparable<E>> E[] asSortedArray(
      Collection collection, E[] a) {
    return asSortedList(collection, a).toArray(a);
  }

  static public <E extends Comparable<E>> List<E> asSortedList(
      Collection collection, E[] a) {
    List<E> result = asList(collection);

    Collections.sort(result);

    return result;
  }

  static public String[] toStringArray(Collection collection) {
    String[] result = new String[collection.size()];
    int i = 0;
    for (Object obj : collection) {
      result[i++] = obj.toString();
    }
    return result;
  }

  static public final Object[] getObjectsFromEnumeration(
      java.util.Enumeration aenum) {
    Map<String, Object> map = new HashMap<String, Object>();
    for (; aenum.hasMoreElements();) {
      Object nextObj = aenum.nextElement();
      map.put(nextObj.toString(), nextObj);
    }

    List<String> keys = new ArrayList<String>(map.keySet());
    Collections.sort(keys);

    Object[] result = new Object[keys.size()];
    for (int i = 0; i < keys.size(); i++) {
      result[i] = map.get(keys.get(i));
    }

    return result;
  }

  static public boolean objectEquals(Object obj, String expectFile) {
    try {
      XStream xstream = new XStream();
      if (new File(expectFile).exists()) {
        File actualFile = new File(expectFile + ".actual");

        FileWriter fw = new FileWriter(actualFile);
        xstream.toXML(obj, fw);
        fw.close();

        boolean result = FileUtils.contentEquals(new File(expectFile),
            actualFile);

        if (result) {
          actualFile.delete();
        } else {
          System.err.println("Object equals failed, check actual file "
              + actualFile.getAbsolutePath());
        }

        return result;
      } else {
        xstream.toXML(obj, new FileWriter(expectFile));
        throw new IllegalStateException("Check " + expectFile + " first!");
      }
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
  }
}
