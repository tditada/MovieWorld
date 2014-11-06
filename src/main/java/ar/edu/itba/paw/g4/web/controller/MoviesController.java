package ar.edu.itba.paw.g4.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.movie.Director;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieGenre;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;
import ar.edu.itba.paw.g4.util.persist.Orderings;
import ar.edu.itba.paw.g4.web.form.MovieForm;
import ar.edu.itba.paw.g4.web.form.NewCommentForm;
import ar.edu.itba.paw.g4.web.form.ScoreCommentForm;
import ar.edu.itba.paw.g4.web.form.validation.MovieFormValidator;

@Controller
@RequestMapping("/movies")
public class MoviesController {
	private static final String USER_ID = "user";
	private static final String MOVIE_ID = "movie";
	private static final String CAN_COMMENT_ID = "ableToComment";
	private static final String SCOREABLES_BY_USER = "scoreablesByUser";
	private static final String REPORTABLES_BY_USER = "reportablesByUser";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";

	private static final String SESSION_MOVIE_ID = "movie";
	private static final String SESSION_USER_ID = "user";

	private MovieRepo movies;
	private UserRepo users;
	private MovieFormValidator movieFormValidator;

	@Autowired
	MoviesController(MovieRepo movies, UserRepo users,
			MovieFormValidator movieFormValidator) {
		this.users = users;
		this.movies = movies;
		this.movieFormValidator = movieFormValidator;
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public ModelAndView showInsert(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);
		if (user == null || !user.isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		mav.addObject("movieForm", new MovieForm());
		mav.addObject(USER_ID, user);
		mav.setViewName("/movies/insert");
		return mav;
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public ModelAndView insert(MovieForm movieForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);
		if (user == null || !user.isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		movieFormValidator.validate(movieForm, errors);
		if (errors.hasErrors()) {
			mav.addObject(USER_ID, user);
			mav.setViewName("/movies/insert");
			return mav;
		}

		Movie movie = movieForm.build();
		movies.save(movie);
		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);
		if (movie == null || user == null || !user.isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		saveMovieInSession(session, movie);
		mav.addObject(USER_ID, user);
		mav.addObject(MOVIE_ID, movie);
		mav.addObject("movieForm", new MovieForm());
		mav.setViewName("movies/edit");
		return mav;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ModelAndView edit(MovieForm movieForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);
		Movie movie = getMovieFromSession(session);
		if (user == null || !user.isAdmin() || movie == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		// TODO: editMovieFormValidator
		movieFormValidator.validate(movieForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("edit");
			return mav;
		}

		movie.setTitle(movieForm.getTitle());
		movie.setReleaseDate(movieForm.getReleaseDate());
		movie.setGenres(movieForm.getGenres());
		movie.setDirector(movieForm.getDirector());
		movie.setSummary(movieForm.getSummary());
		movie.setRuntimeInMins(movieForm.getRuntimeInMins());

		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(@RequestParam(required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = getLoggedUserFromSession(session);

		if (movie != null && user != null && user.isAdmin()) {
			movies.remove(movie);
		}

		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "genre", required = false) MovieGenre genre,
			@RequestParam(value = "director", required = false) Director director,
			HttpSession session) {
		List<Movie> movieList;
		if (genre == null && director == null) {
			movieList = movies.findAllByReleaseDate(Orderings.DESC);
		} else if (genre != null) {
			movieList = movies.findAllByGenre(genre);
		} else {
			movieList = movies.findAllByDirector(director);
		}
		List<Director> directors = movies
				.findAllDirectorsOrderedByName(Orderings.ASC);
		List<MovieGenre> genres = movies
				.findAllGenresOrderedByName(Orderings.ASC);

		ModelAndView mav = new ModelAndView();
		mav.addObject(GENRES_ID, genres);
		mav.addObject(DIRECTORS_ID, directors);
		mav.addObject(MOVIES_ID, movieList);
		mav.addObject(USER_ID, getLoggedUserFromSession(session));
		mav.setViewName("movies/all");
		return mav;
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam(required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		if (movie == null) {
			mav.setViewName("redirect:/app/movies/list");
			return mav;
		}

		boolean canComment = false;

		User user = getLoggedUserFromSession(session);
		if (user != null) {
			canComment = movie.isCommentableBy(user);
			mav.addObject(SCOREABLES_BY_USER,
					movie.getCommentsScoreableBy(user));
			mav.addObject(REPORTABLES_BY_USER,
					movie.getCommentsReportableBy(user));
		}
		saveMovieInSession(session, movie);

		mav.addObject("scoreCommentForm", new ScoreCommentForm());
		mav.addObject("newCommentForm", new NewCommentForm());
		mav.addObject(MOVIE_ID, movie);
		mav.addObject(CAN_COMMENT_ID, canComment);
		mav.addObject(USER_ID, getLoggedUserFromSession(session));
		mav.setViewName("movies/single");
		return mav;
	}

	private User getLoggedUserFromSession(HttpSession session) {
		Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
		if (userId == null) {
			return null;
		}
		return users.findById(userId);
	}

	private Movie getMovieFromSession(HttpSession session) {
		Integer movieId = (Integer) session.getAttribute(SESSION_MOVIE_ID);
		if (movieId == null) {
			return null;
		}
		return movies.findById(movieId);
	}

	private void saveMovieInSession(HttpSession session, Movie movie) {
		session.setAttribute(SESSION_MOVIE_ID, movie.getId());
	}
}
