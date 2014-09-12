package ar.edu.itba.paw.g4.util.persist;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyArr;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

public class PSQLQueryHelpers {
	public static String insertQuery(String tableName, String... columnNames) {
		checkArgument(tableName, neitherNullNorEmpty());
		checkArgument(columnNames, notNull(), notEmptyArr());

		return "INSERT " + tableName + toColumnNamesStr(columnNames)
				+ " VALUES" + getVariablesStr(columnNames.length) + ";";
	}

	public static String updateQuery(String tableName, String... columnNames) {
		checkArgument(tableName, neitherNullNorEmpty());
		checkArgument(columnNames, notNull(), notEmptyArr());

		return "UPDATE " + tableName + toColumnNamesStr(columnNames)
				+ " SET VALUES" + getVariablesStr(columnNames.length) + ";";
	}

	private static String toColumnNamesStr(String[] columnNames) {
		checkArgument(columnNames, notNull(), notEmptyArr());
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
