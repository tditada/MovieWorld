package ar.edu.itba.paw.g4.web.controller;

import static ar.edu.itba.paw.g4.web.ErrorHelpers.errorViewRedirect;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
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
@RequestMapping("/movies")
@SessionAttributes({ "user", "movie" })
public class MoviesController {
	public static final String MOVIE_PARAM_ID = "id";

	public static final String MOVIE_ID = "movie";
	public static final String COMMENT_LIST_ID = "comments";
	public static final String CAN_COMMENT_ID = "ableToComment";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";

	private MovieService movieService;
	private CommentService commentService;

	@Autowired
	MoviesController(MovieService movieService, CommentService commentService) {
		this.movieService = movieService;
		this.commentService = commentService;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "genre", required = false) MovieGenres genre,
			@RequestParam(value = "director", required = false) Director director) {
		try {
			List<Movie> movies;
			if (genre == null && director == null) {
				movies = movieService.getMovieList();
			} else if (genre != null) {
				movies = movieService.getAllMoviesByGenre(genre);
			} else {
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

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(
			@RequestParam(value = MOVIE_PARAM_ID, required = false) Movie movie,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (movie == null) {
			mav.setViewName("redirect:/app/movies/list");// TODO: check!
			return mav;
		}
		try {
			List<Comment> comments = commentService.getCommentsFor(movie);

			User user = (User) session.getAttribute("user");
			boolean canComment = false;
			if (user != null) {
				canComment = commentService.userCanCommentOnMovie(user, movie);
			}

			session.setAttribute(MOVIE_ID, movie);

			mav.addObject(COMMENT_LIST_ID, comments);
			mav.addObject(MOVIE_ID, movie);
			mav.addObject(CAN_COMMENT_ID, canComment);
			mav.setViewName("movies/single");
			return mav;
		} catch (ServiceException e) {
			return errorViewRedirect(e);
		}
	}
}
