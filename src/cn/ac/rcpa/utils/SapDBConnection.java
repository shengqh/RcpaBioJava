package cn.ac.rcpa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.ac.rcpa.models.IDatabaseConnection;

public class SapDBConnection
    implements IDatabaseConnection {
  private static Class driver = null;

  private String server;
  private String database;
  private String username;
  private String password;

  private Connection connection;

  public SapDBConnection(String server, String database,
                         String username, String password) {
    doInit(server, database, username, password);
  }

  private static void initDriver() {
    if (driver == null) {
      try {
        driver = Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
      }
      catch (ClassNotFoundException ex) {
        throw new IllegalStateException(ex.getException());
      }
    }
  }

  private void doInit(String server, String database,
                   String username, String password) {
    initDriver();

    this.server = server;
    this.database = database;
    this.username = username;
    this.password = password;
  }

  public void init(String server, String database,
                   String username, String password) {
    doInit(server, database, username, password);
  }

  public Connection getConnection() {
    if (connection == null){
      if (driver == null ||
          server == null ||
          database == null ||
          username == null ||
          password == null) {
        throw new IllegalStateException("Call init function first!");
      }

      System.out.print("Connecting to database ... ");
      try {
        connection = DriverManager.getConnection(
            "jdbc:sapdb://" + server + "/" + database,
            username,
            password);
      }
      catch (SQLException ex) {
        throw new IllegalStateException("Connect to database error : " +
                                        ex.getMessage());
      }
      System.out.println("succeed.");
    }

    return connection;
  }
}
