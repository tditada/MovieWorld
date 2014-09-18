package ar.edu.itba.paw.g4.controller;

import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;
import ar.edu.itba.paw.g4.exception.ServiceException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class MovieDetailController extends HttpServlet {
	public static final String MOVIE_PARAM_ID = "id";
	public static final String MOVIE_ID = "movie";

	private MovieService movieService = MovieServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String movieIdParam = request.getParameter(MOVIE_PARAM_ID);
		if (movieIdParam == null) {
			response.sendRedirect(response.encodeRedirectURL("/index"));/*
																		 * TODO:
																		 * check
																		 * !
																		 */
		}
		int movieId = Integer.valueOf(movieIdParam);
		try{
		Movie movie = movieService.getMovieById(movieId);
		request.setAttribute(MOVIE_ID, movie);

		request.getRequestDispatcher("/WEB-INF/jsp/showMovie.jsp").forward(
				request, response);
		}catch(ServiceException e){
			manageError(e,request,response);
		}
	}

}
