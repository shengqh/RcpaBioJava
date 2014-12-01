package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ac.rcpa.bio.annotation.FunctionClassificationEntry;
import cn.ac.rcpa.bio.annotation.FunctionClassificationEntryMap;
import cn.ac.rcpa.bio.annotation.IAnnotationQuery;
import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.models.IInterruptable;

public class GOAnnotationQueryByGOSlim
    extends AbstractDBApplication implements IAnnotationQuery, IInterruptable {
  private FunctionClassificationEntryMap goa = new
      FunctionClassificationEntryMap();
  private PreparedStatement goaQuery;
  private String accessNumberFieldName;
  protected boolean interrupted = false;

  public GOAnnotationQueryByGOSlim(String accessNumberFieldName) throws
      ClassNotFoundException, SQLException {
    super(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION));
    this.accessNumberFieldName = accessNumberFieldName;
  }

  public GOAnnotationQueryByGOSlim(Connection connection,
                                   String accessNumberFieldName) {
    super(connection);
    this.accessNumberFieldName = accessNumberFieldName;
  }

  public FunctionClassificationEntry[] getAnnotation(String accessNumber) {
    interrupted = false;

    if (goa.containsKey(accessNumber)) {
      return goa.get(accessNumber);
    }

    validateConnection();

    List<FunctionClassificationEntry> entries = new ArrayList<
        FunctionClassificationEntry> ();

    try {
      PreparedStatement query = getSelectGOAPreparedStatement();
      query.setString(1, accessNumber);

      if (query.execute()) {
        ResultSet rs = query.getResultSet();
        while (rs.next()) {
          entries.add(getFunctionClassificationEntryFromDB(rs));
        }
      }
    }
    catch (SQLException ex) {
      throw new IllegalStateException(ex.getMessage());
    }

    final FunctionClassificationEntry[] result = (FunctionClassificationEntry[])
        entries.toArray(new FunctionClassificationEntry[0]);
    goa.put(accessNumber, result);
    return result;
  }

  public FunctionClassificationEntryMap getAnnotation(String[] acNumbers) {
    FunctionClassificationEntryMap annotationsInCache = new
        FunctionClassificationEntryMap();

    final String[] acNumberNotInCache = getAccessNumberNotInCache(acNumbers,
        annotationsInCache);

    FunctionClassificationEntryMap result = getAnnotationFromDB(
        acNumberNotInCache);

    result.putAll(annotationsInCache);

    return result;
  }

  private String[] getAccessNumberNotInCache(String[] acNumbers,
                                             FunctionClassificationEntryMap
                                             annotationsInCache) {
    List<String> result = new ArrayList<String> ();

    for (int i = 0; i < acNumbers.length; i++) {
      if (goa.containsKey(acNumbers[i])) {
        annotationsInCache.put(acNumbers[i], goa.get(acNumbers[i]));
      }
      else {
        result.add(acNumbers[i]);
      }
    }

    return (String[]) result.toArray(new String[0]);
  }

  private FunctionClassificationEntryMap getAnnotationFromDB(String[] iprs) {
    validateConnection();

    FunctionClassificationEntryMap result = new FunctionClassificationEntryMap();
    for (int i = 0; i < iprs.length; i++) {
      FunctionClassificationEntry[] annotationEntries = getAnnotation(iprs[i]);
      result.put(iprs[i], annotationEntries);
    }
    return result;
  }

  private FunctionClassificationEntry getFunctionClassificationEntryFromDB(
      ResultSet rs) throws SQLException {
    FunctionClassificationEntry result = new FunctionClassificationEntry();

    result.setAccess_number(rs.getString(accessNumberFieldName));
    result.setDb_object_name(rs.getString("db_object_name"));
    result.setAspect(rs.getString("aspect"));
    result.setClassification(rs.getString("classification"));

    return result;
  }

  private PreparedStatement getSelectGOAPreparedStatement() throws
      SQLException {
    if (goaQuery == null) {
      String sql = "select distinct GOA." + accessNumberFieldName + ", GOA.db_object_name, GOA.aspect, goslim.classification from GOA, goslim where GOA." +
          accessNumberFieldName + "=? and GOA.goid=goslim.child_go";
      goaQuery = connection.prepareStatement(sql);
    }
    return goaQuery;
  }

  /*
    private PreparedStatement getSelectGOAPreparedStatement() throws
        SQLException{
      if (goaQuery == null) {
        String sql = "select distinct PROTEOMICS.GOA." + accessNumberFieldName +", PROTEOMICS.GOA.db_object_name, PROTEOMICS.GOA.aspect, PROTEOMICS.goslim.classification from PROTEOMICS.GOA, PROTEOMICS.goslim where PROTEOMICS.GOA." + accessNumberFieldName + "=? and PROTEOMICS.GOA.goid=PROTEOMICS.goslim.child_go";
        goaQuery = connection.prepareStatement(sql);
      }
      return goaQuery;
    }
   */

  public static void main(String[] args) throws SQLException,
      ClassNotFoundException {
    GOAnnotationQueryByGOSlim goa = new GOAnnotationQueryByGOSlim("synonym");
    FunctionClassificationEntry[] entries = goa.getAnnotation("IPI00231252");
    System.out.println(entries);
  }

  public void interrupt() {
    interrupted = true;
  }
}
