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

@Controller
@RequestMapping("/comment")
public class CommentController {
	private static final String COMMENT_TEXT_ID = "commentText";
	private static final String COMMENT_SCORE_ID = "commentScore";
	
	private MovieRepo movies;
	private UserRepo users;
	
	@Autowired
	public CommentController(MovieRepo movies, UserRepo users) {
		this.movies = movies;
		this.users = users;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView comment(CommentForm form, Errors errors, HttpSession session) {
		DateTime creationDate = DateTime.now();
		ModelAndView mav = new ModelAndView();
		if(session.getAttribute("user_id")==null || session.getAttribute("movie_id")==null){
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		
		User user= users.findById((int)session.getAttribute("user_id"));
		Movie movie= movies.findById((int)session.getAttribute("movie_id"));
		System.out.println("comment user"+user);
		System.out.println(movie);
		Comment comment = Comment.builder().withMovie(movie).withUser(user)
				.withText(form.getCommentText()).withScore(form.getCommentScore()).withCreationDate(creationDate)
				.build();
		System.out.println(comment);
		mav.addObject("movie",movie);
		user.addComment(comment);
		mav.setViewName("redirect:/app/movies/detail?id="+movie.getId());
		return mav;
		// FIXME response.sendRedirect(request.getHeader("referer"));
//		return "redirect:/app/movies/detail?" + MoviesController.MOVIE_PARAM_ID
//				+ "=" + movie.getId();
	}

}
