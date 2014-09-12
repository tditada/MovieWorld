package ar.edu.itba.paw.g4.util.persist;

import static ar.edu.itba.paw.g4.util.persist.DatabaseConnectionManager.getConnection;

import java.sql.Connection;
import java.sql.SQLException;

import ar.edu.itba.paw.g4.exception.DatabaseException;

public abstract class DatabaseConnection {
	public void run() {
		try {
			Connection connection = getConnection();
			handleConnection();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	protected abstract void handleConnection() throws SQLException;
}
