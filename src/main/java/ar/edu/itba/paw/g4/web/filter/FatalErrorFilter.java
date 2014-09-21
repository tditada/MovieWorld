package ar.edu.itba.paw.g4.web.filter;

import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FatalErrorFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		try {
			chain.doFilter(request, response);
//		} catch (Exception e) {
//			Exception error = new Exception("Fatal Error");
//			manageError(error, (HttpServletRequest) request,
//					(HttpServletResponse) response);
//		}

	}

	@Override
	public void destroy() {

	}

}
