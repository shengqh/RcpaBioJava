package cn.ac.rcpa.bio.annotation.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;

import cn.ac.rcpa.bio.annotation.AnnotationFactory;
import cn.ac.rcpa.bio.annotation.FunctionClassificationEntry;
import cn.ac.rcpa.bio.annotation.FunctionClassificationEntryMap;
import cn.ac.rcpa.bio.annotation.GOAAspectType;
import cn.ac.rcpa.bio.annotation.IAnnotationQuery;
import cn.ac.rcpa.bio.database.AccessNumberFastaParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.processor.AbstractFileProcessor;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class GOAnnotationProcessor
    extends AbstractFileProcessor {
  private GOAAspectType[] types;
  private SequenceDatabaseType dbType;

  public GOAnnotationProcessor(GOAAspectType[] types, SequenceDatabaseType dbType) {
    if (types == null || types.length == 0) {
      throw new IllegalArgumentException("Types should not be null or empty!");
    }

    this.types = new GOAAspectType[types.length];
    for (int i = 0; i < types.length; i++) {
      this.types[i] = types[i];
    }

    this.dbType = dbType;
  }

  public List<String> process(String originFile) throws
      Exception {
    List<String> result = new ArrayList<String> ();

    File fastaFile = new File(originFile);
    FunctionClassificationEntryMap entryMap = getFunctionClassificationEntryMap(
        fastaFile, dbType);
    if (entryMap.keySet().length > 0) {
      for (int i = 0; i < types.length; i++) {
        Map<String,
            List<String>>
            classificationMap = getClassificationMap(entryMap, types[i]);

        final File resultFile = new File(fastaFile.getParentFile(),
                                         "STATISTIC/" +
                                         RcpaFileUtils.changeExtension(
                                             fastaFile.getName(),
                                             types[i].getRoot().getName() +
                                             ".stat"));
        result.add(resultFile.getAbsolutePath());

        String[] cfs = (String[]) classificationMap.keySet().toArray(new String[
            0]);

        if (!resultFile.getParentFile().exists()) {
          resultFile.getParentFile().mkdirs();
        }

        PrintWriter goaWriter = new PrintWriter(new FileWriter(resultFile));
        goaWriter.println(types[i].getRoot().getName() + " classification");

        for (int j = 0; j < cfs.length; j++) {
          List acs = (List) classificationMap.get(cfs[j]);
          goaWriter.println(cfs[j] + "\t" + acs.size());
        }

        goaWriter.println();

        for (int j = 0; j < cfs.length; j++) {
          List acs = (List) classificationMap.get(cfs[j]);
          for (Iterator iter = acs.iterator(); iter.hasNext(); ) {
            String acNumber = (String) iter.next();
            goaWriter.println(cfs[j] + "\t" + acNumber);
          }
        }

        goaWriter.close();
      }
    }
    return result;
  }

  /**
   * 返回Map，结构如下：
   * --Key : Classification
   * --Value : List of AccessNumber
   *
   * @param entryMap FunctionClassificationEntryMap
   * @param type GOAAspectType
   * @return Map
   */
  private Map<String,
      List<String>> getClassificationMap(FunctionClassificationEntryMap
                                         entryMap,
                                         GOAAspectType type) {
    Map<String, List<String>> result = new HashMap<String, List<String>> ();

    String[] acs = entryMap.keySet();
    for (int i = 0; i < acs.length; i++) {
      FunctionClassificationEntry[] entries = entryMap.get(acs[i]);

      boolean bFound = false;
      for (int k = 0; k < entries.length; k++) {
        if (type == GOAAspectType.getGOAAspectType(entries[k].getAspect())) {
          String sClassification = entries[k].getClassification();

          addAccessNumberToMap(result, sClassification, acs[i]);
          bFound = true;
        }
      }

      if (!bFound) {
        addAccessNumberToMap(result, type.getUnknown().getName(), acs[i]);
      }
    }

    return result;
  }

  private void addAccessNumberToMap(Map<String, List<String>> result,
                                    String sClassification,
                                    String accessNumber) {
    if (!result.containsKey(sClassification)) {
      result.put(sClassification, new ArrayList<String> ());
    }
    List<String> acList = result.get(sClassification);
    acList.add(accessNumber);
  }

  private FunctionClassificationEntryMap getFunctionClassificationEntryMap(File
      fastaFile, SequenceDatabaseType dbType) throws ClassNotFoundException,
      SQLException, NoSuchElementException, BioException, FileNotFoundException {
    IAnnotationQuery query = AnnotationFactory.getAnnotationQuery(dbType);

    String[] acList = null;
    acList = AccessNumberFastaParser.getNames(fastaFile.getAbsolutePath(),
                                              dbType);

    return query.getAnnotation(acList);
  }

  /*
    final public void parseFile(File file) throws IOException {
      final String destDirectory = file.getParent() + File.separator + "GOA";
      new File(destDirectory).mkdir();

      for (int i = 0; i < types.length; i++) {
        GOAAspectType aspectType = types[i];
        BufferedReader br = new BufferedReader(new FileReader(file));
        PrintWriter goaWriter = new PrintWriter(new FileWriter(
            destDirectory + File.separator +
            file.getName() + "." + aspectType.getDescription()));

        String line;
        final HashMap hm = new HashMap();
        while ( (line = br.readLine()) != null) {
          String[] lines = line.trim().split("\t");
          if (GOAAspectType.getGOAAspectType(lines[3]) == aspectType) {
            goaWriter.println(lines[0] + "\t" + lines[1] + "\t" + lines[2]);
            if (hm.containsKey(lines[2])) {
              Integer temp = (Integer) hm.get(lines[2]);
              hm.put(lines[2], new Integer(temp.intValue() + 1));
            }
            else {
              hm.put(lines[2], new Integer(1));
            }
          }
        }
        br.close();
        goaWriter.close();

        PrintWriter stateWriter = new PrintWriter(new FileWriter(
            destDirectory + File.separator +
            file.getName() + "." + aspectType.getDescription() + ".stat"));
        for (Iterator iter = hm.keySet().iterator(); iter.hasNext(); ) {
          String key = (String) iter.next();
          stateWriter.println(key + "\t" + hm.get(key));
        }
        stateWriter.close();
      }
    }
   */
}
