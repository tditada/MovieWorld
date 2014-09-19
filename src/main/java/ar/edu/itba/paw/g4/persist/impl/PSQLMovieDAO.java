package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.asSQLOrdering;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getEnum;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getInt;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getString;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.insertQuery;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.updateQuery;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static com.google.common.collect.FluentIterable.from;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.util.persist.Orderings;
import ar.edu.itba.paw.g4.util.persist.sql.DatabaseConnection;
import ar.edu.itba.paw.g4.util.persist.sql.PSQLStatement;

import com.google.common.collect.Lists;

public class PSQLMovieDAO implements MovieDAO {
	private static final String TABLE_NAME_ID = "movies";
	private static final String TITLE_ID = "title";
	private static final String CREAT_DATE_ID = "creationDate";
	private static final String REL_DATE_ID = "releaseDate";
	private static final String GENRES_ID = "genres";
	private static final String DIR_NAME_ID = "directorName";
	private static final String RUNTIME_ID = "runtimeMins";
	private static final String SUMMARY_ID = "summary";
	private static final String ID_ATTR_ID = "movieId";
	private static final String TOTAL_SCORE_ID = "totalScore";
	private static final String TOTAL_COMMENTS_ID = "totalComments";

	private static final MovieDAO instance = new PSQLMovieDAO();

	public static MovieDAO getInstance() {
		return instance;
	}

	@Override
	public void save(final Movie movie) {
		checkArgument(movie, notNull());

		new DatabaseConnection<Void>() {

			@Override
			protected Void handleConnection(Connection connection)
					throws SQLException {
				String query;
				List<String> columns = Lists.newArrayList(TITLE_ID,
						CREAT_DATE_ID, REL_DATE_ID, GENRES_ID, DIR_NAME_ID,
						RUNTIME_ID, SUMMARY_ID, TOTAL_SCORE_ID,
						TOTAL_COMMENTS_ID);
				if (!movie.isPersisted()) {
					query = insertQuery(TABLE_NAME_ID, columns);
				} else {
					columns.add(ID_ATTR_ID);
					query = updateQuery(TABLE_NAME_ID, columns);
				}

				PSQLStatement statement = new PSQLStatement(connection, query,
						true);
				statement.addParameter(movie.getTitle());
				statement.addParameter(movie.getCreationDate());
				statement.addParameter(movie.getReleaseDate());
				statement.addParameter(movie.getGenres());
				statement.addParameter(movie.getDirector().getName());
				statement.addParameter(movie.getRuntimeInMins());
				statement.addParameter(movie.getSummary());
				statement.addParameter(movie.getAverageScore());
				statement.addParameter(movie.getTotalComments());

				if (movie.isPersisted()) {
					statement.addParameter(movie.getId());
				}

				int result = statement.executeUpdate();

				if (!movie.isPersisted()) {
					movie.setId(result);
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
	public Movie getById(final int id) {
		DatabaseConnection<Movie> connection = new DatabaseConnection<Movie>() {

			@Override
			protected Movie handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ ID_ATTR_ID + "=?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(id);

				ResultSet results = statement.executeQuery();
				if (results.next()) {
					return getMovieFromResults(results);
				}
				return null; // TODO: ver si no habria que tirar exception aca
								// (pelicula inexistente)
			}
		};
		return connection.run();
	}

	@Override
	public List<Movie> getAllByReleaseDate(final Orderings ordering) {
		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " ORDER BY "
						+ REL_DATE_ID + " " + asSQLOrdering(ordering);
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);

				ResultSet results = statement.executeQuery();
				return getMoviesFromResults(results);
			}
		};
		return connection.run();
	}

	@Override
	public List<Movie> getAllByGenre(final MovieGenres genre) {
		checkArgument(genre, notNull());
		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID
						+ " WHERE ? = ANY(" + GENRES_ID + ")";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(genre);

				ResultSet results = statement.executeQuery();
				return getMoviesFromResults(results);
			}
		};
		return connection.run();
	}

	@Override
	public List<Movie> getNewestNByCreationDate(final int quantity) {
		checkArgument(quantity > 0);
		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " ORDER BY "
						+ CREAT_DATE_ID + " " + asSQLOrdering(Orderings.DESC)
						+ " LIMIT ?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(quantity);

				ResultSet results = statement.executeQuery();
				return getMoviesFromResults(results);
			}
		};
		return connection.run();
	}

	@Override
	public List<Movie> getAllByDirector(final Director director) {
		checkArgument(director, notNull());
		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ DIR_NAME_ID + " = ?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(director.getName());

				ResultSet results = statement.executeQuery();
				return getMoviesFromResults(results);
			}
		};
		return connection.run();
	}

	@Override
	public List<Movie> getAllInOrderByReleaseDateInRange(final Orderings ordering, final DateTime fromDate,
			final DateTime toDate) {
		checkArgument(fromDate, notNull());
		checkArgument(toDate, notNull());
		checkArgument(toDate.isAfter(fromDate));

		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " WHERE "
						+ REL_DATE_ID + " >= ? AND " + REL_DATE_ID + " <= ?"
						+ " ORDER BY " + REL_DATE_ID + " "
						+ asSQLOrdering(ordering);
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(fromDate);
				statement.addParameter(toDate);

				ResultSet results = statement.executeQuery();
				return getMoviesFromResults(results);
			}
		};
		return connection.run();
	}

	public List<Movie> getAllInOrderByAverageScore(Orderings ordering,
			final int quantity) {
		checkArgument(ordering, notNull());
		checkArgument(quantity >= 0);

		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME_ID + " ORDER BY "
						+ TOTAL_SCORE_ID + " " + asSQLOrdering(Orderings.DESC)
						+ " LIMIT ?";
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);
				statement.addParameter(quantity);

				ResultSet results = statement.executeQuery();
				return getMoviesFromResults(results);
			}

		};
		return connection.run();
	}

	@Override
	public List<Director> getAllDirectorsOrderedByName(final Orderings ordering) {
		DatabaseConnection<List<Director>> connection = new DatabaseConnection<List<Director>>() {

			@Override
			protected List<Director> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT DISTINCT " + DIR_NAME_ID + " FROM "
						+ TABLE_NAME_ID + " ORDER BY " + DIR_NAME_ID + " "
						+ asSQLOrdering(ordering);
				PSQLStatement statement = new PSQLStatement(connection, query,
						false);

				ResultSet results = statement.executeQuery();
				return getDirectorsFromResults(results);
			}

		};
		return connection.run();
	}

	private List<Director> getDirectorsFromResults(ResultSet results)
			throws SQLException {
		List<Director> directors = new LinkedList<>();
		while (results.next()) {
			Director director = Director.builder()
					.withName(getString(results, DIR_NAME_ID)).build();
			directors.add(director);
		}
		return directors;
	}

	private List<Movie> getMoviesFromResults(ResultSet results)
			throws SQLException {
		List<Movie> movies = new LinkedList<>();
		while (results.next()) {
			movies.add(getMovieFromResults(results));
		}
		return movies;
	}

	private Movie getMovieFromResults(ResultSet results) throws SQLException {
		Director director = Director.builder()
				.withName(getString(results, DIR_NAME_ID)).build();

		List<MovieGenres> genres = from(
				getEnum(results, GENRES_ID, MovieGenres.getConverter()))
				.copyInto(new LinkedList<MovieGenres>());

		Movie movie = Movie.builder().withTitle(getString(results, TITLE_ID))
				.withCreationDate(getDateTime(results, CREAT_DATE_ID))
				.withReleaseDate(getDateTime(results, REL_DATE_ID))
				.withGenres(genres).withDirector(director)
				.withRuntimeInMins(getInt(results, RUNTIME_ID))
				.withSummary(getString(results, SUMMARY_ID))
				.withTotalScore(getInt(results, TOTAL_SCORE_ID))
				.withTotalComments(getInt(results, TOTAL_COMMENTS_ID)).build();
		movie.setId(getInt(results, ID_ATTR_ID));
		return movie;
	}

}
