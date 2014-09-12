package ar.edu.itba.paw.g4.util.persist.sql;

import static ar.edu.itba.paw.g4.util.persist.sql.SQLQueryHelpers.asTimestamp;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;

public class SQLStatement {
	private PreparedStatement statement;
	private int parameterCount = 0;
	private boolean update;

	public SQLStatement(Connection connection, String sql, boolean update)
			throws SQLException {
		checkArgument(connection, notNull());
		checkArgument(sql, neitherNullNorEmpty());

		this.update = update;
		if (update) {
			this.statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
		} else {
			this.statement = connection.prepareStatement(sql);
		}
	}

	public SQLStatement addParameter(String value) throws SQLException {
		checkArgument(value, notNull());
		this.statement.setString(++parameterCount, value);
		return this;
	}

	public SQLStatement addParameter(int value) throws SQLException {
		this.statement.setInt(++parameterCount, value);
		return this;
	}

	public SQLStatement addParameter(DateTime date) throws SQLException {
		checkArgument(date, notNull());
		this.statement.setTimestamp(++parameterCount, asTimestamp(date));
		return this;
	}

	public int executeUpdate() throws SQLException {
		checkState(update);
		return statement.executeUpdate();
	}

	public ResultSet executeQuery() throws SQLException {
		checkState(!update);
		return statement.executeQuery();
	}
}
