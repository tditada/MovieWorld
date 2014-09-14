package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.selectAllQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import ar.edu.itba.paw.g4.util.persist.Entity;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;
import ar.edu.itba.paw.g4.util.persist.query.Condition;
import ar.edu.itba.paw.g4.util.persist.query.Conditionals;
import ar.edu.itba.paw.g4.util.persist.query.Orderings;
import ar.edu.itba.paw.g4.util.persist.query.Query;
import ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnection;
import ar.edu.itba.paw.g4.util.persist.sql.PSQLStatement;

public abstract class PSQLDAO<E extends Entity, A extends Enum<A>> implements
		GenericDAO<E> {

	public List<E> getMatches(final Query<A> query) {
		DatabaseConnection<List<E>> connection = new DatabaseConnection<List<E>>() {

			@Override
			protected List<E> handleConnection(Connection connection)
					throws SQLException {
				List<Pair<A, Condition>> conditions = query.getConditions();
				List<Pair<String, Conditionals>> sqlConditions = new ArrayList<>(
						conditions.size());
				List<Object> sqlParameters = new ArrayList<>(conditions.size());

				for (Pair<A, Condition> cond : conditions) {
					String columnName = getColumnName(cond.getKey());
					Condition condition = cond.getValue();

					sqlConditions.add(Pair.of(columnName,
							condition.getConditional()));
					sqlParameters.add(condition.getParameter());
				}

				Pair<A, Orderings> ordering = query.getOrdering();
				Pair<String, Orderings> sqlOrdering = Pair.of(
						getColumnName(ordering.getLeft()), ordering.getRight());

				String queryStr = selectAllQuery(getTableName(), sqlConditions,
						sqlOrdering, query.getQuantifier());

				PSQLStatement statement = new PSQLStatement(connection,
						queryStr, false);
				for (Object parameter : sqlParameters) {
					statement.addParameter(parameter);
				}

				ResultSet results = statement.executeQuery();
				List<E> entities = new LinkedList<>();
				while (results.next()) {
					entities.add(getEntityFrom(results));
				}
				if (entities.isEmpty()) {
					return null;
					/*
					 * TODO: ver si no habria que tirar exception aca (entidad
					 * inexistente)
					 */
				}
				return entities;
			}
		};
		return connection.run();
	}

	protected abstract String getTableName();

	protected abstract String getColumnName(A attribute);

	protected abstract E getEntityFrom(ResultSet result) throws SQLException;

}
