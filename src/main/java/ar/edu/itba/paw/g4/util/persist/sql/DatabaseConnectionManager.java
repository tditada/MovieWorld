package ar.edu.itba.paw.g4.util.persist.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.util.persist.DatabaseSettings;

public class DatabaseConnectionManager {
	private static final String DB_URL_ID = "url";
	private static final String DB_AUTOCOMMIT_ID = "autocommit";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Properties properties = DatabaseSettings.getInstance()
					.getProperties();
			String url = properties.getProperty(DB_URL_ID);
			boolean autocommit = Boolean.valueOf(properties
					.getProperty(DB_AUTOCOMMIT_ID));
			connection = DriverManager.getConnection(url, properties);
			connection.setAutoCommit(autocommit); // TODO: check!
		} catch (Exception e) {// TODO: check error handling!
			throw new DatabaseException(e);
		}
		return connection;
	}
}
