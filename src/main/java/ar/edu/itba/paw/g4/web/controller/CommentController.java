package ar.edu.itba.paw.g4.web.controller;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;
import ar.edu.itba.paw.g4.web.form.CommentForm;
import ar.edu.itba.paw.g4.web.form.CommentScoreForm;
import ar.edu.itba.paw.g4.web.form.DeleteForm;

@Controller
@RequestMapping("/comment")
public class CommentController {
	// private static final String COMMENT_TEXT_ID = "commentText";
	// private static final String COMMENT_SCORE_ID = "commentScore";

	public static final String MOVIE_PARAM_ID = "movie_id";
	public static final String USER_PARAM_ID = "user_id";
	public static final String COMMENT_ID = "comment";

	private MovieRepo movies;
	private UserRepo users;

	@Autowired
	public CommentController(MovieRepo movies, UserRepo users) {
		this.movies = movies;
		this.users = users;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView comment(CommentForm form, Errors errors,
			HttpSession session) {
		DateTime creationDate = DateTime.now();
		ModelAndView mav = new ModelAndView();
		if (session.getAttribute("user_id") == null
				|| session.getAttribute("movie_id") == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		User user = users.findById((int) session.getAttribute("user_id"));
		Movie movie = movies.findById((int) session.getAttribute("movie_id"));
		Comment comment = Comment.builder().withMovie(movie).withUser(user)
				.withText(form.getCommentText()).withScore(form.getFilmScore())
				.withCreationDate(creationDate).build();
		user.addComment(comment);
		// mav.addObject("movie", movie);
		mav.setViewName("redirect:/app/movies/detail?id=" + movie.getId());
		return mav;
	}

	@RequestMapping(value = "score", method = RequestMethod.POST)
	public ModelAndView score(CommentScoreForm form, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Movie movie = movies.findById((int) session.getAttribute("movie_id"));
		User user = users.findById(form.getUserId());
		updateCommentScore(movie, user, form.getCommentScore()); // updetea
																		// el
																		// score
																		// en
																		// user
																		// y
																		// este
																		// llama
																		// a
																		// hacer
																		// lo
																		// mismo
																		// en
																		// movie
		mav.addObject("movie", movie);
		mav.setViewName("redirect:/app/movies/detail?id=" + movie.getId());
		return mav;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(DeleteForm form, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = users.findById(form.getUserId());
		Comment c = user.getComment(form.getCommentId());
		user.removeComment(c);
		System.out.println(form.getCommentId());
		users.removeComment(form.getCommentId());
		mav.setViewName("redirect:/app/home");
		return mav;
	}
	
	private void updateCommentScore(Movie movie, User user, int score) {
		for(Comment c:user.getComments()){
			if(c.getMovie().equals(movie) && c.getUser().equals(user)){
				c.setCommentScore(user, score);
			}
		}
		
		for(Comment c:movie.getComments()){
			if(c.getMovie().equals(movie) && c.getUser().equals(user)){
				c.setCommentScore(user, score);
			}
		}
		
	}

}
