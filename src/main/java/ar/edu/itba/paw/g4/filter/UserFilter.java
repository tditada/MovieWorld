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
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
//	private static String PASS2_ID = "secondPassword";
	private static String BIRTHDAY_ID = "birthday";

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
		// TODO: ¿Está bien este casteo? HttpServletRequest extiende HttpServlet
		if (req.getAttribute("user") == null && userHasSession((HttpServletRequest)req)) {
			HttpSession session = ((HttpServletRequest) req).getSession();
			User user = userService.getUserById((Integer) session .getAttribute("id"));
			req.setAttribute("user", user);
		}
		chain.doFilter(req, rsp);
	}
	
	public boolean userHasSession(HttpServletRequest req) {
	 HttpSession session = req.getSession();
	 return SessionAttributesAreNull(session);
}
	
	 private boolean SessionAttributesAreNull(HttpSession session){
		 return session.getAttribute(NAME_ID)==null &&
		 session.getAttribute(LASTNAME_ID)==null &&
		 session.getAttribute(EMAIL_ID)==null && session.getAttribute(PASS_ID)==null
		 && session.getAttribute(BIRTHDAY_ID)==null;
		 }

}
