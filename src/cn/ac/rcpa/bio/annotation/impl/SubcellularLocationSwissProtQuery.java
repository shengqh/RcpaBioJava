package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import cn.ac.rcpa.bio.annotation.ISubcellularLocationQuery;
import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.utils.DatabaseUtils;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: Get subcellular information based on given access number such
 * like Q08170 </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class SubcellularLocationSwissProtQuery
    extends AbstractDBApplication implements ISubcellularLocationQuery {
  public SubcellularLocationSwissProtQuery(Connection connection) {
    super(connection);
  }

  public Map<String, String> getSubcellularLocation(
      String[] acNumbers) throws SQLException {
    final String tmpACTableName = DatabaseUtils.createTempTable(connection,
        "SP_AC", acNumbers);

    final Statement stat = connection.createStatement();

    final String sql = getQueryString(tmpACTableName);

    final ResultSet rs = stat.executeQuery(sql);

    Map<String, String> result = new HashMap<String, String> ();
    while (rs.next()) {
      result.put(rs.getString(1), rs.getString(2));
    }

    return result;
  }

  private String getQueryString(String tmpACTableName) throws SQLException {
    return "select ACCESSION.AC_NUMBER, FREE_COMMENTS.CC_DETAILS from FREE_COMMENTS, ACCESSION, "
        + tmpACTableName
        + " where "
        + tmpACTableName
        + ".ACCESSNUMBER=ACCESSION.AC_NUMBER AND "
        + "ACCESSION.SWISSPROT_ID=FREE_COMMENTS.SWISSPROT_ID AND "
        + "FREE_COMMENTS.CC_TOPIC='SUBCELLULAR LOCATION'";
  }
}
