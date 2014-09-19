package ar.edu.itba.paw.g4.servlet;

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
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.CommentServiceImpl;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class CommentServlet extends HttpServlet {
	CommentService commentService = CommentServiceImpl.getInstance();
	MovieService movieService = MovieServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		(Integer.parseInt(req.getParameter("commentMovie"))
//		Comment comment = Comment.builder().withMovie((Movie)movieService.getMovieById(Integer.valueOf(1)))
//						.withUser((User) req.getSession().getAttribute("user"))
//						.withText(req.getParameter("commentText"))
//						.withScore(Integer.valueOf(req.getParameter("commentScore")))
//						.withCreationDate(DateTime.now()).build();
//		commentService.addComment(comment);
		req.getRequestDispatcher(resp.getHeader("referer")).forward(
				req, resp);
	}
}
