package cn.ac.rcpa.bio.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import cn.ac.rcpa.bio.utils.DatabaseUtils;

public class GOPathToDB extends AbstractDBApplication {
  public GOPathToDB(Connection connection) {
    super(connection);
  }

  public static void main(String[] args) throws ClassNotFoundException,
      SQLException, FileNotFoundException, IOException {
    File goPathFile = new File("");

    if (args.length > 0) {
      goPathFile = new File(args[0]);
    }

    if (!goPathFile.exists()) {
      printPrompt();
      return;
    }

    new GOPathToDB(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION))
        .store(goPathFile.getAbsolutePath());
  }

  public void store(String goPathFile) throws ClassNotFoundException,
      SQLException, FileNotFoundException, IOException {
    createTable();
    try {
      addFileToDB(goPathFile);
      DatabaseUtils.appendDatabaseLog(connection, goPathFile);
    } finally {
      createIndex();
    }
  }

  private static void printPrompt() {
    System.err.println("GOPathToDB go_XXXX.path");
  }

  private void addFileToDB(String dbfile) throws SQLException,
      FileNotFoundException, IOException {
    System.out.println("Importing GOPATH data ...");
    PreparedStatement ps = connection
        .prepareStatement("INSERT INTO GOPATH VALUES(?,?,?)");
    BufferedReader br = new BufferedReader(new FileReader(dbfile));
    String line;
    int icount = 0;
    while ((line = br.readLine()) != null) {
      icount++;
      if (icount % 1000 == 0) {
        System.out.println(icount);
      }

      String[] parsed = line.split("\t");
      try {
        ps.setString(1, parsed[0]);
        ps.setString(2, parsed[1]);
        ps.setString(3, parsed[2]);
      } catch (com.sap.dbtech.jdbc.exceptions.ValueOverflow ex) {
        System.out.println(line);
        throw ex;
      }

      ps.execute();
    }
    br.close();

    System.out.println("Import GOPATH data succeed.");
  }

  private void dropTable() {
    try {
      Statement sm = connection.createStatement();
      sm.execute("DROP TABLE GOPATH");
    } catch (Exception ex) {
    }
  }

  private void dropIndex() {
    try {
      Statement sm = connection.createStatement();
      sm.execute("DROP INDEX GOPATH_CHILD");
      sm.execute("DROP INDEX GOPATH_FATHER");
      sm.execute("DROP INDEX GOPATH_UNIQUE");
      sm.execute("DROP INDEX GOPATH_LEVEL");
    } catch (Exception ex) {
    }
  }

  private void createIndex() throws SQLException {
    Statement sm = connection.createStatement();
    sm.execute("CREATE INDEX GOPATH_CHILD ON GOPATH(\"CHILD_GO\" ASC)");
    sm.execute("CREATE INDEX GOPATH_FATHER ON GOPATH(\"FATHER_GO\" ASC)");
    sm
        .execute("CREATE INDEX GOPATH_UNIQUE ON GOPATH(\"CHILD_GO\" ASC, \"FATHER_GO\" ASC)");
    sm.execute("CREATE INDEX GOPATH_LEVEL ON GOPATH(\"LEVEL\" ASC)");
  }

  private void createTable() throws SQLException {
    dropTable();

    dropIndex();

    System.out.print("Creating GOPATH database ... ");

    String createSQL = "CREATE TABLE GOPATH" + "\n" + "("
        + "\"CHILD_GO\" Varchar (50) ASCII,"
        + "\"FATHER_GO\" Varchar (50) ASCII," + "\"LEVEL\" INTEGER )";

    connection.createStatement().execute(createSQL);

    System.out.println("succeed.");
  }

}
