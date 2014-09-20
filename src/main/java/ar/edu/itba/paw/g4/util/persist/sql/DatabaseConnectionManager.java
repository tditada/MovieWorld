package ar.edu.itba.paw.g4.util.persist.sql;

import java.sql.Connection;
import java.sql.DriverManager;

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.util.persist.DatabaseSettings;

public class DatabaseConnectionManager {

	public static Connection getConnection() {
		Connection connection = null;
		try {

			String url = DatabaseSettings.getUrl();
			String username = DatabaseSettings.getUsername();
			String password = DatabaseSettings.getPassword();

			Class.forName(DatabaseSettings.getDriver());
			connection = DriverManager.getConnection(url, username, password);

			boolean autocommit = DatabaseSettings.getAutoCommit();
			connection.setAutoCommit(autocommit);
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
		return connection;
	}
}
