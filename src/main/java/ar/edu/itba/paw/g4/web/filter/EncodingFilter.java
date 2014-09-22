package ar.edu.itba.paw.g4.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	private static final String ENCODING_ID = "UTF-8";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(ENCODING_ID);
		chain.doFilter(request, response);
		response.setCharacterEncoding(ENCODING_ID);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
