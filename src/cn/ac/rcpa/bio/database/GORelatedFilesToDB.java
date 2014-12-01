package cn.ac.rcpa.bio.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.jdom.JDOMException;

import cn.ac.rcpa.bio.annotation.GOPathBuilder;
import cn.ac.rcpa.bio.annotation.GOSlimBuilder;

public class GORelatedFilesToDB {
  public GORelatedFilesToDB() {
  }

  public static void main(String[] args) throws JDOMException,
      FileNotFoundException, IOException, SQLException, ClassNotFoundException,
      Exception {
    File xmlFile = new File("");
    File oboFile = new File("");
    if (args.length >= 2) {
      xmlFile = new File(args[0]);
      oboFile = new File(args[1]);
    }

    if (!xmlFile.exists() || !oboFile.exists()) {
      System.err.println(
          "GOFileToDB go_YYYYMM-termdb.rdf-xml goslim_goa_rcpa.obo");
      return;
    }

    store(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION),
          xmlFile.getAbsolutePath(), oboFile.getAbsolutePath());
  }

  public static void store(Connection connection, String xmlFile,
                           String oboFile) throws Exception {
    final String pathFile = GOPathBuilder.build(xmlFile);
    final String slimFile = GOSlimBuilder.build(xmlFile, oboFile);

    new GOToDB(connection).store(xmlFile);
    new GOPathToDB(connection).store(pathFile);
    new GOSlimToDB(connection).store(slimFile);
  }
}
