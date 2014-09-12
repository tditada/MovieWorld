package ar.edu.itba.paw.g4.persist.impl;

import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getBoolean;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getEmailAddress;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getEnum;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getInt;
import static ar.edu.itba.paw.g4.util.persist.SQLQueryHelpers.getString;
import static com.google.common.collect.FluentIterable.from;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.MovieDAO;

public class SQLMovieDAO implements MovieDAO {
	private static final MovieDAO instance = new SQLMovieDAO();

	public static MovieDAO getInstance() {
		return instance;
	}

	@Override
	public void save(Movie movie) {
		// TODO Auto-generated method stub

	}

	@Override
	public Movie getById(int id) {
		// ResultSet results = statement.executeQuery();
		// while (results.next()) {
		// Director director = Director.builder().withName("directorName")
		// .build();
		//
		// List<MovieGenres> genres = from(
		// getEnum(results, "genres", MovieGenres.getConverter()))
		// .copyInto(new LinkedList<MovieGenres>());
		//
		// Movie movie = movieDAO.getById(getInt(results,"movieId"));
		// .builder()
		// .withId(getInt(results, "movieId"))
		// .withTitle(getString(results, "title"))
		// .withCreationDate(
		// getDateTime(results, "Movies.creationDate"))
		// .withReleaseDate(getDateTime(results, "releaseDate"))
		// .withGenres(genres).withDirector(director)
		// .withRuntimeInMins(getInt(results, "runtimeMins"))
		// .withSummary(getString(results, "summary")).build();
		//
		// User user = User.builder().withId(getInt(results, "usersId"))
		// .withFirstName(getString(results, "firstName"))
		// .withLastName(getString(results, "lastName"))
		// .withPassword(getString(results, "password"))
		// .withEmail(getEmailAddress(results, "email"))
		// .withBirthDate(getDateTime(results, "birthDate"))
		// .withVip(getBoolean(results, "vip")).build();
		//
		// comment = Comment.builder().withId(id).withMovie(movie)
		// .withUser(user).withScore(getInt(results, "score"))
		// .withText(getString(results, "txt")).build();

		return null;
	}

}
