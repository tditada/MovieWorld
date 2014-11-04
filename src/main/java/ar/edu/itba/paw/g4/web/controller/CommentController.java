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
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.web.form.NewCommentForm;
import ar.edu.itba.paw.g4.web.form.ScoreCommentForm;
import ar.edu.itba.paw.g4.web.form.validation.NewCommentFormValidator;

@Controller
@RequestMapping("/comment")
public class CommentController {
	private static final String USER_ID = "user";
	private static final String MOVIE_ID = "user";
	private static final String COMMENT_ID = "comment";

	private NewCommentFormValidator newCommentFormValidator;

	@Autowired
	public CommentController(NewCommentFormValidator newCommentFormValidator) {
		this.newCommentFormValidator = newCommentFormValidator;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView comment(NewCommentForm newCommentForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = (User) session.getAttribute(USER_ID);
		Movie movie = (Movie) session.getAttribute(MOVIE_ID);

		if (user == null || movie == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		newCommentFormValidator.validate(newCommentForm, errors);
		if (!errors.hasErrors()) {
			Comment comment = Comment.builder().withMovie(movie).withUser(user)
					.withText(newCommentForm.getCommentText())
					.withScore(newCommentForm.getMovieScore()).build();
			user.addComment(comment);
		}
		// mav.addObject("movie", movie);
		mav.setViewName("redirect:/app/movies/detail?id=" + movie.getId());
		return mav;
	}

	@RequestMapping(value = "score", method = RequestMethod.POST)
	public ModelAndView score(ScoreCommentForm scoreCommentForm, HttpSession session,
			Errors errors) {
		ModelAndView mav = new ModelAndView();

		User user = (User) session.getAttribute(USER_ID);
		Movie movie = (Movie) session.getAttribute(MOVIE_ID);

		if (user == null || movie == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		if (!errors.hasErrors()) { // TODO: check!
			Comment comment = scoreCommentForm.getComment();
			comment.addScore(user, scoreCommentForm.getScore());
		}

		mav.addObject(MOVIE_ID, movie);
		mav.setViewName("redirect:/app/movies/detail?id=" + movie.getId());
		return mav;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(
			@RequestParam(value = COMMENT_ID, required = true) Comment comment,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = (User) session.getAttribute(USER_ID);

		if (user == null || !user.isAdmin()) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		user.removeComment(comment);
		mav.setViewName("redirect:/app/home");
		return mav;
	}
}
