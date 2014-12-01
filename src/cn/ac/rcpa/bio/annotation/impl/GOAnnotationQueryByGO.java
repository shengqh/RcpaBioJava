package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cn.ac.rcpa.bio.annotation.GOAClassificationEntry;
import cn.ac.rcpa.bio.annotation.IAnnotationQueryByGO;
import cn.ac.rcpa.bio.annotation.IGOEntry;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.bio.utils.DatabaseUtils;

public class GOAnnotationQueryByGO extends GOAnnotationQueryTreeBuilder
    implements IAnnotationQueryByGO {
  private String accessNumberFieldName;

  public GOAnnotationQueryByGO(String accessNumberFieldName)
      throws ClassNotFoundException, SQLException {
    super(RcpaDBFactory.getInstance()
        .getConnection(RcpaDatabaseType.ANNOTATION));

    this.accessNumberFieldName = accessNumberFieldName;
  }

  public GOAnnotationQueryByGO(Connection connection,
      String accessNumberFieldName) {
    super(connection);

    this.accessNumberFieldName = accessNumberFieldName;
  }

  public GOAClassificationEntry getAnnotation(String rootGO, int level,
      String[] acNumbers) throws SQLException {
    validateConnection();

    GOAClassificationEntry result = getGOAEntryTree(rootGO, level);

    fillAnnotation(result, acNumbers);

    return result;
  }

  public void fillAnnotation(GOAClassificationEntry rootEntry,
      String[] acNumbers) throws SQLException {
    validateConnection();

    Map<String, IGOEntry> goaMap = rootEntry.getGOEntryMap();

    String tmpACTableName = DatabaseUtils.createTempTable(connection, "AC",
        acNumbers);

    String tmpGOTableName = DatabaseUtils.createTempTable(connection, "GO",
        goaMap.keySet().toArray(new String[0]));

    System.out.println("Fill annotation start : " + new java.util.Date());

    fillGOAByTempTable(goaMap, tmpACTableName, tmpGOTableName);

    System.out.println("Fill annotation end   : " + new java.util.Date());

    rootEntry.removeEmptyGOEntry();

    rootEntry.fillGOAOther();
  }

  private void fillGOAByTempTable(Map<String, IGOEntry> goaMap,
      String tmpACTableName, String tmpGOTableName) {
    final String sql = "SELECT DISTINCT GOA." + accessNumberFieldName + ", "
        + tmpGOTableName + ".ACCESSNUMBER from GOA, GOPATH, " + tmpACTableName
        + ", " + tmpGOTableName + " where " + "GOPATH.FATHER_GO="
        + tmpGOTableName + ".ACCESSNUMBER" + " AND "
        + "GOA.GOID=GOPATH.CHILD_GO" + " AND " + "GOA." + accessNumberFieldName
        + "=" + tmpACTableName + ".ACCESSNUMBER";
    try {
      ResultSet rs = connection.createStatement().executeQuery(sql);
      while (rs.next()) {
        GOAClassificationEntry entry = (GOAClassificationEntry) goaMap.get(rs
            .getString(2));
        entry.getProteins().add(rs.getString(1));
      }
    } catch (SQLException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
  }
}
