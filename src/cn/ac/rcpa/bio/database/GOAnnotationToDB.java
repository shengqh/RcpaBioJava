package cn.ac.rcpa.bio.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.ac.rcpa.bio.annotation.GOAnnotationEntry;
import cn.ac.rcpa.bio.utils.DatabaseUtils;

public class GOAnnotationToDB
    extends AbstractDBApplication {
  public GOAnnotationToDB(Connection connection) {
    super(connection);
  }

  public static void main(String[] args) throws ClassNotFoundException,
      SQLException, FileNotFoundException, IOException {
    if (args.length < 1) {
      printPrompt();
      return;
    }

    for (String filename : args) {
      if (!new File(filename).exists()) {
        System.err.println("File not exists : " + filename);
        return;
      }
    }

    new GOAnnotationToDB(RcpaDBFactory.getInstance().getConnection(
        RcpaDatabaseType.ANNOTATION)).store(args);
  }

  /**
   * @param filenames
   * @throws SQLException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void store(String[] filenames) throws SQLException,
      FileNotFoundException, IOException {
    createTables();
    try {
      for (String filename : filenames) {
        addFileToDB(filename);
        DatabaseUtils.appendDatabaseLog(connection, filename);
      }
    }
    finally {
      createIndex();
    }
  }

  private static void printPrompt() {
    System.err.println("GOAnnotationToDB goa_database [goa_database]");
  }

  private void addFileToDB(String dbfile) throws SQLException,
      FileNotFoundException, IOException {
    ResultSet rs = connection.createStatement().executeQuery(
        "SELECT COUNT(*) FROM GOA");
    rs.next();
    int id = rs.getInt(1);
    System.out.println("\nThere are " + id + " entries in database. Appending "
                       + dbfile + " begin ---");

    PreparedStatement ps = connection
        .prepareStatement(
            "INSERT INTO GOA VALUES(GOA_ID.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    BufferedReader br = new BufferedReader(new FileReader(dbfile));
    String line;
    while ( (line = br.readLine()) != null) {
      GOAnnotationEntry entry = GOAnnotationEntry.parse(line);
      if (entry == null) {
        System.err.println(line);
      }

      id++;

      if (id % 1000 == 0) {
        System.out.print(".");
      }

      ps.setString(1, entry.getDb());
      ps.setString(2, entry.getDb_object_id());
      ps.setString(3, entry.getDb_object_symbol());
      ps.setString(4, entry.getQualifier());
      ps.setString(5, entry.getGoid());
      ps.setString(6, entry.getDb_reference());
      ps.setString(7, entry.getEvidence());
      ps.setString(8, entry.getWith());
      ps.setString(9, entry.getAspect());
      ps.setString(10, entry.getDb_object_name());
      ps.setString(11, entry.getSynonym());
      ps.setString(12, entry.getDb_object_type());
      ps.setString(13, entry.getTaxon_ID());
      ps.setString(14, entry.getDate());
      ps.setString(15, entry.getAssigned_By());

      ps.execute();
    }
    br.close();

    System.out.println("\nAfter append, there are " + id
                       + " entries in database.");
  }

  private void dropTable() {
    try {
      Statement sm = connection.createStatement();
      sm.execute("DROP TABLE GOA");
      sm.execute("DROP SEQUENCE GOA_ID");
    }
    catch (Exception ex) {
    }
  }

  private void dropIndex() {
    try {
      Statement sm = connection.createStatement();
      sm.execute("DROP INDEX GOA_ASPECT");
      sm.execute("DROP INDEX GOA_DB");
      sm.execute("DROP INDEX GOA_DB_OBJECT_ID");
      sm.execute("DROP INDEX GOA_DB_OBJECT_SYMBOL");
      sm.execute("DROP INDEX GOA_GOID");
      sm.execute("DROP INDEX GOA_SYNONYM");
      sm.execute("DROP INDEX GOA_TAXON_ID");
    }
    catch (Exception ex) {
    }
  }

  private void createIndex() throws SQLException {
    Statement sm = connection.createStatement();
    sm.execute("CREATE INDEX GOA_ASPECT ON GOA(\"ASPECT\" ASC)");
    sm.execute("CREATE INDEX GOA_DB ON GOA(\"DB\" ASC)");
    sm.execute("CREATE INDEX GOA_DB_OBJECT_ID ON GOA(\"DB_OBJECT_ID\" ASC)");
    sm
        .execute(
            "CREATE INDEX GOA_DB_OBJECT_SYMBOL ON GOA(\"DB_OBJECT_SYMBOL\" ASC)");
    sm.execute("CREATE INDEX GOA_GOID ON GOA(\"GOID\" ASC)");
    sm.execute("CREATE INDEX GOA_SYNONYM ON GOA(\"SYNONYM\" ASC)");
    sm.execute("CREATE INDEX GOA_TAXON_ID ON GOA(\"TAXON_ID\" ASC)");
  }

  private void createTables() throws SQLException {
    dropTable();
    dropIndex();

    connection.createStatement().execute("CREATE SEQUENCE GOA_ID CACHE 100");

    String createSQL = "CREATE TABLE GOA" + "\n" + "("
        + "\"ID\" Integer NOT NULL," + "\"DB\" Varchar (30) ASCII,"
        + "\"DB_OBJECT_ID\" Varchar (30) ASCII,"
        + "\"DB_OBJECT_SYMBOL\" Varchar (30) ASCII,"
        + "\"QUALIFIER\" Varchar (30) ASCII," + "\"GOID\" Varchar (30) ASCII,"
        + "\"DB_REFERENCE\" Varchar (50) ASCII,"
        + "\"EVIDENCE\" Varchar (10) ASCII," + "\"WITH\" Varchar (50) ASCII,"
        + "\"ASPECT\" Varchar (10) ASCII,"
        + "\"DB_OBJECT_NAME\" Varchar(500) ASCII,"
        + "\"SYNONYM\" Varchar (30) ASCII,"
        + "\"DB_OBJECT_TYPE\"  Varchar (20) ASCII,"
        + "\"TAXON_ID\" Varchar (20) ASCII," + "\"DATE\" Varchar (10) ASCII,"
        + "\"ASSIGNED_BY\" Varchar (10) ASCII," + "PRIMARY KEY (\"ID\")" + ")";

    connection.createStatement().execute(createSQL);
  }
}
