package cn.ac.rcpa.bio.database.ebi.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;

import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.bio.database.ebi.protein.ProteinEntryParser;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;
import cn.ac.rcpa.bio.utils.DatabaseUtils;

/*
 * 创建日期 2004-6-9
 *
 * @author Li Long, Sheng Quan-Hu
 *
 */
public class StoreProteinEntryFileToDB {
  private Connection connection;

  public StoreProteinEntryFileToDB(Connection connection) {
    this.connection = connection;
  }

  public void initilizeDatabaseByFiles(String[] filenames) throws
      BioException, IOException, SQLException, ClassNotFoundException {
    if (filenames.length == 0) {
      throw new IllegalArgumentException("Parameter filenames of StoreProteinEntryFileToDB.initilizeDatabaseByFiles should not be null!");
    }

    final ProteinEntryToDB stdb = new ProteinEntryToDB(connection);

    stdb.dropTable();
    System.out.println("Deleting database ok! Now beginning store file ...");
    stdb.createTable();
    try {
      appendFilesToDB(filenames, stdb);
    }
    finally {
      stdb.createIndex();
      stdb.disconnect();
    }

    System.out.println("Finished !");
  }

  private void appendFilesToDB(String[] filenames, ProteinEntryToDB stdb) throws
      SQLException, BioException, NoSuchElementException, FileNotFoundException,
      IOException {
    final ProteinEntryParser spfd = new ProteinEntryParser();
    for (int i = 0; i < filenames.length; i++) {
      System.out.println("Processing " + filenames[i] + " ...");
      spfd.open(filenames[i]);
      while (spfd.hasNext()) {
        ProteinEntry newEntry = spfd.getNextEntry();
        stdb.storeToDB(newEntry);
      }
      DatabaseUtils.appendDatabaseLog(connection, filenames[i]);
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("StoreProteinEntryFileToDB dataBaseFiles");
      return;
    }
    else {
      for (int j = 0; j < args.length; j++) {
        if (!new File(args[j]).exists()) {
          System.err.println("The dataBaseFile:" + args[j] +
                             " does not exist!");
          return;
        }
      }
    }

    new StoreProteinEntryFileToDB(RcpaDBFactory.getInstance().
                                  getConnection(RcpaDatabaseType.ANNOTATION)).
        initilizeDatabaseByFiles(args);
  }

}
