package ar.edu.itba.paw.g4.web.controller;

import static ar.edu.itba.paw.g4.web.ErrorHelpers.errorViewRedirect;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.CommentService;
import ar.edu.itba.paw.g4.service.MovieService;

@Controller
public class MoviesController {
	public static final String MOVIE_PARAM_ID = "id";
	public static final String MOVIE_ID = "movie";
	public static final String COMMENT_LIST_ID = "comments";
	private static final String USER_ID = "user";
	public static final String CAN_COMMENT_ID = "ableToComment";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";
	private static final String FILTER_BY_GENRE_ID = "genre";
	private static final String FILTER_BY_DIRECTOR_ID = "director";

	private MovieService movieService;
	private CommentService commentService;

	@Autowired
	MoviesController(MovieService movieService, CommentService commentService) {
		this.movieService = movieService;
		this.commentService = commentService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = FILTER_BY_GENRE_ID, required = false) String genreName,
			@RequestParam(value = FILTER_BY_DIRECTOR_ID, required = false) String directorName) {
		try {
			List<Movie> movies;
			if (genreName == null && directorName == null) {
				movies = movieService.getMovieList();
			} else if (genreName != null) {
				MovieGenres filterGenre = MovieGenres.valueOf(genreName);
				movies = movieService.getAllMoviesByGenre(filterGenre);
			} else {
				Director director = Director.builder().withName(directorName)
						.build();
				movies = movieService.getAllMoviesByDirector(director);
			}

			ModelAndView mav = new ModelAndView();
			mav.addObject(GENRES_ID, MovieGenres.values());
			mav.addObject(DIRECTORS_ID, movieService.getAllDirectors());
			mav.addObject(MOVIES_ID, movies);
			mav.setViewName("movies/all");
			return mav;
		} catch (ServiceException e) {
			return errorViewRedirect(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam(MOVIE_PARAM_ID) Integer movieId,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (movieId == null) {
			mav.setViewName("redirect:/app/index");// TODO: check!
			return mav;
		}
		try {
			Movie movie = movieService.getMovieById(movieId);
			List<Comment> comments = commentService.getCommentsFor(movie);
			mav.addObject(COMMENT_LIST_ID, comments);
			mav.addObject(MOVIE_ID, movie);

			session.setAttribute(MOVIE_ID, movie);

			User user = (User) session.getAttribute(USER_ID);
			boolean canComment = false;
			if (user != null) {
				canComment = commentService.userCanCommentOnMovie(user, movie);
			}
			mav.addObject(CAN_COMMENT_ID, canComment);

			mav.setViewName("movies/single");
			return mav;
		} catch (ServiceException e) {
			return errorViewRedirect(e);
		}
	}
}
