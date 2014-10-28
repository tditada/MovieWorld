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

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.Orderings;
import ar.edu.itba.paw.g4.web.form.InsertMovieForm;

@Controller
@RequestMapping("/movies")
public class MoviesController {
	public static final String MOVIE_PARAM_ID = "id";

	public static final String MOVIE_ID = "movie";
	public static final String COMMENT_LIST_ID = "comments";
	public static final String CAN_COMMENT_ID = "ableToComment";

	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";

	private MovieRepo movies;

	@Autowired
	MoviesController(MovieRepo movies) {
		this.movies = movies;
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public ModelAndView insert() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("insertMovieForm", new InsertMovieForm());
		mav.setViewName("movies/insert");
		return mav;
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public ModelAndView insert(InsertMovieForm insertMovieForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
//		InsertMovieFormValidator.validate(insertMovieForm, errors);
//		if (errors.hasErrors()) {
//			mav.setViewName("/movies/insert");
//			return mav;
//		}
//
		Movie movie = insertMovieForm.build();
		movies.save(movie);
		mav.setViewName("redirect:/app/home");
		return mav;

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "genre", required = false) MovieGenres genre,
			@RequestParam(value = "director", required = false) Director director) {
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
		mav.setViewName("movies/all");
		return mav;
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
		User user = (User) session.getAttribute("user");
		boolean canComment = false;
		if (user != null) {
			canComment = movie.isCommentableBy(user);
		}

		session.setAttribute(MOVIE_ID, movie);

		mav.addObject(MOVIE_ID, movie);
		mav.addObject(CAN_COMMENT_ID, canComment);
		mav.setViewName("movies/single");
		return mav;
	}
}
