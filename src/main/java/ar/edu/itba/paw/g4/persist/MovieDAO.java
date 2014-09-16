package ar.edu.itba.paw.g4.persist;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;
import ar.edu.itba.paw.g4.util.persist.Orderings;

public interface MovieDAO extends GenericDAO<Movie> {

	public List<Movie> getAllByReleaseDate(Orderings ordering);

	public List<Movie> getAllByGenre(MovieGenres genre);

	public List<Movie> getNewestNByCreationDate(int quantity);

	public List<Movie> getAllByDirector(Director director);

	public List<Movie> getAllByReleaseDateInRange(DateTime fromDate,
			DateTime toDate);

}
