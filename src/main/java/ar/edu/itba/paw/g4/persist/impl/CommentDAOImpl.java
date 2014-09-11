package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.QueryHelpers.getBoolean;
import static ar.edu.itba.paw.g4.util.persist.QueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.QueryHelpers.getEmailAddress;
import static ar.edu.itba.paw.g4.util.persist.QueryHelpers.getEnum;
import static ar.edu.itba.paw.g4.util.persist.QueryHelpers.getInt;
import static ar.edu.itba.paw.g4.util.persist.QueryHelpers.getString;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static com.google.common.collect.FluentIterable.from;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.util.persist.DatabaseConnectionManager;

public class CommentDAOImpl implements CommentDAO {
	private static final CommentDAOImpl instance = new CommentDAOImpl();

	public static CommentDAOImpl getInstance() {
		return instance;
	}

	@Override
	public void save(Comment comment) {
		checkArgument(comment, notNull());

		try {
			Connection connection = DatabaseConnectionManager.getInstance()
					.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO Comments"
							+ "(score, txt, creationDate, userId, movieId)"
							+ " VALUES (?,?,?,?,?);");
			statement.setInt(1, comment.getScore());
			statement.setString(2, comment.getText());
			statement.setTimestamp(3, new Timestamp(comment.getCreationDate()
					.getMillis()));
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
			Connection connection = DatabaseConnectionManager.getInstance()
					.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT"
							+ " movieId, title, Movies.creationDate, releaseDate, genres, directorName,"
							+ " runtimeMins, summary,"
							+ " usersId, firstName, lastName, emailAddr, password, birthDate, vip,"
							+ " score, txt, creationDate"
							+ " FROM Comments, Movies, Users"
							+ " WHERE Comments.commentId=? AND Comments.movieId=movieId AND Comments.userId=userId");
			statement.setInt(1, id);

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				Director director = Director.builder().withName("directorName")
						.build();

				List<MovieGenres> genres = from(
						getEnum(results, "genres", MovieGenres.getConverter()))
						.copyInto(new LinkedList<MovieGenres>());

				Movie movie = Movie
						.builder()
						.withId(getInt(results, "movieId"))
						.withTitle(getString(results, "title"))
						.withCreationDate(
								getDateTime(results, "Movies.creationDate"))
						.withReleaseDate(getDateTime(results, "releaseDate"))
						.withGenres(genres).withDirector(director)
						.withRuntimeInMins(getInt(results, "runtimeMins"))
						.withSummary(getString(results, "summary")).build();

				User user = User.builder().withId(getInt(results, "usersId"))
						.withFirstName(getString(results, "firstName"))
						.withLastName(getString(results, "lastName"))
						.withPassword(getString(results, "password"))
						.withEmail(getEmailAddress(results, "email"))
						.withBirthDate(getDateTime(results, "birthDate"))
						.withVip(getBoolean(results, "vip")).build();

				comment = Comment.builder().withId(id).withMovie(movie)
						.withUser(user).withScore(getInt(results, "score"))
						.withText(getString(results, "txt")).build();
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return comment;
	}
}
