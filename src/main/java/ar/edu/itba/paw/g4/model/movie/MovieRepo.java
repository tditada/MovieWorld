package ar.edu.itba.paw.g4.model.movie;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.util.persist.Orderings;

public interface MovieRepo {
	public List<Movie> findAllByReleaseDate(Orderings ordering);

	public List<Movie> findAllByGenre(MovieGenres genre);

	public List<Movie> findAllByDirector(Director director);

	public List<Movie> findAllInOrderByReleaseDateInRange(Orderings ordering,
			DateTime fromDate, DateTime toDate);

	public List<Movie> findAllInOrderByTotalScore(Orderings ordering);

	public List<Director> findAllDirectorsOrderedByName(Orderings ordering);

	public Movie findById(int id);

	public void save(Movie movie);

	public List<Movie> findTopMovies(int quantity);

	public List<Movie> findNewAdditions(int quantity);

	public List<Movie> findReleases();
	
	public void remove(String id);
}
