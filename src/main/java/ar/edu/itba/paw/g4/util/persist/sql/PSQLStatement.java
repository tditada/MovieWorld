package ar.edu.itba.paw.g4.util.persist.sql;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.asTimestamp;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkState;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.joda.time.DateTime;

public class PSQLStatement {
	private Connection connection;
	private PreparedStatement statement;
	private int parameterCount = 0;
	private boolean update;

	public PSQLStatement(Connection connection, String sql, boolean update)
			throws SQLException {
		checkArgument(connection, notNull());
		checkArgument(sql, neitherNullNorEmpty());

		this.connection = connection;
		this.update = update;
		if (update) {
			this.statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
		} else {
			this.statement = connection.prepareStatement(sql);
		}
	}

	public PSQLStatement addParameter(String value) throws SQLException {
		checkArgument(value, notNull());
		this.statement.setString(++parameterCount, value);
		return this;
	}

	public PSQLStatement addParameter(int value) throws SQLException {
		this.statement.setInt(++parameterCount, value);
		return this;
	}

	public PSQLStatement addParameter(DateTime date) throws SQLException {
		checkArgument(date, notNull());
		this.statement.setTimestamp(++parameterCount, asTimestamp(date));
		return this;
	}

	public void addParameter(String sqlType, List<?> list) throws SQLException {
		checkArgument(list, notNull());
		Array elements = connection.createArrayOf(sqlType, list.toArray());
		this.statement.setArray(++parameterCount, elements);
	}

	public void addParameter(List<? extends Enum<?>> list) throws SQLException {
		checkArgument(list, notNull());
		// IMPORTANT: it's 'varchar' and not 'VARCHAR'
		Array elements = connection.createArrayOf("varchar", list.toArray());
		this.statement.setArray(++parameterCount, elements);
	}

	public int executeUpdate() throws SQLException {
		checkState(update);
		statement.executeUpdate();
		ResultSet result = statement.getGeneratedKeys();
		result.next();
		return (int) result.getLong(1); // TODO: Check this!
	}

	public ResultSet executeQuery() throws SQLException {
		checkState(!update);
		return statement.executeQuery();
	}

}
