package cn.ac.rcpa.bio.database.ebi.protein.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

public class IPIProteinEntryQuery extends AbstractProteinEntryQuery {
  private final static Pattern piid = Pattern.compile("(IPI\\d+)");

  public IPIProteinEntryQuery(Connection connection){
    super(connection);
  }

  @Override
  public ProteinEntry getEntry(String identity) throws SQLException {
    Matcher miid = piid.matcher(identity);
    if (miid.find()) {
      String ipiid = miid.group(1);
      String spid = getIDByAcNum(ipiid);
      return spid == null ? null : getProteinEntryByID(spid);
    }
    else {
      throw new IllegalArgumentException(identity
                                         + " is not a valid IPI id description");
    }
  }

  private String getIDByAcNum(String acNum) throws SQLException {
    final ResultSet rs = connection.createStatement()
        .executeQuery(
        "select swissprot_id from accession where ac_number like '"
        + acNum + "'");
    if (rs.next()) {
      return rs.getString(1);
    }

    return null;
  }
}
