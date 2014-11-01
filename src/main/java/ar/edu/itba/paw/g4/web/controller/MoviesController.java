package ar.edu.itba.paw.g4.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import ar.edu.itba.paw.g4.web.convert.MovieGenresSetFormatter;
import ar.edu.itba.paw.g4.web.form.CommentForm;
import ar.edu.itba.paw.g4.web.form.CommentScoreForm;
import ar.edu.itba.paw.g4.web.form.DeleteForm;
import ar.edu.itba.paw.g4.web.form.MovieForm;
import ar.edu.itba.paw.g4.web.form.validation.MovieFormValidator;

@Controller
@RequestMapping("/movies")
public class MoviesController {
	public static final String PARAM_ID = "id";

	public static final String MOVIE_ID = "movie";
	public static final String MOVIE_PARAM_ID ="movie_id";
	public static final String COMMENT_LIST_ID = "comments";
	public static final String CAN_COMMENT_ID = "ableToComment";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";

	private static final String USER_PARAM_ID = "user_id";
	private static final String USER_ID = "user";

	private MovieRepo movies;
	private UserRepo users;

	@Autowired
	MoviesController(MovieRepo movies, UserRepo users) {
		this.movies = movies;
		this.users = users;
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public ModelAndView insert(HttpSession session) {
		User admin = users.getAdmin();
		User actualUser = users.findById((int) session
				.getAttribute(USER_PARAM_ID));
		ModelAndView mav = new ModelAndView();
		if (actualUser == null || actualUser.getId() != admin.getId()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		mav.addObject("movieForm", new MovieForm());
		mav.addObject("user", actualUser);
		mav.setViewName("/movies/insert");
		return mav;
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public ModelAndView insert(MovieForm form, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MovieFormValidator validator = new MovieFormValidator();
		validator.validate(form, errors);
		if (errors.hasErrors()) {
			setUserInMav(session, mav);
			mav.setViewName("/movies/insert");
			return mav;
		}

		Movie movie = form.build();
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
		User admin = users.getAdmin();
		User actualUser = users.findById((int) session
				.getAttribute(USER_PARAM_ID));
		if (actualUser == null || actualUser.getId() != admin.getId()) {
			mav.setViewName("redirect:/app/home");
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
		MovieFormValidator validator = new MovieFormValidator();
		validator.validate(movieForm, errors);
		int id = (int) session.getAttribute(MOVIE_ID);
		Movie movie = movies.findById(id);
		movie.updateMovie(movieForm.getfilmTitle(),
				movieForm.getfilmReleaseDate(), movieForm.getFilmGenres(),
				movieForm.getfilmDirector(), movieForm.getfilmSummary(),
				movieForm.getfilmRuntimeInMins());
		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(
			@RequestParam(value = PARAM_ID, required = false) String id,
			HttpSession session) {
		User admin = users.getAdmin();
		User actualUser = users.findById((int) session
				.getAttribute(USER_PARAM_ID));
		ModelAndView mav = new ModelAndView();
		if (actualUser == null || actualUser.getId() != admin.getId()) {
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
			@RequestParam(value = PARAM_ID, required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		CommentForm form = new CommentForm();
		CommentScoreForm scoreForm = new CommentScoreForm();
		DeleteForm deleteForm = new DeleteForm();
		boolean canComment = false;
		if (movie == null) {
			mav.setViewName("redirect:/app/movies/list");// TODO: check!
			return mav;
		}
		if (session.getAttribute(USER_PARAM_ID)!=null) {
			int id = (int) (session.getAttribute(USER_PARAM_ID));
			User user = users.findById(id);
			if (user != null) {
				canComment = movie.isCommentableBy(user);
			}
			session.setAttribute(MOVIE_ID, movie);
		}
		mav.addObject("commentScoreForm", scoreForm);
		mav.addObject("delete", deleteForm);
		mav.addObject("commentForm", form);
		mav.addObject(MOVIE_ID, movie);
		mav.addObject(CAN_COMMENT_ID, canComment);
		setMovieInSession(movie,session);
		setUserInMav(session, mav);
		mav.setViewName("movies/single");
		return mav;
	}

	private void setMovieInSession(Movie movie,HttpSession session) {
		session.setAttribute(MOVIE_PARAM_ID, movie.getId());
	}

	private void setUserInMav(HttpSession session, ModelAndView mav) {
		if (session.getAttribute(USER_PARAM_ID) != null) {
			User user = users.findById((int) session
					.getAttribute(USER_PARAM_ID));
			mav.addObject(USER_ID, user);
		}
	}
}
