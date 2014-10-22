package ar.edu.itba.paw.g4.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.web.controller.MoviesController;

public class LastMovieFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Movie movie = (Movie) httpRequest.getSession().getAttribute(
				MoviesController.MOVIE_ID);
		if (movie != null) {
			httpRequest.setAttribute(MoviesController.MOVIE_ID, movie);
		}
		chain.doFilter(request, response);
	}

}
