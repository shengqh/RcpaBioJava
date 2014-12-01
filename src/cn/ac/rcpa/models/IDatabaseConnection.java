package cn.ac.rcpa.models;

import java.sql.Connection;

public interface IDatabaseConnection {
  public void init(String server, String database,
                   String userName, String password);

  public Connection getConnection();
}
