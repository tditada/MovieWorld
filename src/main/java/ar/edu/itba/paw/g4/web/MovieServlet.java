package ar.edu.itba.paw.g4.web;

import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.CommentService;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.CommentServiceImpl;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class MovieServlet extends HttpServlet {
	public static final String MOVIE_PARAM_ID = "id";
	public static final String MOVIE_ID = "movie";
	public static final String COMMENT_LIST_ID = "comments";
	private static final String USER_ID = "user";
	public static final String ABLE_TO_COMMENT_ID = "ableToComment";

	private final MovieService movieService = MovieServiceImpl.getInstance();
	private final CommentService commentService = CommentServiceImpl
			.getInstance();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String movieIdParam = request.getParameter(MOVIE_PARAM_ID);
		if (movieIdParam == null) {
			response.sendRedirect(response.encodeRedirectURL("index"));/*
																		 * TODO:
																		 * check
																		 * !
																		 */
		}
		int movieId = Integer.valueOf(movieIdParam);
		try {
			Movie movie = movieService.getMovieById(movieId);
			List<Comment> comments = commentService.getCommentsFor(movie);
			request.setAttribute(COMMENT_LIST_ID, comments);
			request.setAttribute(MOVIE_ID, movie);
			request.getSession().setAttribute(MOVIE_ID, movie);

			boolean canComment = movie.getReleaseDate().isAfter(DateTime.now());
			request.setAttribute(ABLE_TO_COMMENT_ID, canComment);

			if (canComment) {
				for (Comment comment : comments) {
					User user = comment.getUser();
					User requestUser = (User) request.getAttribute(USER_ID);
					if (requestUser != null
							&& user.getId().equals(requestUser.getId())) {
						request.setAttribute(ABLE_TO_COMMENT_ID, false);
					}
				}
			}

			request.getRequestDispatcher("/WEB-INF/jsp/showMovie.jsp").forward(
					request, response);
		} catch (ServiceException e) {
			manageError(e, request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
