package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.persist.hibernate.HQLQueryHelpers.asHQLOrdering;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.g4.model.AbstractHibernateRepo;
import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.comment.CommentRepo;
import ar.edu.itba.paw.g4.model.genre.Genre;
import ar.edu.itba.paw.g4.model.genre.GenreRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.Orderings;

@Repository
public class HibernateMovieRepo extends AbstractHibernateRepo implements
		MovieRepo {
	private CommentRepo comments;
	private GenreRepo genres;

	@Autowired
	public HibernateMovieRepo(SessionFactory sessionFactory,
			CommentRepo comments, GenreRepo genres) {
		super(sessionFactory);
		this.comments = comments;
		this.genres = genres;
	}

	@Override
	public Movie findById(int id) {
		return get(Movie.class, id);
	}

	@Override
	public void save(Movie movie) {
		checkArgument(movie, notNull());
		for (Genre genre : movie.getGenres()) {
			// this is because JPA's mapping {CascadeType.PERSIST,
			// CascadeType.MERGE} doesn't work well with Hibernate's Session
			genres.save(genre);
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
	public List<Movie> findAllByGenre(Genre genre) {
		checkArgument(genre, notNull());
		return find("from Movie movie where ? member of movie.genres", genre);
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
		return find("select distinct movie.director from Movie movie order by Director "
				+ asHQLOrdering(ordering));
	}

	@Override
	public List<Movie> findTopMovies(int quantity) {
		checkArgument(quantity > 0);
		return find("from Movie order by totalScore desc limit " + quantity);
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
	public void remove(User admin, Movie movie) {
		checkArgument(movie, notNull());
		checkArgument(admin, notNull());
		checkArgument(admin.isAdmin());

		List<Comment> commentsToRemove = new LinkedList<>();
		commentsToRemove.addAll(movie.getComments()); // to avoid
														// ConcurrentModificationExceptions

		for (Comment comment : commentsToRemove) {
			comments.remove(admin, comment); // to be consistent in memory
		}

		// genres should NOT be removed, since they are strong
		// entities

		super.remove(movie);
	}

	@Override
	public Movie findByTitleAndDirector(String title, Director director) {
		List<Movie> movies = find("from Movie where title=? and director=?",
				title, director);
		if (movies.isEmpty()) {
			return null;
		}
		return movies.get(0);
	}

}
