package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getEmailAddress;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getString;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.insertQuery;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.updateQuery;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnection;
import ar.edu.itba.paw.g4.util.persist.sql.PSQLStatement;

import com.google.common.collect.Lists;

public class PSQLUserDAO implements UserDAO {
	private static final String TABLE_NAME = "users";

	private static final PSQLUserDAO instance = new PSQLUserDAO();

	public static PSQLUserDAO getInstance() {
		return instance;
	}

	@Override
	public void save(final User user) {
		checkArgument(user, notNull());

		new DatabaseConnection<Void>() {

			@Override
			protected Void handleConnection(Connection connection)
					throws SQLException {
				String query;
				List<String> columns = Lists.newArrayList("firstName",
						"lastName", "emailAddr", "password", "birthDate");
				if (!user.isPersisted()) {
					query = insertQuery(TABLE_NAME, columns);
				} else {
					columns.add("userId");
					query = updateQuery(TABLE_NAME, columns);
				}

				PSQLStatement statement = new PSQLStatement(connection, query,
						true);
				statement.addParameter(user.getFirstName());
				statement.addParameter(user.getLastName());
				statement.addParameter(user.getEmail().asTextAddress());
				statement.addParameter(user.getPassword());
				statement.addParameter(user.getBirthDate());

				if (user.isPersisted()) {
					statement.addParameter(user.getId());
				}

				int result = statement.executeUpdate();

				if (!user.isPersisted()) {
					user.setId(result);
				}

				connection.commit();
				return null;
			}

		}.run();/*
				 * TODO: preguntar si estaria bien meterlo en el constructor de
				 * DatabaseConnection
				 */
	}

	@Override
	public User getById(final int id) {
		DatabaseConnection<User> connection = new DatabaseConnection<User>() {

			@Override
			protected User handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE userId=?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(id);

				ResultSet results = statement.executeQuery();
				if (results.next()) {
					User user = User.builder()
							.withFirstName(getString(results, "firstName"))
							.withLastName(getString(results, "lastName"))
							.withPassword(getString(results, "password"))
							.withEmail(getEmailAddress(results, "emailAddr"))
							.withBirthDate(getDateTime(results, "birthDate"))
							.build();
					user.setId(id);
					return user;
				}
				return null;// TODO: ver si no habria que tirar exception aca
							// (usuario inexistente)
			}
		};
		return connection.run();
	}
}
