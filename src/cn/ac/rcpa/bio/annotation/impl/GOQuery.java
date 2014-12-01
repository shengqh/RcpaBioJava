package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.ac.rcpa.bio.annotation.IGOEntry;
import cn.ac.rcpa.bio.database.AbstractDBApplication;

public class GOQuery
    extends AbstractDBApplication {

  public GOQuery(Connection connection) {
    super(connection);
  }

  public void fillGOEntry(IGOEntry goEntry,
                          String rootGO, int level) {
    goEntry.setAccession(rootGO);

    fillGODetail(goEntry);

    Map<String, IGOEntry> goaMap = new HashMap<String, IGOEntry> ();

    fillGO(goEntry, goaMap, level);
  }

  private void fillGO(IGOEntry entry, Map<String, IGOEntry> goaMap, int level) {
    try {
      IGOEntry existedEntry = goaMap.get(entry.getAccession());
      if (existedEntry != null && existedEntry.getChildren().size() > 0) {
        return;
      }

      goaMap.put(entry.getAccession(), entry);

      PreparedStatement query = getSelectGOQuery(entry.getAccession());
      if (query.execute()) {
        ResultSet rs = query.getResultSet();
        try {
          while (rs.next()) {
            String goAccession = rs.getString(1);
            IGOEntry child;
            if (goaMap.containsKey(goAccession)) {
              child = goaMap.get(goAccession);
            }
            else {
              try {
                child = entry.getClass().newInstance();
              }
              catch (Exception ex) {
                throw new IllegalStateException(ex);
              }
              child.setAccession(goAccession);
              child.setName(rs.getString(2));
              child.setDefinition(rs.getString(3));
              goaMap.put(goAccession, child);
            }
            entry.getChildren().add(child);
          }
        }
        finally {
          rs.close();
          query.close();
        }
      }
    }
    catch (SQLException ex) {
      throw new IllegalStateException(ex);
    }

    level--;
    if (level <= 0) {
      return;
    }

    for (IGOEntry child : entry.getChildren()) {
      fillGO(child, goaMap, level);
    }
  }

  private void fillGODetail(IGOEntry goEntry) throws
      IllegalStateException {
    try {
      final String sql = "SELECT NAME, DEFINITION FROM GO WHERE ACCESSION=?";
      PreparedStatement query = connection.prepareStatement(sql);
      query.setString(1, goEntry.getAccession());
      ResultSet rs = query.executeQuery();
      if (rs.next()) {
        goEntry.setName(rs.getString(1));
        goEntry.setDefinition(rs.getString(2));
      }
      rs.close();
    }
    catch (SQLException ex) {
      throw new IllegalStateException(ex);
    }
  }

  private PreparedStatement getSelectGOQuery(String accession) throws
      SQLException {
    final String sql = "select go.accession, go.name, go.definition from go, gopath where gopath.father_go=? and gopath.level=1 and gopath.child_go=go.accession";
    PreparedStatement result = connection.prepareStatement(sql);
    result.setString(1, accession);
    return result;
  }

}
