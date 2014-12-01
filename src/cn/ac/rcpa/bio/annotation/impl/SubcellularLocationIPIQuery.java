package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.annotation.ISubcellularLocationQuery;
import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.IPIAccessNumber2SwissProtQuery;

public class SubcellularLocationIPIQuery
    extends AbstractDBApplication implements ISubcellularLocationQuery {
  private IPIAccessNumber2SwissProtQuery ipi2spQuery;
  private SubcellularLocationSwissProtQuery spQuery;

  public SubcellularLocationIPIQuery(Connection connection) {
    super(connection);

    ipi2spQuery = new IPIAccessNumber2SwissProtQuery(connection);
    spQuery = new SubcellularLocationSwissProtQuery(connection);
  }

  public Map<String, String> getSubcellularLocation(
      String[] acNumbers) throws SQLException {
    final Map<String,
        String> sp2ipi = ipi2spQuery.getSwissProt2IPIMap(acNumbers);

    final Set<String> spAccessNumbers = new HashSet<String> (sp2ipi.keySet());

    final Map<String,
        String> spResult = spQuery.getSubcellularLocation(spAccessNumbers.
        toArray(new String[0]));

    Map<String, String> result = new HashMap<String, String> ();
    for (String sp : spResult.keySet()) {
      result.put(sp2ipi.get(sp), spResult.get(sp));
    }

    return result;
  }
}
