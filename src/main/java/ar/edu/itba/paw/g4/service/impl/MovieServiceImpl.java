package ar.edu.itba.paw.g4.service.impl;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.util.persist.Orderings;

public class MovieServiceImpl implements MovieService {
	private static final MovieService instance = new MovieServiceImpl();

	private MovieDAO movieDAO = PSQLMovieDAO.getInstance();

	public static MovieService getInstance() {
		return instance;
	}

	private MovieServiceImpl() {
	}

	@Override
	public List<Movie> getMovieList() {
		return movieDAO.getAllByReleaseDate(Orderings.DESC);
	}

	@Override
	public List<Movie> getAllMoviesByGenre(MovieGenres genre) {
		checkArgument(genre, notNull());
		return movieDAO.getAllByGenre(genre);

	}

	@Override
	public List<Movie> getNewAdditions(int quantity) {
		checkArgument(quantity > 0);
		return movieDAO.getNewestNByCreationDate(quantity);
	}

	public List<Movie> getTopMovies(int quantity) {
		checkArgument(quantity > 0);
		return movieDAO.getAllInOrderByAverageScore(Orderings.DESC, quantity);
	}

	@Override
	public List<Movie> getAllMoviesByDirector(Director director) {
		checkArgument(director, notNull());
		// TODO: tiene sentido aca chequear si existe o no el director?
		return movieDAO.getAllByDirector(director);
	}

	@Override
	public List<Movie> getReleases() {
		return movieDAO.getAllInOrderByReleaseDateInRange(Orderings.DESC, now()
				.minusDays(Movie.DAYS_AS_RELEASE), now());
	}

	@Override
	public Movie getMovieById(int id) {
		Movie movie = movieDAO.getById(id);
		if (movie == null) {
			throw new IllegalArgumentException("There is no movie with id ("
					+ id + ")");
		}
		return movie;
	}

	@Override
	public List<Director> getAllDirectors() {
		return movieDAO.getAllDirectorsOrderedByName(Orderings.ASC);
	}

}
