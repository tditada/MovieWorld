package ar.edu.itba.paw.g4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class MovieHomeController extends HttpServlet {
	private static final String TOP_MOVIES_ID = "topMovies";
	private static final String RELEASES_ID = "releases";
	private static final String NEW_ADDITIONS_ID = "newAdditions";

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;

	private MovieService movieService = MovieServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Movie> topMovies = movieService.getTopMovies(TOP_MOVIES_QUANTITY);
		request.setAttribute(TOP_MOVIES_ID, topMovies);

		List<Movie> newAdditions = movieService
				.getNewAdditions(NEW_ADDITIONS_QUANTITY);
		request.setAttribute(NEW_ADDITIONS_ID, newAdditions);

		List<Movie> releases = movieService.getReleases();
		request.setAttribute(RELEASES_ID, releases);

		request.getRequestDispatcher("/WEB-INF/jsp/moviesHome.jsp").forward(
				request, response);
	}
}
