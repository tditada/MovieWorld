package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.insertQuery;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.updateQuery;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLResultHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLResultHelpers.getInt;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLResultHelpers.getString;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static java.util.Arrays.asList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.util.persist.query.Condition;
import ar.edu.itba.paw.g4.util.persist.query.Orderings;
import ar.edu.itba.paw.g4.util.persist.query.Query;
import ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnection;
import ar.edu.itba.paw.g4.util.persist.sql.PSQLStatement;

import com.google.common.collect.Lists;

public class PSQLCommentDAO extends PSQLDAO<Comment, CommentDAO.Attributes>
		implements CommentDAO {
	private static final String TABLE_NAME = "comments";

	private static final CommentDAO instance = new PSQLCommentDAO();

	private static final MovieDAO movieDAO = PSQLMovieDAO.getInstance();
	private static final UserDAO userDAO = PSQLUserDAO.getInstance();

	public static CommentDAO getInstance() {
		return instance;
	}

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
		@SuppressWarnings("unchecked")
		Query<CommentDAO.Attributes> query = Query
				.builder()
				.withConditions(
						asList(Pair.of(Attributes.ID, Condition.equalsVal(id))))
				.withOrdering(Pair.of(Attributes.ID, Orderings.ASC)).build();
		List<Comment> comment = getMatches(query);
		return comment != null ? comment.get(0) : null;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String getColumnName(Attributes attribute) {
		switch (attribute) {
		case MOVIE:
			return "movieId";
		case USER:
			return "userId";
		case ID:
			return "commentId";
		default:
			throw new UnsupportedOperationException(attribute
					+ "is unsupported");
		}
	}

	@Override
	protected Comment getEntityFrom(ResultSet result) throws SQLException {
		Movie movie = movieDAO.getById(getInt(result, "movieId"));
		User user = userDAO.getById(getInt(result, "userId"));

		Comment comment = Comment.builder().withScore(getInt(result, "score"))
				.withText(getString(result, "txt"))
				.withCreationDate(getDateTime(result, "creationDate"))
				.withMovie(movie).withUser(user).build();
		comment.setId(getInt(result, "commentId"));
		return comment;
	}
}
