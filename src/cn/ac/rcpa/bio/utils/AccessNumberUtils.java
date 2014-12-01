package cn.ac.rcpa.bio.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class AccessNumberUtils {
  public AccessNumberUtils() {
  }

  public static String getAccessNumber(Collection<String> acNumbers,
      SequenceDatabaseType dbType) {
    IAccessNumberParser parser = AccessNumberParserFactory.getParser(dbType);

    LinkedHashSet<String> acSet = new LinkedHashSet<String>();

    for (String ac : acNumbers) {
      acSet.add(parser.getValue(ac));
    }

    ArrayList<String> acList = new ArrayList<String>(acSet);

    Collections.sort(acList);

    return StringUtils.join(acList.iterator(), "\n");
  }

  public static String[] loadFromFile(String acNumberFile,
      boolean bSkipFirstLine) throws IOException {
    String[] lines = RcpaFileUtils.readFile(acNumberFile, false);
    List<String> result = new ArrayList<String>();
    int iFirstLine = bSkipFirstLine ? 1 : 0;
    for (int i = iFirstLine; i < lines.length; i++) {
      String line = lines[i].trim();
      if (line.length() == 0) {
        break;
      }

      result.addAll(parseAccessNumber(line));
    }

    return result.toArray(new String[0]);
  }

  public static List<String> parseAccessNumber(String line) {
    List<String> result = new ArrayList<String>();
    String[] subids = line.split("\\s");
    for (String subid : subids) {
      subid = subid.trim();
      if (subid.endsWith(",")) {
        subid = subid.substring(0, subid.length() - 1);
      }
      if (subid.length() > 1) {
        result.add(subid.trim());
      }
    }
    return result;
  }
}
