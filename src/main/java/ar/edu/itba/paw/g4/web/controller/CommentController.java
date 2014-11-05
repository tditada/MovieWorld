package ar.edu.itba.paw.g4.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;
import ar.edu.itba.paw.g4.web.form.NewCommentForm;
import ar.edu.itba.paw.g4.web.form.ScoreCommentForm;
import ar.edu.itba.paw.g4.web.form.validation.NewCommentFormValidator;

@Controller
@RequestMapping("/comment")
public class CommentController {
	private static final String MOVIE_ID = "movie";

	private static final String SESSION_USER_ID = "user";
	private static final String SESSION_MOVIE_ID = "movie";

	private NewCommentFormValidator newCommentFormValidator;
	private UserRepo users;
	private MovieRepo movies;

	@Autowired
	public CommentController(UserRepo users, MovieRepo movies,
			NewCommentFormValidator newCommentFormValidator) {
		this.newCommentFormValidator = newCommentFormValidator;
		this.users = users;
		this.movies = movies;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView comment(NewCommentForm newCommentForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);
		Movie movie = getMovieFromSession(session);

		if (user == null || movie == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		String prevUrl = "/app/movies/detail?movie=" + movie.getId();

		newCommentFormValidator.validate(newCommentForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName(prevUrl);
			return mav;
		}
		Comment comment = Comment.builder().withMovie(movie).withUser(user)
				.withText(newCommentForm.getCommentText())
				.withScore(newCommentForm.getMovieScore()).build();
		user.addComment(comment);
		// mav.addObject("movie", movie); TODO: check!
		mav.setViewName("redirect:" + prevUrl);
		return mav;
	}

	@RequestMapping(value = "score", method = RequestMethod.POST)
	public ModelAndView score(ScoreCommentForm scoreCommentForm,
			HttpSession session, Errors errors) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);
		Movie movie = getMovieFromSession(session);

		if (user == null || movie == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		String prevUrl = "/app/movies/detail?movie=" + movie.getId();
		if (errors.hasErrors()) {
			mav.setViewName(prevUrl);
			return mav;
		}
		Comment comment = scoreCommentForm.getComment();
		comment.addScore(user, scoreCommentForm.getScore());

		mav.addObject(MOVIE_ID, movie);
		mav.setViewName("redirect:" + prevUrl);
		return mav;
	}

	@RequestMapping(value = "report", method = RequestMethod.POST)
	public ModelAndView report(@RequestParam(required = false) Comment comment,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);

		if (user == null || comment == null || !comment.isReportableBy(user)) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		comment.addReport(user);

		Movie movie = comment.getMovie();

		mav.addObject(MOVIE_ID, movie);
		mav.setViewName("redirect:/app/movies/detail?movie=" + movie.getId());
		return mav;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(@RequestParam(required = false) Comment comment,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = getLoggedUserFromSession(session);

		if (comment != null && user != null && user.isAdmin()) {
			user.removeComment(comment);
		}
		mav.setViewName("redirect:/app/home");
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

}
