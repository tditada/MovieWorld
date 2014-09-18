package ar.edu.itba.paw.g4.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;

public class UserFilter implements Filter {
	private FilterConfig filterConfig;
	private UserService userService = UserServiceImpl.getInstance();
	private static String NAME_ID = "name";
	private static String LAST_NAME = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS = "password";
	private static String BIRTHDAY = "birthday";

	@Override
	public void init(FilterConfig fil) throws ServletException {
		filterConfig = fil;
	}

	@Override
	public void destroy() {
		// Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		// TODO:¿Está bien este casteo? HttpServletRequest extiende HttpServlet
		if (req.getAttribute("user") == null && userHasSession((HttpServletRequest)req)) {
			HttpSession session = ((HttpServletRequest) req).getSession();
			User user = userService.getUserById((Integer) session .getAttribute("id"));
			req.setAttribute("user", user);
			/*
			 * FIXME: not compiling User user =
			 * userService.getUserById((Integer) session .getAttribute("id"));
			 * req.setAttribute("user", user);
			 * 
			 * ???
			 */
		}
		chain.doFilter(req, rsp);
	}
	
	private boolean userHasSession(HttpServletRequest request) {
		 HttpSession session = request.getSession();
		 return !SessionAttributesAreNull(session);
	}
	
	 private boolean SessionAttributesAreNull(HttpSession session){
		 return session.getAttribute(NAME_ID)==null &&
		 session.getAttribute(LAST_NAME)==null &&
		 session.getAttribute(EMAIL_ID)==null && session.getAttribute(PASS)==null
		 && session.getAttribute(BIRTHDAY)==null;
	 }

}
