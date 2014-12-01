package cn.ac.rcpa.bio.database.ebi.protein.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

public class SwissProtProteinEntryQuery
    extends AbstractProteinEntryQuery {
  public SwissProtProteinEntryQuery(Connection connection){
    super(connection);
  }

  @Override
  public ProteinEntry getEntry(String identity) throws SQLException {
    String spid = getIDByEntryName(identity);
    return spid == null ? null : getProteinEntryByID(spid);
  }

  private String getIDByEntryName(String name) throws SQLException {
    final ResultSet rs = connection.createStatement()
        .executeQuery("select id from swissprot where entry_name like '"
                      + name + "'");
    if (rs.next()) {
      return rs.getString(1);
    }
    return null;
  }
}
