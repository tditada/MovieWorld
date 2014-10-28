package ar.edu.itba.paw.g4.web.controller;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;

@Controller
@RequestMapping("/comment")
public class CommentController {
	private static final String COMMENT_TEXT_ID = "commentText";
	private static final String COMMENT_SCORE_ID = "commentScore";

	public CommentController() {
	}

	@RequestMapping(method = RequestMethod.POST)
	public String comment(
			@ModelAttribute Movie movie,
			@ModelAttribute User user,
			@RequestParam(value = COMMENT_TEXT_ID, required = true) String text,
			@RequestParam(value = COMMENT_SCORE_ID, required = true) int score) {
		DateTime creationDate = DateTime.now();

		Comment comment = Comment.builder().withMovie(movie).withUser(user)
				.withText(text).withScore(score).withCreationDate(creationDate)
				.build();
		user.addComment(comment);

		// FIXME response.sendRedirect(request.getHeader("referer"));
		return "redirect:/app/movies/detail?" + MoviesController.MOVIE_PARAM_ID
				+ "=" + movie.getId();
	}

}
