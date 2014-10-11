package ar.edu.itba.paw.g4.web.filter;

import static ar.edu.itba.paw.g4.util.web.ErrorHelpers.manageError;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class FatalErrorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			Exception error = new Exception("Fatal Error");
			manageError(error, (HttpServletRequest) request,
					(HttpServletResponse) response);
		}
	}

}
