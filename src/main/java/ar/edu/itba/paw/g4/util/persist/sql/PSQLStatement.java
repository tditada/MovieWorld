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
import java.util.Collection;

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

	public <E extends Enum<E>> PSQLStatement addParameter(E value)
			throws SQLException {
		checkArgument(value, notNull());
		return addParameter(value.name()); // IMPORTANT: this is ok
	}

	public PSQLStatement addParameter(String sqlType, Collection<?> collection)
			throws SQLException {
		checkArgument(collection, notNull());
		Array elements = connection
				.createArrayOf(sqlType, collection.toArray());
		this.statement.setArray(++parameterCount, elements);
		return this;
	}

	public PSQLStatement addParameter(Collection<? extends Enum<?>> coll)
			throws SQLException {
		checkArgument(coll, notNull());
		// IMPORTANT: it's 'varchar' and not 'VARCHAR'
		return addParameter("varchar", coll);
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
