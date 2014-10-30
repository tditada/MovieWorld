package ar.edu.itba.paw.g4.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class LastMovieFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		int movie_id = (int) httpRequest.getSession().getAttribute(
//				MoviesController.MOVIE_ID);
//		if (movie_id>0) {
//			httpRequest.setAttribute(MoviesController.MOVIE_ID, movie_id);
//		}
		chain.doFilter(request, response);
	}

}
