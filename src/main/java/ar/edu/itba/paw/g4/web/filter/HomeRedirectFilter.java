package ar.edu.itba.paw.g4.web.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

//@Component
public class HomeRedirectFilter extends OncePerRequestFilter {

//	@Autowired
//	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
//		Set<RequestMappingInfo> mappings = requestMappingHandlerMapping
//				.getHandlerMethods().keySet();
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String redirectUrl = httpResponse.encodeRedirectURL("home");
		httpResponse.sendRedirect(redirectUrl);
	}

}
