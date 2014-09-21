package ar.edu.itba.paw.g4.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.web.MovieServlet;

public class LastMovieFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Movie movie = (Movie) httpRequest.getSession().getAttribute(
				MovieServlet.MOVIE_ID);
		if (movie != null) {
			httpRequest.setAttribute(MovieServlet.MOVIE_ID, movie);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
