package ar.edu.itba.paw.g4.web;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.CommentService;
import ar.edu.itba.paw.g4.web.filter.UserFilter;

@Controller
public class CommentController {
	private static final String COMMENT_TEXT_ID = "commentText";
	private static final String COMMENT_SCORE_ID = "commentScore";
	private static final String USER_ID = "user";
	private static final String COMMENTS_ID = "comments";

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String comment(
			@RequestParam(value = MovieController.MOVIE_ID, required = true) Movie movie,
			@RequestParam(value = UserFilter.USER_ID, required = true) User user,
			@RequestParam(value = COMMENT_TEXT_ID, required = true) String text,
			@RequestParam(value = COMMENT_SCORE_ID, required = true) int score) {
		DateTime creationDate = DateTime.now();

		Comment comment = Comment.builder().withMovie(movie).withUser(user)
				.withText(text).withScore(score).withCreationDate(creationDate)
				.build();
		commentService.addComment(comment);

		// FIXME response.sendRedirect(request.getHeader("referer"));
		return "redirect:movies/detail?" + MovieController.MOVIE_ID + "="
				+ movie.getId();
	}

	@RequestMapping(method = RequestMethod.GET, value = "me/comments/all")
	public ModelAndView userComments(
			@RequestParam(value = USER_ID, required = true) User user) {
		// try {
		List<Comment> commentList = commentService.getCommentsOf(user);
		ModelAndView mav = new ModelAndView();
		mav.addObject(COMMENTS_ID, commentList);
		mav.setViewName("/WEB-INF/jsp/userComments.jsp");
		return mav;
		// TODO } catch (ServiceException e) {
		// manageError(e, req, resp);
		// }
	}
}
