package ar.edu.itba.paw.g4.web;

import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.service.impl.MovieServiceImpl;

@SuppressWarnings("serial")
public class MovieListServlet extends HttpServlet {
	private static final String GENRES_ID = "genres";
	private static final String DIRECTORS_ID = "directors";
	private static final String MOVIES_ID = "movies";
	private static final String FILTER_BY_GENRE_ID = "genre";
	private static final String FILTER_BY_DIRECTOR_ID = "director";

	private final MovieService movieService = MovieServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute(GENRES_ID, MovieGenres.values());
		request.setAttribute(DIRECTORS_ID, movieService.getAllDirectors());

		String filterGenreParam = request.getParameter(FILTER_BY_GENRE_ID);
		String filterDirectorParam = request
				.getParameter(FILTER_BY_DIRECTOR_ID);
		List<Movie> moviesList;

		try {
			if (filterGenreParam == null && filterDirectorParam == null) {
				moviesList = movieService.getMovieList();
			} else if (filterGenreParam != null) {
				MovieGenres filterGenre = MovieGenres.valueOf(filterGenreParam);
				moviesList = movieService.getAllMoviesByGenre(filterGenre);
			} else {
				Director director = Director.builder()
						.withName(filterDirectorParam).build();
				moviesList = movieService.getAllMoviesByDirector(director);
			}

			request.setAttribute(MOVIES_ID, moviesList);

			request.getRequestDispatcher("/WEB-INF/jsp/showMovies.jsp")
					.forward(request, response);
		} catch (ServiceException e) {
			manageError(e, request, response);
		}
	}
}
