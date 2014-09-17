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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	private static final String TABLE_NAME = "movies";

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
				List<String> columns = Lists.newArrayList("title",
						"creationDate", "releaseDate", "genres",
						"directorName", "runtimeMins", "summary");
				if (!movie.isPersisted()) {
					query = insertQuery(TABLE_NAME, columns);
				} else {
					columns.add("movieId");
					query = updateQuery(TABLE_NAME, columns);
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
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE movieId=?";
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
				String query = "SELECT * FROM " + TABLE_NAME
						+ " ORDER BY releaseDate " + asSQLOrdering(ordering);
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
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE ? = ANY(genres)";
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
				String query = "SELECT * FROM " + TABLE_NAME
						+ " ORDER BY creationDate DESC LIMIT ?";
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
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE directorName = ?";
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
	public List<Movie> getAllByReleaseDateInRange(final DateTime fromDate,
			final DateTime toDate) {
		checkArgument(fromDate, notNull());
		checkArgument(toDate, notNull());
		checkArgument(toDate.isAfter(fromDate));

		DatabaseConnection<List<Movie>> connection = new DatabaseConnection<List<Movie>>() {

			@Override
			protected List<Movie> handleConnection(Connection connection)
					throws SQLException {
				String query = "SELECT * FROM " + TABLE_NAME
						+ " WHERE releaseDate >= ? AND releaseDate <= ?"
						+ " ORDER BY releaseDate DESC";
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
				.withName(getString(results, "directorName")).build();

		Set<MovieGenres> genres = from(
				getEnum(results, "genres", MovieGenres.getConverter()))
				.copyInto(new HashSet<MovieGenres>());

		Movie movie = Movie.builder().withTitle(getString(results, "title"))
				.withCreationDate(getDateTime(results, "creationDate"))
				.withReleaseDate(getDateTime(results, "releaseDate"))
				.withGenres(genres).withDirector(director)
				.withRuntimeInMins(getInt(results, "runtimeMins"))
				.withSummary(getString(results, "summary")).build();
		movie.setId(getInt(results, "movieId"));
		return movie;
	}

}
