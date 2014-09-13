package ar.edu.itba.paw.g4.util.persist;

import static com.google.common.io.Closeables.closeQuietly;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseSettings {
	private static final String DB_PROPERTIES_FILENAME = "db.properties";
	private static final String DB_PROPERTIES_FILE_PATH = "/"
			+ DB_PROPERTIES_FILENAME;

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

	public Properties getProperties() {
		return properties;
	}
}
