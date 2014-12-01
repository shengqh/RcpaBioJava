package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cn.ac.rcpa.bio.annotation.GOAClassificationEntry;
import cn.ac.rcpa.bio.annotation.IAnnotationQueryByGO;
import cn.ac.rcpa.bio.annotation.IGOEntry;
import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.bio.utils.DatabaseUtils;

public class GeneGoAnnotationQuery extends AbstractDBApplication implements
    IAnnotationQueryByGO {
  public GeneGoAnnotationQuery(Connection connection) {
    super(connection);
  }

  public GeneGoAnnotationQuery() {
    super(RcpaDBFactory.getInstance()
        .getConnection(RcpaDatabaseType.ANNOTATION));
  }

  public GOAClassificationEntry getAnnotation(String rootGO, int level,
      String[] acNumbers) throws SQLException {
    validateConnection();

    GOAClassificationEntry result = new GOAnnotationQueryTreeBuilder(connection)
        .getGOAEntryTree(rootGO, level);

    fillAnnotation(result, acNumbers);

    return result;
  }

  public void fillAnnotation(GOAClassificationEntry rootEntry,
      String[] acNumbers) throws SQLException {
    validateConnection();

    Map<String, IGOEntry> goaMap = rootEntry.getGOEntryMap();

    String tmpACTableName = DatabaseUtils.createIntegerTempTable(connection,
        "AC", acNumbers);

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
    final String sql = "SELECT DISTINCT GENE2GO.GENE_ID, " + tmpGOTableName
        + ".ACCESSNUMBER from GENE2GO, GOPATH, " + tmpACTableName + ", "
        + tmpGOTableName + " where " + "GOPATH.FATHER_GO=" + tmpGOTableName
        + ".ACCESSNUMBER" + " AND " + "GENE2GO.GO_ID=GOPATH.CHILD_GO" + " AND "
        + "GENE2GO.GENE_ID=" + tmpACTableName + ".ACCESSNUMBER";
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

  public static void main(String[] args) throws Exception {
    GOAClassificationEntry entry = new GeneGoAnnotationQuery().getAnnotation(
        "GO:0005575", 2, new String[] { "814630" });
    entry.saveToFile("data/814630.xml");
  }
}
