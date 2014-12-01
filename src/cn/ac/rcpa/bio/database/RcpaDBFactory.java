package cn.ac.rcpa.bio.database;

import java.sql.Connection;
import java.util.HashMap;

public class RcpaDBFactory {
	private HashMap<RcpaDatabaseType, RcpaDBConnection> connections;

	private static RcpaDBFactory instance;

	public static RcpaDBFactory getInstance() {
		if (instance == null) {
			instance = new RcpaDBFactory();
		}
		return instance;
	}

	private RcpaDBFactory() {
		connections = new HashMap<RcpaDatabaseType, RcpaDBConnection>();

		connections.put(RcpaDatabaseType.ANNOTATION, new RcpaDBConnection(
				"config/RcpaQueryDatabase.conf", "com.sap.dbtech.jdbc.DriverSapDB",
				"jdbc:sapdb://192.168.88.201/RCPA", "annotation", "annotation", true));

		connections.put(RcpaDatabaseType.PROTEIN, new RcpaDBConnection(
				"config/RcpaEditDatabase.conf", "com.sap.dbtech.jdbc.DriverSapDB",
				"jdbc:sapdb://192.168.88.201/RCPA", "protein", "protein", false));

		connections.put(RcpaDatabaseType.TEST, new RcpaDBConnection(
				"config/RcpaTestDatabase.conf", "com.sap.dbtech.jdbc.DriverSapDB",
				"jdbc:sapdb://192.168.88.201/RCPA", "test", "test", false));

		connections.put(RcpaDatabaseType.MLPP, new RcpaDBConnection(
				"config/RcpaMLPPDatabase.conf", "com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/mlpp", "mlpp", "mlpp", true));
//		connections.put(RcpaDatabaseType.MLPP, new RcpaDBConnection(
//				"config/RcpaMLPPDatabase.conf", "com.sap.dbtech.jdbc.DriverSapDB",
//				"jdbc:sapdb://192.168.88.201/RCPA", "mlpp", "mlpp", true));
	}

	public Connection getConnection(RcpaDatabaseType rdt) {
		if (connections.containsKey(rdt)) {
			return connections.get(rdt).getConnection();
		}
		throw new IllegalArgumentException("Cannot find connection for " + rdt);
	}

	public static void main(String[] args) {
		RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.MLPP);
	}
}
