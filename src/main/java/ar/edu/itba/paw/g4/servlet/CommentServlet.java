package ar.edu.itba.paw.g4.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.filter.UserFilter;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.CommentService;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.CommentServiceImpl;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class CommentServlet extends HttpServlet {
	CommentService commentService = CommentServiceImpl.getInstance();
	MovieService movieService = MovieServiceImpl.getInstance();
	private static String COMMENTTEXT_ID = "commentText";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Movie movie=(Movie) req.getAttribute(MovieServlet.MOVIE_ID);
		
		User user=(User)(req.getAttribute(UserFilter.USER_ID));
		String text=req.getParameter(COMMENTTEXT_ID);
		Integer score=Integer.valueOf(req.getParameter("commentScore"));
		DateTime date=DateTime.now();
		
		Comment comment = Comment.builder().withMovie(movie)
						.withUser(user)
						.withText(text)
						.withScore(score)
						.withCreationDate(date).build();
		commentService.addComment(comment);
		resp.sendRedirect(resp.getHeader("referer"));
	}
}
