package ar.edu.itba.paw.g4;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLCommentDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLUserDAO;
import ar.edu.itba.paw.g4.util.EmailAddress;
import ar.edu.itba.paw.g4.util.persist.Orderings;

import com.google.common.collect.Lists;

public class TestMain {
	public static void main(String[] args) throws Exception {
		testDAOs();
		System.out.println("Everything OK");
	}

	private static void testDAOs() {
		// IMPORTANT: These tests will only work with an empty database!
		UserDAO userDAO = PSQLUserDAO.getInstance();

		User user = User
				.builder()
				.withBirthDate(DateTime.now())
				.withEmail(
						EmailAddress.build("pepes" + System.currentTimeMillis()
								+ "@foo.com")).withFirstName("Pepe")
				.withLastName("Sanchez").withPassword("12345678910").build();

		userDAO.save(user);

		User user2 = userDAO.getById(user.getId());
		if (!user.equals(user2)) {
			System.out.println(user);
			System.out.println(user2);
			throw new RuntimeException("Problemas con el userDAO");
		}

		MovieDAO movieDAO = PSQLMovieDAO.getInstance();

		List<MovieGenres> genres = Lists.newArrayList(MovieGenres.ACTION);
		Director director = Director.builder().withName("El Barto").build();
		Movie movie = Movie
				.builder()
				.withTitle("Bartman, el origen")
				.withSummary(
						"La epica historia del barto, plagada de accion y groserias")
				.withDirector(director)
				.withCreationDate(DateTime.now().minusYears(1))
				.withGenres(genres)
				.withReleaseDate(DateTime.now().minusMonths(1))
				.withRuntimeInMins(115).build();

		movieDAO.save(movie);

		Movie movie2 = movieDAO.getById(movie.getId());
		if (!movie.equals(movie2)) {
			System.out.println(movie);
			System.out.println(movie2);
			throw new RuntimeException("Problemas con el movieDAO");
		}

		CommentDAO commentDAO = PSQLCommentDAO.getInstance();

		Comment comment = Comment.builder().withScore(5)
				.withText("Me fascino. Cambio mi vida")
				.withCreationDate(DateTime.now()).withMovie(movie)
				.withUser(user).build();

		commentDAO.save(comment);

		Comment comment2 = commentDAO.getById(comment.getId());
		if (!comment.equals(comment2)) {
			System.out.println(comment);
			System.out.println(comment2);
			throw new RuntimeException("Problemas con el commentDAO");
		}

		List<Comment> commentsByMovie = commentDAO.getAllByMovie(movie);
		if (!commentsByMovie.contains(comment)) {
			System.out.println(commentsByMovie);
			System.out.println(comment);
			throw new RuntimeException("Problemas con el commentDAO");
		}

		List<Comment> commentsByUser = commentDAO.getAllByUser(user);
		if (!commentsByUser.contains(comment)) {
			System.out.println(commentsByUser);
			System.out.println(comment);
			throw new RuntimeException("Problemas con el commentDAO");
		}

		List<Movie> allMovies = movieDAO.getAllByReleaseDate(Orderings.DESC);
		if (!allMovies.contains(movie)) {
			System.out.println(allMovies);
			System.out.println(movie);
			throw new RuntimeException("Problemas con el movieDAO");
		}

		List<Movie> allMoviesByGenre = movieDAO.getAllByGenre(movie.getGenres()
				.iterator().next());
		if (!allMoviesByGenre.contains(movie)) {
			System.out.println(allMoviesByGenre);
			System.out.println(movie);
			throw new RuntimeException("Problemas con el movieDAO");
		}

		int n = 5;
		List<Movie> newestNMovies = movieDAO.getNewestNByCreationDate(n);
		if (!newestNMovies.contains(movie)
				|| (allMovies.size() >= n && newestNMovies.size() < n)) {
			System.out.println(!newestNMovies.contains(movie));
			System.out.println(allMovies.size() >= n
					&& newestNMovies.size() < n);
			System.out.println(newestNMovies);
			System.out.println(movie);
			throw new RuntimeException("Problemas con el movieDAO");
		}

		List<Movie> allMoviesByDirector = movieDAO.getAllByDirector(movie
				.getDirector());
		if (!allMoviesByDirector.contains(movie)) {
			System.out.println(allMoviesByDirector);
			System.out.println(movie);
			throw new RuntimeException("Problemas con el movieDAO");
		}

		DateTime fromDate = DateTime.now().plusYears(1).minusDays(6);
		DateTime toDate = DateTime.now().plusYears(1);

		Movie movieNotReleasedInRange1 = Movie.builder()
				.withTitle("Future movie not in range 1")
				.withSummary("Some movie summary").withDirector(director)
				.withCreationDate(DateTime.now().plusYears(1))
				.withGenres(genres).withReleaseDate(fromDate.minusMinutes(15))
				.withRuntimeInMins(115).build();
		movieDAO.save(movieNotReleasedInRange1);
		Movie movieReleasedInRange1 = Movie.builder()
				.withTitle("Future movie X").withSummary("Some movie summary")
				.withDirector(director)
				.withCreationDate(DateTime.now().plusYears(1))
				.withGenres(genres).withReleaseDate(fromDate)
				.withRuntimeInMins(115).build();
		movieDAO.save(movieReleasedInRange1);
		Movie movieReleasedInRange2 = Movie.builder()
				.withTitle("Future movie Y").withSummary("Some movie summary")
				.withDirector(director)
				.withCreationDate(DateTime.now().plusYears(1))
				.withGenres(genres).withReleaseDate(toDate)
				.withRuntimeInMins(115).build();
		movieDAO.save(movieReleasedInRange2);
		Movie movieNotReleasedInRange2 = Movie.builder()
				.withTitle("Future movie not in range 2")
				.withSummary("Some movie summary").withDirector(director)
				.withCreationDate(DateTime.now().plusYears(1))
				.withGenres(genres).withReleaseDate(toDate.plusMinutes(15))
				.withRuntimeInMins(115).build();
		movieDAO.save(movieNotReleasedInRange2);

		List<Movie> allReleasedInRange = movieDAO
				.getAllInOrderByReleaseDateInRange(Orderings.DESC, fromDate,
						toDate);
		if (!allReleasedInRange.contains(movieReleasedInRange1)
				|| !allReleasedInRange.contains(movieReleasedInRange2)
				|| allReleasedInRange.contains(movieNotReleasedInRange1)
				|| allReleasedInRange.contains(movieNotReleasedInRange2)) {
			System.out.println(allReleasedInRange);
			System.out.println(movieReleasedInRange1);
			System.out.println(movieReleasedInRange2);
			System.out.println(movieNotReleasedInRange1);
			System.out.println(movieNotReleasedInRange2);
			throw new RuntimeException("Problemas con el movieDAO");
		}
	}
}