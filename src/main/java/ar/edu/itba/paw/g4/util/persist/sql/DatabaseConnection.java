package ar.edu.itba.paw.g4.util.persist.sql;

import static ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnectionManager.getConnection;

import java.sql.Connection;
import java.sql.SQLException;

import ar.edu.itba.paw.g4.exception.DatabaseException;

public abstract class DatabaseConnection<T> {

	public T run() {
		try {
			Connection connection = getConnection();
			T response = handleConnection(connection);
			connection.close();
			return response;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	protected abstract T handleConnection(Connection connection)
			throws SQLException;
}
