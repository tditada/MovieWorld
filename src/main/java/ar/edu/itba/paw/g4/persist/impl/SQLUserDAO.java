package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.DatabaseConnectionManager.getConnection;
import static ar.edu.itba.paw.g4.util.persist.PSQLQueryHelpers.insertQuery;
import static ar.edu.itba.paw.g4.util.persist.PSQLQueryHelpers.updateQuery;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.asTimestamp;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getBoolean;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getEmailAddress;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getString;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;

public class SQLUserDAO implements UserDAO {
	private static final String TABLE_NAME = "Users";

	private static final SQLUserDAO instance = new SQLUserDAO();

	public static SQLUserDAO getInstance() {
		return instance;
	}

	@Override
	public void save(User user) {
		checkArgument(user, notNull());

		try {
			Connection connection = getConnection();

			String query;
			if (!user.isPersisted()) {
				query = insertQuery(TABLE_NAME, "firstName", "lastName",
						"emailAddr", "password", "birthDate");
			} else {
				query = updateQuery(TABLE_NAME, "firstName", "lastName",
						"emailAddr", "password", "birthDate", "userId");
			}

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail().asTextAddress());
			statement.setString(4, user.getPassword());
			statement.setTimestamp(5, asTimestamp(user.getBirthDate()));

			if (!user.isPersisted()) {
				statement.setInt(6, user.getId());
			}

			int result = statement.executeUpdate(query,
					Statement.RETURN_GENERATED_KEYS);

			if (!user.isPersisted()) {
				user.setId(result);
			}

			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public User getById(int id) {
		User user = null;
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM " + TABLE_NAME
							+ " WHERE userId=?");
			statement.setInt(1, id);

			ResultSet results = statement.executeQuery();
			user = User.builder().withId(id)
					.withFirstName(getString(results, "firstName"))
					.withLastName(getString(results, "lastName"))
					.withPassword(getString(results, "password"))
					.withEmail(getEmailAddress(results, "email"))
					.withBirthDate(getDateTime(results, "birthDate"))
					.withVip(getBoolean(results, "vip")).build();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return user;
	}
}
