package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.DatabaseConnectionManager.getConnection;
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

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;

public class SQLUserDAO implements UserDAO {
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
				query = "INSERT Users"
						+ "(firstName, lastName, emailAddr, password, birthDate)"
						+ "VALUES" + "(?,?,?,?,?)" + ";";
				//aca me haria falta hacer un get para el id para poderlo settear
			} else {
				query = "UPDATE Users"
						+ "(firstName, lastName, emailAddr, password, birthDate)"
						+ "SET VALUES" + "(?,?,?,?,?)" + ";";
			}

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail().asTextAddress());
			statement.setString(4, user.getPassword());
			statement.setTimestamp(5, asTimestamp(user.getBirthDate()));
			statement.setInt(6, user.getId());

			statement.execute();

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
					.prepareStatement("SELECT * FROM Users WHERE userId=?");
			statement.setInt(1, id);

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				user = User.builder().withId(id)
						.withFirstName(getString(results, "firstName"))
						.withLastName(getString(results, "lastName"))
						.withPassword(getString(results, "password"))
						.withEmail(getEmailAddress(results, "email"))
						.withBirthDate(getDateTime(results, "birthDate"))
						.withVip(getBoolean(results, "vip")).build();
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return user;
	}
}
