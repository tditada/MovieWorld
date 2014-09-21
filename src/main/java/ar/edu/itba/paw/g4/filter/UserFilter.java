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

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;

public class UserFilter implements Filter {
	public UserService userService = UserServiceImpl.getInstance();
	public static String NAME_ID = "firstname";
	public static String LASTNAME_ID = "lastname";
	public static String EMAIL_ID = "email";
	public static String PASS_ID = "password";
	// private static String PASS2_ID = "secondPassword";
	public static String BIRTHDAY_ID = "birthday";

	public static String USER_ID = "user";

	@Override
	public void init(FilterConfig fil) throws ServletException {
	}

	@Override
	public void destroy() {
		// Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		if (req.getAttribute(USER_ID) == null
				&& userHasSession((HttpServletRequest) req)) {
			HttpSession session = ((HttpServletRequest) req).getSession();
			User user = userService.getUserByEmail((EmailAddress) session
					.getAttribute(EMAIL_ID));
			req.setAttribute(USER_ID, user);
		}
		chain.doFilter(req, rsp);
	}

	private boolean userHasSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return !SessionAttributesAreNull(session);
	}

	private boolean SessionAttributesAreNull(HttpSession session) {
		return session.getAttribute(NAME_ID) == null
				&& session.getAttribute(LASTNAME_ID) == null
				&& session.getAttribute(EMAIL_ID) == null
				&& session.getAttribute(PASS_ID) == null
				&& session.getAttribute(BIRTHDAY_ID) == null;
	}

}
