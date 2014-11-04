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
import ar.edu.itba.paw.g4.model.user.UserRepo;
import ar.edu.itba.paw.g4.util.persist.Orderings;
import ar.edu.itba.paw.g4.web.form.MovieForm;
import ar.edu.itba.paw.g4.web.form.NewCommentForm;
import ar.edu.itba.paw.g4.web.form.ScoreCommentForm;
import ar.edu.itba.paw.g4.web.form.validation.MovieFormValidator;
import ar.edu.itba.paw.g4.web.formatters.MovieGenresSetFormatter;

@Controller
@RequestMapping("/movies")
public class MoviesController {
	private static final String CURR_USER_ID = "user";

	public static final String PARAM_ID = "id";

	public static final String MOVIE_ID = "movie";
	public static final String MOVIE_PARAM_ID = "movie_id";
	public static final String COMMENT_LIST_ID = "comments";
	public static final String CAN_COMMENT_ID = "ableToComment";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";

	private static final String USER_PARAM_ID = "user_id";
	private static final String USER_ID = "user";

	private MovieRepo movies;
	private UserRepo users;
	private MovieFormValidator movieFormValidator;

	@Autowired
	MoviesController(MovieRepo movies, UserRepo users,
			MovieFormValidator movieFormValidator) {
		this.movies = movies;
		this.users = users;
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
			@RequestParam(value = PARAM_ID, required = true) int id,
			HttpSession session) {
		String DATE_TIME_FORMAT = "yyyy-MM-dd";
		DateTimeFormatter dateTimeFormatter = DateTimeFormat
				.forPattern(DATE_TIME_FORMAT);
		ModelAndView mav = new ModelAndView();
		User actualUser = users.findById((int) session
				.getAttribute(USER_PARAM_ID));
		if (isNotLoggedOrisNotAdmin(session, mav)) {
			return mav;
		}

		Movie movie = movies.findById(id);
		MovieGenresSetFormatter formatter = new MovieGenresSetFormatter();
		String s = formatter.print(movie.getGenres(), null);

		session.setAttribute(MOVIE_ID, movie.getId());
		mav.addObject(USER_ID, actualUser);
		mav.addObject(MOVIE_ID, movie);
		mav.addObject("MovieForm", new MovieForm());
		mav.addObject("genres", s);
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
			@RequestParam(value = PARAM_ID, required = false) String id,
			HttpSession session) {
		User actualUser = users.findById((int) session
				.getAttribute(USER_PARAM_ID));
		ModelAndView mav = new ModelAndView();
		if (actualUser == null || !actualUser.isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		movies.remove(id);
		mav.setViewName("redirect:/app/home");
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

		ModelAndView mav = new ModelAndView();
		mav.addObject(GENRES_ID, MovieGenres.values());
		mav.addObject(DIRECTORS_ID,
				movies.findAllDirectorsOrderedByName(Orderings.ASC));
		mav.addObject(MOVIES_ID, movieList);
		setUserInMav(session, mav);
		mav.setViewName("movies/all");
		return mav;
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(
			@RequestParam(value = "id", required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		boolean canComment = false;
		if (movie == null) {
			mav.setViewName("redirect:/app/movies/list");// TODO: check!
			return mav;
		}
		if (session.getAttribute(USER_PARAM_ID) != null) {
			int id = (int) (session.getAttribute(USER_PARAM_ID));
			User user = users.findById(id);
			if (user != null) {
				canComment = movie.isCommentableBy(user);
			}
			session.setAttribute(MOVIE_ID, movie);
		}
		mav.addObject("commentScoreForm", new ScoreCommentForm());
		mav.addObject("commentForm", new NewCommentForm());
		mav.addObject(MOVIE_ID, movie);
		mav.addObject(CAN_COMMENT_ID, canComment);
		setMovieInSession(movie, session);
		setUserInMav(session, mav);
		mav.setViewName("movies/single");
		return mav;
	}

	private void setMovieInSession(Movie movie, HttpSession session) {
		session.setAttribute(MOVIE_PARAM_ID, movie.getId());
	}

	private void setUserInMav(HttpSession session, ModelAndView mav) {
		if (session.getAttribute(USER_PARAM_ID) != null) {
			User user = users.findById((int) session
					.getAttribute(USER_PARAM_ID));
			mav.addObject(USER_ID, user);
		}
	}

	private boolean isNotLoggedOrisNotAdmin(HttpSession session,
			ModelAndView mav) {
		Object o = session.getAttribute(USER_PARAM_ID);
		if (o == null || !((User) o).isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return true;
		}
		return false;
	}

	private User getCurrentUserFromSession(HttpSession session) {
		return (User) session.getAttribute(CURR_USER_ID);
	}
}
