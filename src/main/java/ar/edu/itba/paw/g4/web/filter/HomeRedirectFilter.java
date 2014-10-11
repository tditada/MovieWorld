package ar.edu.itba.paw.g4.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class HomeRedirectFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String redirectUrl = httpResponse.encodeRedirectURL("home");
		httpResponse.sendRedirect(redirectUrl);
	}

}
