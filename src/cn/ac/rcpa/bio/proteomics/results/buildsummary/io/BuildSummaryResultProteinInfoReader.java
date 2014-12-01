package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.bio.proteomics.ProteinInfoType;

public class BuildSummaryResultProteinInfoReader {
  private IAccessNumberParser acParser;
  private Set<ProteinInfoType> requiredTypes;

  public BuildSummaryResultProteinInfoReader(SequenceDatabaseType dbType, Collection<ProteinInfoType> requiredTypes) {
    this.requiredTypes = new LinkedHashSet<ProteinInfoType>(requiredTypes);
    this.acParser = AccessNumberParserFactory.getParser(dbType);
  }

  public Map<String, Map<ProteinInfoType, String>> read(String filename) throws IOException {
    Map<String, Map<ProteinInfoType, String>> result = new LinkedHashMap<String, Map<ProteinInfoType, String>>();
    BufferedReader br = new BufferedReader(new FileReader(filename));
    try {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.startsWith("$")) {
          break;
        }
      }

      while (line != null) {
        line = line.trim();

        if (line.length() == 0) {
          break;
        }

        if (line.startsWith("$")) {
          String[] lines = line.split("\t");
          if (lines.length < 7) {
            throw new IllegalStateException(line
                + " is not a valid protein line");
          }

          final String acNumber = acParser.getValue(lines[1]);

          final Map<ProteinInfoType,String> proteinInfos = getProteinInfoMap(lines);

          result.put(acNumber, proteinInfos);
        }

        line = br.readLine();
      }
    } finally {
      br.close();
    }

    return result;
  }

  private Map<ProteinInfoType, String> getProteinInfoMap(String[] lines) {
    final LinkedHashMap<ProteinInfoType, String> result = new LinkedHashMap<ProteinInfoType, String>();
    for(ProteinInfoType aType:requiredTypes){
      String value = "";
      switch(aType){
        case Reference:
          value = lines[1].trim();
          break;
        case PepCount:
          value = lines[2].trim();
          break;
        case UniquePepCount:
          value = lines[3].trim();
          break;
        case CoverPercent:
          value = lines[4].trim();
          break;
        case MW:
          value = lines[5].trim();
          break;
        case PI:
          value = lines[6].trim();
          break;
        default:
          throw new RuntimeException("Unknown how to get protein info type " + aType + " from protein line");
      }
      result.put(aType, value);
    }
    return result;
  }
}
