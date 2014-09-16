package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getInt;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getString;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.insertQuery;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.updateQuery;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnection;
import ar.edu.itba.paw.g4.util.persist.sql.PSQLStatement;

import com.google.common.collect.Lists;

public class PSQLCommentDAO implements CommentDAO {
	private static final String TABLE_NAME = "comments";

	private static final CommentDAO instance = new PSQLCommentDAO();

	public static CommentDAO getInstance() {
		return instance;
	}

	private static final MovieDAO movieDAO = PSQLMovieDAO.getInstance();
	private static final UserDAO userDAO = PSQLUserDAO.getInstance();

	@Override
	public void save(final Comment comment) {
		checkArgument(comment, notNull());

		new DatabaseConnection<Void>() {

			@Override
			protected Void handleConnection(Connection connection)
					throws SQLException {
				String query;
				List<String> columns = Lists.newArrayList("score", "txt",
						"creationDate", "userId", "movieId");
				if (!comment.isPersisted()) {
					query = insertQuery(TABLE_NAME, columns);
				} else {
					columns.add("commentId");
					query = updateQuery(TABLE_NAME, columns);
				}

				PSQLStatement statement = new PSQLStatement(connection, query,
						true);
				statement.addParameter(comment.getScore());
				statement.addParameter(comment.getText());
				statement.addParameter(comment.getCreationDate());
				statement.addParameter(comment.getUser().getId());
				statement.addParameter(comment.getMovie().getId());

				if (comment.isPersisted()) {
					statement.addParameter(comment.getId());
				}

				int result = statement.executeUpdate();

				if (!comment.isPersisted()) {
					comment.setId(result);
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
	public Comment getById(final int id) {
		DatabaseConnection<Comment> connection = new DatabaseConnection<Comment>() {

			@Override
			protected Comment handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE commentId=?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(id);

				ResultSet results = statement.executeQuery();
				if (results.next()) {
					return getCommentFromResults(results);
				}
				return null; // TODO: ver si no habria que tirar exception aca
								// (comentario inexistente)
			}
		};
		return connection.run();
	}

	@Override
	public List<Comment> getAllByMovie(final Movie movie) {
		DatabaseConnection<List<Comment>> connection = new DatabaseConnection<List<Comment>>() {

			@Override
			protected List<Comment> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE movieId=?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(movie.getId());

				ResultSet results = statement.executeQuery();
				List<Comment> comments = new LinkedList<>();
				while (results.next()) {
					comments.add(getCommentFromResults(results));
				}
				return comments;
			}
		};
		return connection.run();
	}

	@Override
	public List<Comment> getAllByUser(final User user) {
		DatabaseConnection<List<Comment>> connection = new DatabaseConnection<List<Comment>>() {

			@Override
			protected List<Comment> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE userId=?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(user.getId());

				ResultSet results = statement.executeQuery();
				List<Comment> comments = new LinkedList<>();
				while (results.next()) {
					comments.add(getCommentFromResults(results));
				}
				return comments;
			}
		};
		return connection.run();
	}

	private Comment getCommentFromResults(ResultSet results)
			throws SQLException {
		Movie movie = movieDAO.getById(getInt(results, "movieId"));
		User user = userDAO.getById(getInt(results, "userId"));

		Comment comment = Comment.builder().withScore(getInt(results, "score"))
				.withText(getString(results, "txt"))
				.withCreationDate(getDateTime(results, "creationDate"))
				.withMovie(movie).withUser(user).build();
		comment.setId(getInt(results, "commentId"));
		return comment;
	}
}
