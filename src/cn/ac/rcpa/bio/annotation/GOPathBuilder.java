package cn.ac.rcpa.bio.annotation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jdom.JDOMException;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */

public class GOPathBuilder {
  public static void main(String[] args) throws Exception {
    File totalFile = new File("");

    if (args.length > 0){
      totalFile = new File(args[0]);
    }

    if (!totalFile.exists()) {
      printPrompt();
      return;
    }

    build(totalFile.getAbsolutePath());
  }

  public static String build(String totalFile) throws
      Exception {
    final String result = totalFile + ".path";

    GOEntryMap totalMap = getTotalGOEntry(totalFile);
    PrintWriter pw = new PrintWriter(new FileWriter(result));
    try {
      String[] accessions = totalMap.getAccessions();
      for (String accession : accessions) {
        if (!accession.startsWith("GO:")) {
          continue;
        }

        pw.println(accession + "\t" + accession + "\t" + 0);
        printPath(pw, accession, accession, 0, totalMap);
      }
    }
    finally {
      pw.close();
    }
    return result;
  }

  private static void printPath(PrintWriter pw, String accession,
                                String fromAccession,
                                int level,
                                GOEntryMap totalMap) {
    IGOEntry entry = totalMap.getEntry(fromAccession);

    for (String parent : entry.getParentAccessions()) {
      pw.println(accession + "\t" + parent + "\t" + (level + 1));
      printPath(pw, accession, parent, level + 1, totalMap);
    }
  }

  /**
   * printPrompt
   */
  private static void printPrompt() {
    System.err.println("GOPathBuilder go_XXXXXX_termdb.xml");
  }

  private static GOEntryMap getTotalGOEntry(String filename) throws
      Exception {
    System.out.print("Getting total entries from file " + filename + " ... ");
    GOEntryMap goMap = GOEntryMap.getGOEntryMapFromXMLFile(filename);
    System.out.println("succeed.");
    return goMap;
  }
}
