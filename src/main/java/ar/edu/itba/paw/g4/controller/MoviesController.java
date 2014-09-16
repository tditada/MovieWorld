package ar.edu.itba.paw.g4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class MoviesController extends HttpServlet {
	public static final String GENRES_ID = "genres";
	public static final String MOVIES_ID = "movies";

	private MovieService movieService = MovieServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute(GENRES_ID, MovieGenres.values());

		List<Movie> moviesList = movieService.getMovieList();
		request.setAttribute(MOVIES_ID, moviesList);

		request.getRequestDispatcher("/WEB-INF/jsp/showMovies.jsp").forward(
				request, response);
	}

}
