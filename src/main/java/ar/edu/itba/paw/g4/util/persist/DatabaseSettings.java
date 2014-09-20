package ar.edu.itba.paw.g4.util.persist;

import static com.google.common.io.Closeables.closeQuietly;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseSettings {
	private static final String DB_PROPERTIES_FILENAME = "db.properties";
	private static final String DB_PROPERTIES_FILE_PATH = "/"
			+ DB_PROPERTIES_FILENAME;

	private static final String DB_URL_ID = "url";
	private static final String DB_AUTOCOMMIT_ID = "autocommit";
	private static final String DB_DRIVER_ID = "driver";
	private static final String DB_USER_ID = "user";
	private static final String DB_PASSWORD_ID = "password";

	private static DatabaseSettings instance = null;

	private Properties properties;

	public static DatabaseSettings getInstance() throws IOException {
		if (instance == null) {
			instance = new DatabaseSettings();
			instance.init();
		}
		return instance;
	}

	private void init() throws IOException {
		InputStream is = null;
		Exception exception = null;
		try {
			is = getClass().getResourceAsStream(DB_PROPERTIES_FILE_PATH);
			Properties properties = new Properties();
			properties.load(is);
			this.properties = properties;
		} catch (IOException e) {// TODO: check error handling!
			exception = e;
		} finally {
			closeQuietly(is); // TODO: check!
			if (exception != null) {
				throw new IOException(exception);
			}
		}
	}

	private static Properties getProperties() throws IOException {
		return getInstance().properties;
	}

	public static String getUrl() throws IOException {
		return getProperties().getProperty(DB_URL_ID);
	}

	public static String getDriver() throws IOException {
		return getProperties().getProperty(DB_DRIVER_ID);
	}

	public static String getPassword() throws IOException {
		return getProperties().getProperty(DB_PASSWORD_ID);
	}

	public static String getUsername() throws IOException {
		return getProperties().getProperty(DB_USER_ID);
	}

	public static boolean getAutoCommit() throws IOException {
		return Boolean.valueOf(getProperties().getProperty(DB_AUTOCOMMIT_ID));
	}
}
