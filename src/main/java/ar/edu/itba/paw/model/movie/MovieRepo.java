package ar.edu.itba.paw.model.movie;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.util.persist.Orderings;

public interface MovieRepo {
	List<Movie> findAllByReleaseDate(Orderings ordering);

	List<Movie> findAllByGenre(Genre genre);

	List<Movie> findAllByDirector(Director director);

	List<Movie> findAllInOrderByReleaseDateInRange(Orderings ordering,
			DateTime fromDate, DateTime toDate);

	List<Movie> findAllInOrderByTotalScore(Orderings ordering);

	List<Director> findAllDirectorsOrderedByName(Orderings ordering);

	Movie findById(int id);

	void save(Movie movie);

	List<Movie> findTopMovies(int quantity);

	List<Movie> findNewAdditions(int quantity);

	List<Movie> findReleases();

	void remove(User admin, Movie movie);

	Movie findByTitleAndDirector(String title, Director director);

}
