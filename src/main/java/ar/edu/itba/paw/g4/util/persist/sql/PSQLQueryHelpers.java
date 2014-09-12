package ar.edu.itba.paw.g4.util.persist.sql;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

public class PSQLQueryHelpers {
	public static String insertQuery(String tableName, List<String> columnNames) {
		checkArgument(tableName, neitherNullNorEmpty());
		checkArgument(columnNames, notNull(), notEmptyColl());

		return "INSERT " + tableName + toColumnNamesStr(columnNames)
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
