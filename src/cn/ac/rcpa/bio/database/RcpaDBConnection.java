package cn.ac.rcpa.bio.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RcpaDBConnection {
	private static Map<String, Class> drivers = new HashMap<String, Class>();

	private String optionFile;

	private String driver;

	private String driverUrl;

	private String user;

	private String password;

	private boolean exportConfigFile;

	public RcpaDBConnection(String optionFile, String driver, String driverUrl,
			String user, String password, boolean exportConfigFile) {
		this.optionFile = optionFile;
		this.driver = driver;
		this.driverUrl = driverUrl;
		this.user = user;
		this.password = password;
		this.exportConfigFile = exportConfigFile;
	}

	private Properties getOption() {
		Properties option = new Properties();
		try {
			option.load(new FileInputStream(optionFile));
		} catch (IOException ex) {
		}

		if (!option.containsKey("driver") || !option.containsKey("driverUrl")
				|| !option.containsKey("user") || !option.containsKey("password")) {
			option.put("driver", driver);
			option.put("driverUrl", driverUrl);
			option.put("user", user);
			option.put("password", password);

			if (exportConfigFile) {
				try {
					option.store(new FileOutputStream(optionFile), "RcpaDatabase");
				} catch (IOException ex) {
				}
			}
		}

		return option;
	}

	public Connection getConnection() {
		Properties option = getOption();

		String currentDriverName = option.getProperty("driver");
		if (!drivers.containsKey(currentDriverName)) {
			try {
				Class currentDriver = Class.forName(currentDriverName);
				drivers.put(currentDriverName, currentDriver);
			} catch (ClassNotFoundException ex) {
				throw new IllegalStateException(ex.getException());
			}
		}

		Connection result;
		System.out.print("Connecting to database ... ");
		try {
			result = DriverManager.getConnection((String) option
					.getProperty("driverUrl"), (String) option.getProperty("user"),
					(String) option.getProperty("password"));
		} catch (SQLException ex) {
			throw new IllegalStateException("Connect to database error : "
					+ ex.getMessage());
		}
		System.out.println("succeed.");

		return result;
	}
}
