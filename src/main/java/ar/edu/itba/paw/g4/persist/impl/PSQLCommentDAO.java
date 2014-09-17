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
	private static final String TABLE_NAME_ID = "comments";
	private static final String SCORE_ID = "score";
	private static final String TEXT_ID = "txt";
	private static final String CREAT_DATE_ID = "creationDate";
	private static final String USER_ATTR_ID = "userId";
	private static final String MOVIE_ATTR_ID = "movieId";
	private static final String ID_ATTR_ID = "commentId";

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
				List<String> columns = Lists.newArrayList(SCORE_ID, TEXT_ID,
						CREAT_DATE_ID, USER_ATTR_ID, MOVIE_ATTR_ID);
				if (!comment.isPersisted()) {
					query = insertQuery(TABLE_NAME_ID, columns);
				} else {
					columns.add(ID_ATTR_ID);
					query = updateQuery(TABLE_NAME_ID, columns);
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
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ ID_ATTR_ID + "=?";
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
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ MOVIE_ATTR_ID + "=?";
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
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ USER_ATTR_ID + "=?";
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
		Movie movie = movieDAO.getById(getInt(results, MOVIE_ATTR_ID));
		User user = userDAO.getById(getInt(results, USER_ATTR_ID));

		Comment comment = Comment.builder()
				.withScore(getInt(results, SCORE_ID))
				.withText(getString(results, TEXT_ID))
				.withCreationDate(getDateTime(results, CREAT_DATE_ID))
				.withMovie(movie).withUser(user).build();
		comment.setId(getInt(results, ID_ATTR_ID));
		return comment;
	}
}
