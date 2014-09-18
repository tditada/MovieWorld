package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getEmailAddress;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getInt;
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
import ar.edu.itba.paw.g4.util.EmailAddress;
import ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnection;
import ar.edu.itba.paw.g4.util.persist.sql.PSQLStatement;

import com.google.common.collect.Lists;

public class PSQLUserDAO implements UserDAO {
	private static final String TABLE_NAME_ID = "users";
	private static final String FIRST_NAME_ID = "firstName";
	private static final String LAST_NAME_ID = "lastName";
	private static final String EMAIL_ADDR_ID = "emailAddr";
	private static final String PASSWD_ID = "password";
	private static final String BIRTH_DATE_ID = "birthDate";
	private static final String ID_ATTR_ID = "userId";

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
				List<String> columns = Lists.newArrayList(FIRST_NAME_ID,
						LAST_NAME_ID, EMAIL_ADDR_ID, PASSWD_ID, BIRTH_DATE_ID);
				if (!user.isPersisted()) {
					query = insertQuery(TABLE_NAME_ID, columns);
				} else {
					columns.add(ID_ATTR_ID);
					query = updateQuery(TABLE_NAME_ID, columns);
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
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ ID_ATTR_ID + "=?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(id);

				ResultSet results = statement.executeQuery();
				if (results.next()) {
					return getUserFromResults(results);
				}
				return null;// TODO: ver si no habria que tirar exception aca
							// (usuario inexistente)
			}
		};
		return connection.run();
	}

	@Override
	public User getByEmail(final EmailAddress email) {
		checkArgument(email, notNull());
		DatabaseConnection<User> connection = new DatabaseConnection<User>() {

			@Override
			protected User handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ EMAIL_ADDR_ID + " = ?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(email.asTextAddress());

				ResultSet results = statement.executeQuery();
				if (results.next()) {
					return getUserFromResults(results);
				}
				return null;
			}
		};
		return connection.run();
	}

	private User getUserFromResults(ResultSet results) throws SQLException {
		User user = User.builder()
				.withFirstName(getString(results, FIRST_NAME_ID))
				.withLastName(getString(results, LAST_NAME_ID))
				.withPassword(getString(results, PASSWD_ID))
				.withEmail(getEmailAddress(results, EMAIL_ADDR_ID))
				.withBirthDate(getDateTime(results, BIRTH_DATE_ID)).build();
		user.setId(getInt(results, ID_ATTR_ID));
		return user;
	}
}
