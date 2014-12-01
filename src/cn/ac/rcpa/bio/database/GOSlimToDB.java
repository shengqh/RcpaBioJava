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

public class GOSlimToDB extends AbstractDBApplication {
  public GOSlimToDB(Connection connection) {
    super(connection);
  }

  public static void main(String[] args) throws ClassNotFoundException,
      SQLException, FileNotFoundException, IOException {
    File goSlimFile = new File("");

    if (args.length > 0) {
      goSlimFile = new File(args[0]);
    }

    if (!goSlimFile.exists()) {
      printPrompt();
      return;
    }

    new GOSlimToDB(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION))
        .store(goSlimFile.getAbsolutePath());
  }

  public void store(String goSlimFile) throws ClassNotFoundException,
      SQLException, FileNotFoundException, IOException {
    createTables();
    try {
      addFileToDB(goSlimFile);
      DatabaseUtils.appendDatabaseLog(connection, goSlimFile);
    } finally {
      createIndex();
    }
  }

  private static void printPrompt() {
    System.err.println("GOSlimToDB goslim_XXX.map");
  }

  private void addFileToDB(String dbfile) throws SQLException,
      FileNotFoundException, IOException {
    System.out.println("Importing data ...");
    PreparedStatement ps = connection
        .prepareStatement("INSERT INTO GOSLIM VALUES(?,?,?)");
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
        ps.setString(1, parsed[3]);
        ps.setString(2, parsed[0]);
        ps.setString(3, parsed[1]);
      } catch (com.sap.dbtech.jdbc.exceptions.ValueOverflow ex) {
        System.out.println(line);
        throw ex;
      }

      ps.execute();
    }
    br.close();

    System.out.println("Import data succeed.");
  }

  private void dropTable() {
    try {
      Statement sm = connection.createStatement();
      sm.execute("DROP TABLE GOSLIM");
    } catch (Exception ex) {
    }
  }

  private void dropIndex() {
    try {
      Statement sm = connection.createStatement();
      sm.execute("DROP INDEX GOSLIM_CHILD");
      sm.execute("DROP INDEX GOSLIM_FATHER");
    } catch (Exception ex) {
    }
  }

  private void createIndex() throws SQLException {
    Statement sm = connection.createStatement();
    sm.execute("CREATE INDEX GOSLIM_CHILD ON GOSLIM(\"CHILD_GO\" ASC)");
    sm.execute("CREATE INDEX GOSLIM_FATHER ON GOSLIM(\"FATHER_GO\" ASC)");
  }

  private void createTables() throws SQLException {
    dropTable();
    dropIndex();

    System.out.print("Creating database ... ");
    String createSQL = "CREATE TABLE GOSLIM" + "\n" + "("
        + "\"CLASSIFICATION\" Varchar(200) ASCII,"
        + "\"CHILD_GO\" Varchar (50) ASCII,"
        + "\"FATHER_GO\" Varchar (50) ASCII" + ")";

    connection.createStatement().execute(createSQL);
    System.out.println("succeed.");
  }
}
