package cn.ac.rcpa.bio.utils;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

public class DatabaseUtils {
  private DatabaseUtils() {
  }

  public static Set<String> getTableNames(Connection conn) throws SQLException {
    DatabaseMetaData dbmd = conn.getMetaData();
    return getTableNames(conn, dbmd.getUserName());
  }

  public static Set<String> getTableNames(Connection conn, String schemaPattern)
      throws SQLException {
    DatabaseMetaData dbmd = conn.getMetaData();

    Set<String> result = new LinkedHashSet<String>();
    ResultSet rs = dbmd.getTables("", schemaPattern, "*", null);
    try {
      while (rs.next()) {
        result.add(rs.getString(3));
      }
    } finally {
      rs.close();
    }

    return result;
  }

  public static String createTempTable(Connection conn, String prefix,
      String[] accessNumbers) {

    try {
      final Set<String> tables = getTableNames(conn, "TEMP");

      String tblName;
      while (true) {
        tblName = prefix
            + "_"
            + new SimpleDateFormat("yyyyMMddhhmmssSSS")
                .format(new java.util.Date());
        if (!tables.contains(tblName)) {
          break;
        }
      }
      tblName = "TEMP." + tblName;

      final String sql = "CREATE TABLE " + tblName
          + " (ACCESSNUMBER VARCHAR(50) PRIMARY KEY)";
      conn.createStatement().execute(sql);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName
          + " VALUES(?)");
      for (String ac : accessNumbers) {
        ps.setString(1, ac);
        ps.execute();
      }
      ps.close();
      return tblName;
    } catch (SQLException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
  }

  public static String createIntegerTempTable(Connection conn, String prefix,
      String[] accessNumbers) {

    try {
      final Set<String> tables = getTableNames(conn, "TEMP");

      String tblName;
      while (true) {
        tblName = prefix
            + "_"
            + new SimpleDateFormat("yyyyMMddhhmmssSSS")
                .format(new java.util.Date());
        if (!tables.contains(tblName)) {
          break;
        }
      }
      tblName = "TEMP." + tblName;

      final String sql = "CREATE TABLE " + tblName
          + " (ACCESSNUMBER INTEGER PRIMARY KEY)";
      conn.createStatement().execute(sql);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName
          + " VALUES(?)");
      for (String ac : accessNumbers) {
        ps.setString(1, ac);
        ps.execute();
      }
      ps.close();
      return tblName;
    } catch (SQLException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
  }

  public static void appendDatabaseLog(Connection conn, String database) {
    final String sql = "CREATE TABLE DatabaseLog (STORE_DATE TIMESTAMP, DB_NAME_VERSION LONG)";
    try {
      conn.createStatement().execute(sql);
    } catch (SQLException e) {
    }

    final String appendLogSql = "INSERT INTO DatabaseLog VALUES(?,?)";
    try {
      PreparedStatement ps = conn.prepareStatement(appendLogSql);
      ps.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
      ps.setString(2, new File(database).getName());
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void printHeader(PrintWriter pw, ResultSet rs)
      throws SQLException {
    ResultSetMetaData rsmd = rs.getMetaData();
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      pw.print((i == 1 ? rsmd.getColumnName(i) : "\t" + rsmd.getColumnName(i)));
    }
    pw.println();
  }

  public static void printRecord(PrintWriter pw, ResultSet rs)
      throws SQLException {
    ResultSetMetaData rsmd = rs.getMetaData();
    printRecord(pw, rs, rsmd);
  }

  public static void printRecord(PrintWriter pw, ResultSet rs,
      ResultSetMetaData rsmd) throws SQLException {
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      String value = rs.getString(i);
      if (value == null) {
        value = "";
      }
      pw.print((i == 1 ? value : "\t" + value));
    }
    pw.println();
  }

  public static void printRecords(PrintWriter pw, ResultSet rs)
      throws SQLException {
    ResultSetMetaData rsmd = rs.getMetaData();
    while (rs.next()) {
      printRecord(pw, rs, rsmd);
    }
    pw.println();
  }

}
