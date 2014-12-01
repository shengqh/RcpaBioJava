package cn.ac.rcpa.bio.database.ebi.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;

import cn.ac.rcpa.bio.database.AccessNumberFastaParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.ebi.protein.IProteinEntryQuery;
import cn.ac.rcpa.bio.database.ebi.protein.ProteinEntryQueryFactory;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

/**
 * 创建日期 2004-6-30
 * @author Li Long, Sheng Quan-Hu
 */

public class GetAnnotationCC {
  public static void getCCFromFasta(String fastaFilename, String resultFilename,
                                    SequenceDatabaseType dbType) throws IOException,
      NoSuchElementException, BioException,
      FileNotFoundException, SQLException, ClassNotFoundException {
    final IProteinEntryQuery ibdb = ProteinEntryQueryFactory.create(dbType);
    final PrintWriter pw = new PrintWriter(new FileWriter(resultFilename));
    String[] names = AccessNumberFastaParser.getNames(fastaFilename, dbType);
    ProteinEntry[] entries = ibdb.getEntries(names).values().toArray(new ProteinEntry[0]);
    HashMap[] cchashArray = ProteinAnnotation.getFreeCommentsAnnotation(entries);
    pw.print("ID");
    for (int k = 0; k < ProteinAnnotation.cctopicArray.length; k++) {
      pw.print("\t" + ProteinAnnotation.cctopicArray[k]);
    }
    pw.println();
    for (int i = 0; i < cchashArray.length; i++) {
      pw.print(cchashArray[i].get("ID") + "\t");
      for (int j = 0; j < ProteinAnnotation.cctopicArray.length; j++) {
        if (cchashArray[i].containsKey(ProteinAnnotation.cctopicArray[j])) {
          pw.print(cchashArray[i].get(ProteinAnnotation.cctopicArray[j]) + "\t");
        }
        else {
          pw.print("\t");
        }
      }
      pw.println();
    }
    pw.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("GetAnnotationCC ipiFastaFile OutPutFile");
      return;
    }
    else {
      if (!new File(args[0]).exists()) {
        System.err.println("The ipiFastaFile:" + args[0]
                           + " does not exist!");
        return;
      }
    }
    File out = new File(args[1]);
    if (out.exists()) {
      out.delete();
    }
    else {
      out.createNewFile();
    }
    getCCFromFasta(args[0], args[1], SequenceDatabaseType.IPI);
  }

}
