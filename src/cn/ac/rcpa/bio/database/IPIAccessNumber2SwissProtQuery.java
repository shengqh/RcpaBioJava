package cn.ac.rcpa.bio.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.ac.rcpa.bio.utils.DatabaseUtils;

public class IPIAccessNumber2SwissProtQuery
    extends AbstractDBApplication {
  public IPIAccessNumber2SwissProtQuery(Connection connection) {
    super(connection);
  }

  private Map<String,
      String> getSwissProt2IPIMap(String tmpIPIACTableName) throws SQLException {
    final String sql = "select ACCESSNUMBER, db_references.primary_identifier from accession, db_references, "
        + tmpIPIACTableName
        + " where "
        + tmpIPIACTableName
        + ".ACCESSNUMBER=ac_number and"
        + " accession.swissprot_id=db_references.swissprot_id and"
        + " db_references.db='UniProtKB/Swiss-Prot'";

    HashMap<String, String> result = new HashMap<String, String> ();
    final ResultSet rs = connection.createStatement().executeQuery(sql);
    while (rs.next()) {
      final String ac = rs.getString(2);
      final int ipos = ac.indexOf('-');
      if (ipos == -1) {
        result.put(ac, rs.getString(1));
      }
      else {
        result.put(ac.substring(0, ipos), rs.getString(1));
      }
    }

    return result;
  }

  public Map<String,
      String> getSwissProt2IPIMap(String[] ipiAcNumbers) throws SQLException {
    final String tmpIPIACTableName = DatabaseUtils.createTempTable(connection,
        "IPI_AC", ipiAcNumbers);

    return getSwissProt2IPIMap(tmpIPIACTableName);
  }

  public Map<String,
      String> getIPI2SwissProtMap(String[] ipiAcNumbers) throws SQLException {
    Map<String, String> sp2ipiMap = getSwissProt2IPIMap(ipiAcNumbers);
    Map<String, String> result = new HashMap<String, String> ();

    for (String key : sp2ipiMap.keySet()) {
      result.put(sp2ipiMap.get(key), key);
    }

    for (String ipiAcNumber : ipiAcNumbers) {
      if (!result.containsKey(ipiAcNumber)) {
        result.put(ipiAcNumber, "");
      }
    }

    return result;
  }
}
