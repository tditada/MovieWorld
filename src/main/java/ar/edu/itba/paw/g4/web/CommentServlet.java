package ar.edu.itba.paw.g4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.CommentService;
import ar.edu.itba.paw.g4.service.impl.CommentServiceImpl;
import ar.edu.itba.paw.g4.web.filter.UserFilter;

@SuppressWarnings("serial")
public class CommentServlet extends HttpServlet {
	private static final String COMMENT_TEXT_ID = "commentText";
	private static final String COMMENT_SCORE_ID = "commentScore";

	private final CommentService commentService = CommentServiceImpl
			.getInstance();

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Movie movie = (Movie) request.getAttribute(MovieServlet.MOVIE_ID);

		User user = (User) (request.getAttribute(UserFilter.USER_ID));
		String text = request.getParameter(COMMENT_TEXT_ID);
		Integer score = Integer.valueOf(request.getParameter(COMMENT_SCORE_ID));
		DateTime creationDate = DateTime.now();

		Comment comment = Comment.builder().withMovie(movie).withUser(user)
				.withText(text).withScore(score).withCreationDate(creationDate)
				.build();
		commentService.addComment(comment);
		response.sendRedirect(request.getHeader("referer"));
	}
}
