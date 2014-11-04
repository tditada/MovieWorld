package ar.edu.itba.paw.g4.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.Orderings;
import ar.edu.itba.paw.g4.web.form.MovieForm;
import ar.edu.itba.paw.g4.web.form.NewCommentForm;
import ar.edu.itba.paw.g4.web.form.ScoreCommentForm;
import ar.edu.itba.paw.g4.web.form.validation.MovieFormValidator;
import ar.edu.itba.paw.g4.web.formatters.MovieGenresSetFormatter;

@Controller
@RequestMapping("/movies")
public class MoviesController {
	private static final String USER_ID = "user";
	private static final String MOVIE_ID = "movie";
	private static final String CAN_COMMENT_ID = "ableToComment";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";

	private MovieRepo movies;
	private MovieFormValidator movieFormValidator;

	@Autowired
	MoviesController(MovieRepo movies, MovieFormValidator movieFormValidator) {
		this.movies = movies;
		this.movieFormValidator = movieFormValidator;
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public ModelAndView showInsert(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getCurrentUserFromSession(session);
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

		User user = getCurrentUserFromSession(session);
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
	public ModelAndView edit(
			@RequestParam(value = "id", required = true) Movie movie,
			HttpSession session) {
		String DATE_TIME_FORMAT = "yyyy-MM-dd"; // XXX
		DateTimeFormatter dateTimeFormatter = DateTimeFormat
				.forPattern(DATE_TIME_FORMAT);
		ModelAndView mav = new ModelAndView();

		User user = getCurrentUserFromSession(session);
		if (user == null || !user.isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		MovieGenresSetFormatter formatter = new MovieGenresSetFormatter();
		String genres = formatter.print(movie.getGenres(), null);// XXX

		session.setAttribute(MOVIE_ID, movie.getId());
		mav.addObject(USER_ID, user);
		mav.addObject(MOVIE_ID, movie);
		mav.addObject("movieForm", new MovieForm());
		mav.addObject("genres", genres);
		mav.addObject("releaseDate",
				movie.getReleaseDate().toString(dateTimeFormatter));
		mav.setViewName("movies/edit");
		return mav;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ModelAndView edit(MovieForm movieForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// TODO: editMovieFormValidator
		movieFormValidator.validate(movieForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("edit");
			return mav;
		}

		Movie movie = (Movie) session.getAttribute(MOVIE_ID);
		if (movie == null) {
			mav.setViewName("redirect:/app/home");
		}

		String title = movieForm.getTitle();
		if (title != null) {
			movie.setTitle(title);
		}

		DateTime releaseDate = movieForm.getReleaseDate();
		if (releaseDate != null) {
			movie.setReleaseDate(releaseDate);
		}

		Set<MovieGenres> genres = movieForm.getGenres();
		if (genres != null) {
			movie.setGenres(genres);
		}

		Director director = movieForm.getDirector();
		if (director != null) {
			movie.setDirector(director);
		}

		String summary = movieForm.getSummary();
		if (summary != null) {
			movie.setSummary(summary);
		}

		// usar el validator para este
		int runtimeInMins = movieForm.getRuntimeInMins();
		if (runtimeInMins > 0) {
			movie.setRuntimeInMins(runtimeInMins);
		}

		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(
			@RequestParam(value = "id", required = true) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/app/home");

		User user = getCurrentUserFromSession(session);

		if (user == null || !user.isAdmin()) {
			return mav;
		}

		movies.remove(movie);
		return mav;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "genre", required = false) MovieGenres genre,
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

		ModelAndView mav = new ModelAndView();
		mav.addObject(GENRES_ID, MovieGenres.values());
		mav.addObject(DIRECTORS_ID, directors);
		mav.addObject(MOVIES_ID, movieList);
		mav.addObject(USER_ID, getCurrentUserFromSession(session));
		mav.setViewName("movies/all");
		return mav;
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(
			@RequestParam(value = "id", required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		if (movie == null) {
			mav.setViewName("redirect:/app/movies/list");// TODO: check!
			return mav;
		}

		boolean canComment = false;

		User user = getCurrentUserFromSession(session);
		if (user != null) {
			canComment = movie.isCommentableBy(user);
		}
		session.setAttribute(MOVIE_ID, movie);

		mav.addObject("scoreCommentForm", new ScoreCommentForm());
		mav.addObject("newCommentForm", new NewCommentForm());
		mav.addObject(MOVIE_ID, movie);
		mav.addObject(CAN_COMMENT_ID, canComment);
		mav.addObject(USER_ID, getCurrentUserFromSession(session));
		mav.setViewName("movies/single");
		return mav;
	}

	private User getCurrentUserFromSession(HttpSession session) {
		return (User) session.getAttribute(USER_ID);
	}
}
