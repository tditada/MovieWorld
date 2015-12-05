package ar.edu.itba.paw.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class EncodingFilter extends GenericFilterBean {
	private static final String ENCODING_ID = "UTF-8";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(ENCODING_ID);
		chain.doFilter(request, response);
		response.setCharacterEncoding(ENCODING_ID);
	}

}
