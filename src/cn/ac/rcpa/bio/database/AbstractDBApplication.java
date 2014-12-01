/*
 * Created on 2005-1-20
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.database;

import java.sql.Connection;
import java.sql.SQLException;

abstract public class AbstractDBApplication {
  protected Connection connection;

  public AbstractDBApplication(Connection connection) {
    this.connection = connection;
  }

  final public void disconnect() throws SQLException {
    if (connection != null) {
      connection.close();
      connection = null;
    }
  }

  final public void validateConnection() throws IllegalStateException {
    if (connection == null) {
      throw new IllegalStateException("Call connect first! ");
    }
  }
}
