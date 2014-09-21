package ar.edu.itba.paw.g4.web.filter;

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
	private static final String FIRST_NAME_ID = "firstname";
	private static final String LAST_NAME_ID = "lastname";
	private static final String EMAIL_ID = "email";
	private static final String PASS_ID = "password";
	private static final String BIRTHDAY_ID = "birthday";

	public static final String USER_ID = "user";

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	public void init(FilterConfig fil) throws ServletException {
	}

	@Override
	public void destroy() {
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
		return session.getAttribute(FIRST_NAME_ID) == null
				&& session.getAttribute(LAST_NAME_ID) == null
				&& session.getAttribute(EMAIL_ID) == null
				&& session.getAttribute(PASS_ID) == null
				&& session.getAttribute(BIRTHDAY_ID) == null;
	}

}
