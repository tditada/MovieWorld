package ar.edu.itba.paw.g4.persist;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;
import ar.edu.itba.paw.g4.util.persist.Orderings;

public interface MovieDAO extends GenericDAO<Movie> {

	public List<Movie> getAllByReleaseDate(Orderings ordering);

	public List<Movie> getAllByGenre(MovieGenres genre);

	public List<Movie> getNewestNByCreationDate(int quantity);

	public List<Movie> getAllByDirector(Director director);

	public List<Movie> getAllInOrderByReleaseDateInRange(Orderings ordering, DateTime fromDate,
			DateTime toDate);

	public List<Movie> getAllInOrderByTotalScore(Orderings ordering);

	public List<Director> getAllDirectorsOrderedByName(Orderings ordering);

}
