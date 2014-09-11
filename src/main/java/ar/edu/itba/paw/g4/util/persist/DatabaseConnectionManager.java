package ar.edu.itba.paw.g4.util.persist;

import static com.google.common.io.Closeables.closeQuietly;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import ar.edu.itba.paw.g4.exception.DatabaseException;

public class DatabaseConnectionManager {
	private static final String DB_PROPERTIES_FILENAME = "db.conf";
	private static final String DB_PROPERTIES_FILE_PATH = "/"
			+ DB_PROPERTIES_FILENAME;

	private static final String DB_URL_ID = "url";

	private static final DatabaseConnectionManager instance = new DatabaseConnectionManager();

	public static DatabaseConnectionManager getInstance() {
		return instance;
	}

	public Connection getConnection() {
		InputStream is = null;
		Connection conn = null;
		try {
			is = getClass().getResourceAsStream(DB_PROPERTIES_FILE_PATH);
			Properties props = new Properties();
			props.load(is);
			String url = props.getProperty(DB_URL_ID);
			conn = DriverManager.getConnection(url, props);
		} catch (Exception e) {// TODO: check error handling!
			throw new DatabaseException(e);
		}
		closeQuietly(is);
		return conn;
	}
}
