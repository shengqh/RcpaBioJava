package cn.ac.rcpa.bio.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TestIPIAccessNumber2SwissProtQuery extends TestCase {
	private IPIAccessNumber2SwissProtQuery query;

	private IPIAccessNumber2SwissProtQuery getQuery() {
		if (query == null) {
			query = new IPIAccessNumber2SwissProtQuery(RcpaDBFactory.getInstance()
					.getConnection(RcpaDatabaseType.ANNOTATION));
		}
		return query;
	}

	public void testNothing(){}
	
	public void ttestGetIPI2SwissProtMap() throws SQLException {
		String[] ipiAcNumbers = new String[] { "IPI00107952", "IPI00107960",
				"IPI00107953" };
		Map<String, String> expectedReturn = new HashMap<String, String>();
		expectedReturn.put("IPI00107952", "P08905");
		expectedReturn.put("IPI00107960", "P09568");
		expectedReturn.put("IPI00107953", "");
		Map<String, String> actualReturn = getQuery().getIPI2SwissProtMap(ipiAcNumbers);
		assertEquals(expectedReturn, actualReturn);
	}

	public void ttestGetSwissProt2IPIMap() throws SQLException {
		String[] ipiAcNumbers = new String[] { "IPI00107952", "IPI00107960",
				"IPI00107953" };
		Map<String, String> expectedReturn = new HashMap<String, String>();
		expectedReturn.put("P08905", "IPI00107952");
		expectedReturn.put("P09568", "IPI00107960");
		Map<String, String> actualReturn = getQuery().getSwissProt2IPIMap(ipiAcNumbers);
		assertEquals(expectedReturn, actualReturn);
	}
}
