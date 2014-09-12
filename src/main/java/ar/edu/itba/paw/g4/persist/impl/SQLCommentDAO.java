package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.DatabaseConnectionManager.getConnection;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.asTimestamp;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getInt;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getString;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.UserDAO;

public class SQLCommentDAO implements CommentDAO {
	private static final CommentDAO instance = new SQLCommentDAO();

	public static CommentDAO getInstance() {
		return instance;
	}

	private static final MovieDAO movieDAO = SQLMovieDAO.getInstance();
	private static final UserDAO userDAO = SQLUserDAO.getInstance();

	@Override
	public void save(Comment comment) {
		checkArgument(comment, notNull());

		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO Comments"
							+ "(score, txt, creationDate, userId, movieId)"
							+ " VALUES (?,?,?,?,?);");
			statement.setInt(1, comment.getScore());
			statement.setString(2, comment.getText());
			statement.setTimestamp(3, asTimestamp(comment.getCreationDate()));
			statement.setInt(4, comment.getUser().getId());
			statement.setInt(5, comment.getMovie().getId());

			int result = statement.executeUpdate(); /*
													 * TODO: what to do with
													 * this?
													 */
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Comment getById(int id) {
		Comment comment = null;
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM Comments WHERE commentId=?;");
			statement.setInt(1, id);

			ResultSet results = statement.executeQuery();
			Movie movie = movieDAO.getById(getInt(results, "movieId"));
			User user = userDAO.getById(getInt(results, "userId"));

			comment = Comment.builder().withScore(getInt(results, "score"))
					.withText(getString(results, "txt")).withMovie(movie)
					.withUser(user).build();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return comment;
	}
}
