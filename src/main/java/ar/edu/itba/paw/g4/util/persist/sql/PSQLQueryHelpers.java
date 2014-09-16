package ar.edu.itba.paw.g4.util.persist.sql;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.util.EmailAddress;
import ar.edu.itba.paw.g4.util.EnumHelpers;
import ar.edu.itba.paw.g4.util.persist.Orderings;

import com.google.common.base.Converter;

public class PSQLQueryHelpers {
	public static String asSQLOrdering(Orderings ordering) {
		switch (ordering) {
		case ASC:
			return "ASC";
		case DESC:
			return "DESC";
		}
		throw new IllegalArgumentException("Not a valid Ordering");
		/*
		 * will never happen though
		 */
	}

	public static Timestamp asTimestamp(DateTime date) {
		return new Timestamp(date.getMillis());
	}

	public static DateTime getDateTime(ResultSet resultSet, String columnName)
			throws SQLException {
		Timestamp timestamp = resultSet.getTimestamp(columnName);
		return new DateTime(timestamp);
	}

	public static <T> T[] getArray(ResultSet resultSet, String columnName,
			Class<T[]> clazz) throws SQLException {
		T[] array = clazz.cast(resultSet.getArray(columnName).getArray());
		return array;
	}

	public static <E extends Enum<E>> Iterable<E> getEnum(ResultSet resultSet,
			String columnName, Converter<String, E> converter)
			throws SQLException {
		String[] stringValues = getArray(resultSet, columnName, String[].class);
		return EnumHelpers.valuesOf(converter, stringValues);
	}

	public static EmailAddress getEmailAddress(ResultSet resultSet,
			String columnName) throws SQLException {
		return EmailAddress.build(resultSet.getString(columnName));
	}

	public static String getString(ResultSet resultSet, String columnName)
			throws SQLException {
		return resultSet.getString(columnName);
	}

	public static int getInt(ResultSet resultSet, String columnName)
			throws SQLException {
		return resultSet.getInt(columnName);
	}

	public static boolean getBoolean(ResultSet resultSet, String columnName)
			throws SQLException {
		return resultSet.getBoolean(columnName);
	}

	public static String insertQuery(String tableName, List<String> columnNames) {
		checkArgument(tableName, neitherNullNorEmpty());
		checkArgument(columnNames, notNull(), notEmptyColl());

		return "INSERT INTO " + tableName + toColumnNamesStr(columnNames)
				+ " VALUES" + getVariablesStr(columnNames.size()) + ";";
	}

	public static String updateQuery(String tableName, List<String> columnNames) {
		checkArgument(tableName, neitherNullNorEmpty());
		checkArgument(columnNames, notNull(), notEmptyColl());

		return "UPDATE " + tableName + toColumnNamesStr(columnNames)
				+ " SET VALUES" + getVariablesStr(columnNames.size()) + ";";
	}

	private static String toColumnNamesStr(List<String> columnNames) {
		checkArgument(columnNames, notNull(), notEmptyColl());
		String colNamesStr = "";
		for (String columnName : columnNames) {
			colNamesStr += columnName + ",";
		}
		return "(" + colNamesStr.substring(0, colNamesStr.length() - 1) + ")";
	}

	private static String getVariablesStr(int length) {
		checkArgument(length > 0);
		String varsStr = "";
		for (int i = 0; i < length; i++) {
			varsStr += "?,";
		}
		return "(" + varsStr.substring(0, varsStr.length() - 1) + ")";
	}
}
