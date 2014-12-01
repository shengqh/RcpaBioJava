package cn.ac.rcpa.bio.annotation.impl;

import java.sql.SQLException;
import java.util.Map;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;

public class TestSubcellularLocationSwissProtQuery
    extends TestCase {

	public void testNothing(){}
	
  public void ttestGetSubcellularLocation() throws SQLException {
    SubcellularLocationSwissProtQuery subcellularLocationSwissProtQuery = new SubcellularLocationSwissProtQuery(
        RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION));
    String[] acs = new String[] {
        "Q08170", "Q14202", "Q91VB2"};
    Map<String,
        String> map = subcellularLocationSwissProtQuery.
        getSubcellularLocation(acs);
    System.out.println(map);
    assertEquals(2, map.size());
    assertTrue(map.get("Q08170").indexOf("Nuclear") != -1 );
    assertTrue(map.get("Q14202").indexOf("Nuclear") != -1);
    assertTrue(map.get("Q91VB2").indexOf("Cytoplasmic") != -1);
  }

}
