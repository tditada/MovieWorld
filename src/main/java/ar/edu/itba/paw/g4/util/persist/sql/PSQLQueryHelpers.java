package ar.edu.itba.paw.g4.util.persist.sql;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import ar.edu.itba.paw.g4.util.persist.query.Conditionals;
import ar.edu.itba.paw.g4.util.persist.query.Orderings;

public class PSQLQueryHelpers {
	public static String selectAllQuery(String tableName,
			List<Pair<String, Conditionals>> conditionals,
			Pair<String, Orderings> ordering, Integer limit) {
		checkArgument(tableName, neitherNullNorEmpty());
		checkArgument(conditionals, notNull(), notEmptyColl());

		String condStr = "";
		for (Pair<String, Conditionals> condPair : conditionals) {
			condStr += condPair.getKey()
					+ conditionalToStr(condPair.getValue()) + "?";
		}

		return "SELECT * FROM " + tableName + " WHERE " + condStr
				+ " ORDER BY " + ordering.getLeft() + " "
				+ orderingToStr(ordering.getRight()) + " LIMIT "
				+ limitToStr(limit) + " ;";
	}

	private static String limitToStr(Integer limit) {
		return limit == null ? "ALL" : String.valueOf(limit.intValue());
	}

	private static String conditionalToStr(Conditionals conditional) {
		switch (conditional) {
		case EQ:
			return "=";
		case NEQ:
			return "<>";
		case SMALLER:
			return "<";
		case SEQ:
			return "<=";
		case BIGGER:
			return ">";
		case BEQ:
			return ">=";
		}
		throw new IllegalArgumentException("Invalid conditional");
	}

	private static String orderingToStr(Orderings ordering) {
		switch (ordering) {
		case ASC:
			return "ASC";
		case DESC:
			return "DESC";
		}
		throw new IllegalArgumentException("Invalid ordering");
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
