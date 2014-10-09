package ar.edu.itba.paw.g4.service.impl;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static com.google.common.collect.FluentIterable.from;
import static org.joda.time.DateTime.now;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.util.persist.Orderings;

@Service
public class MovieServiceImpl implements MovieService {
	private MovieDAO movieDAO;

	@Autowired
	MovieServiceImpl(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
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

	@Override
	public List<Movie> getTopMovies(int quantity) {
		checkArgument(quantity > 0);
		List<Movie> movies = movieDAO.getAllInOrderByTotalScore(Orderings.DESC);
		List<Movie> topMovies = from(movies).toSortedList(
				new Comparator<Movie>() {
					@Override
					public int compare(Movie movie1, Movie movie2) {
						return movie2.getAverageScore()
								- movie1.getAverageScore();
					}
				}).subList(0, quantity);
		return topMovies;
	}

	@Override
	public List<Movie> getAllMoviesByDirector(Director director) {
		checkArgument(director, notNull());
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
