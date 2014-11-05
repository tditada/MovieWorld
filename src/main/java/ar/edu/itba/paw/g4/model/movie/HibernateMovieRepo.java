package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.persist.hibernate.HQLQueryHelpers.asHQLOrdering;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.g4.model.AbstractHibernateRepo;
import ar.edu.itba.paw.g4.util.persist.Orderings;

@Repository
public class HibernateMovieRepo extends AbstractHibernateRepo implements
		MovieRepo {

	@Autowired
	public HibernateMovieRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Movie findById(int id) {
		return get(Movie.class, id);
	}

	@Override
	public void save(Movie movie) {
		// TODO: Revisar que la pelicula no exista ya en la BD

		for (MovieGenre genre : movie.getGenres()) {
			super.save(genre);
		}

		super.save(movie);
	}

	@Override
	public List<Movie> findAllByReleaseDate(Orderings ordering) {
		checkArgument(ordering, notNull());
		return find("from Movie order by releaseDate "
				+ asHQLOrdering(ordering));
	}

	@Override
	public List<Movie> findAllByGenre(MovieGenre genre) {
		checkArgument(genre, notNull());
		return find("from Movie m join m.genres g with (g =?)", genre);
	}

	@Override
	public List<Movie> findNewAdditions(int quantity) {
		checkArgument(quantity > 0);
		return find("from Movie order by creationDate desc limit " + quantity);
	}

	@Override
	public List<Movie> findAllByDirector(Director director) {
		checkArgument(director, notNull());
		return find("from Movie where director=?", director);
	}

	@Override
	public List<Movie> findAllInOrderByReleaseDateInRange(Orderings ordering,
			DateTime fromDate, DateTime toDate) {
		checkArgument(ordering, notNull());
		checkArgument(fromDate, notNull());
		checkArgument(toDate, notNull());
		checkArgument(toDate.isAfter(fromDate));
		return find(
				"from Movie where releaseDate>=? and releaseDate<=? order by releaseDate "
						+ asHQLOrdering(ordering), fromDate, toDate);
	}

	@Override
	public List<Movie> findAllInOrderByTotalScore(Orderings ordering) {
		checkArgument(ordering, notNull());
		return find("from Movie order by totalScore " + asHQLOrdering(ordering));
	}

	@Override
	public List<Director> findAllDirectorsOrderedByName(Orderings ordering) {
		checkArgument(ordering, notNull());
		return find("select movie.director from Movie movie order by Director "
				+ asHQLOrdering(ordering));
	}

	@Override
	public List<Movie> findTopMovies(int quantity) {
		checkArgument(quantity > 0);
		return find("from Movie order by totalScore desc limit " + quantity);
		// TODO: check!
		// List<Movie> movies =
		// movieDAO.getAllInOrderByTotalScore(Orderings.DESC);
		// List<Movie> topMovies = from(movies).toSortedList(
		// new Comparator<Movie>() {
		// @Override
		// public int compare(Movie movie1, Movie movie2) {
		// return movie2.getAverageScore()
		// - movie1.getAverageScore();
		// }
		// }).subList(0, quantity);
		// return topMovies;
	}

	@Override
	public List<Movie> findReleases() {
		DateTime toDate = now();
		DateTime fromDate = toDate.minusDays(Movie.DAYS_AS_RELEASE);

		return find(
				"from Movie where releaseDate>=? and releaseDate<=? order by releaseDate "
						+ asHQLOrdering(Orderings.DESC), fromDate, toDate);
	}

	@Override
	public void remove(Movie movie) {
		super.remove(movie);
	}

	@Override
	public List<MovieGenre> findAllGenresOrderedByName(Orderings ordering) {
		checkArgument(ordering, notNull());
		return find("from MovieGenre genre order by genre.name "
				+ asHQLOrdering(ordering));
	}

	// @Override
	// public MovieGenre findGenreByName(String name) { // TODO:check!
	// checkArgument(name, notNull());
	// List<MovieGenre> genres = find(
	// "from MovieGenre genre where genre.name=?", name);
	// if (genres.isEmpty()) {
	// return null;
	// }
	// return genres.get(0);
	// }

}
