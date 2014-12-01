package cn.ac.rcpa.bio.annotation.impl;

import java.sql.SQLException;
import java.util.Map;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;

public class TestSubcellularLocationIPIQuery extends TestCase {

  public void testNothing(){}
  
  public void ttestGetSubcellularLocation() throws SQLException {
    SubcellularLocationIPIQuery subcellularLocationIPIQuery = new SubcellularLocationIPIQuery(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION));
    String[] acs = new String[]{"IPI00000015", "IPI00029484"};
    Map<String, String> map = subcellularLocationIPIQuery.getSubcellularLocation(acs);
    assertEquals("Nuclear", map.get("IPI00000015"));
    assertEquals("Nuclear (By similarity)", map.get("IPI00029484"));
  }

}
