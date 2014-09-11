package ar.edu.itba.paw.g4.util.persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.DateTime;

import com.google.common.base.Converter;

import ar.edu.itba.paw.g4.util.EmailAddress;
import ar.edu.itba.paw.g4.util.EnumHelpers;

public class QueryHelpers {
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
		String[] stringValues = getArray(resultSet, "genres", String[].class);
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
}
