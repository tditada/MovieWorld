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

import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;

public class UserFilter implements Filter {
	private FilterConfig filterConfig;
	private UserService userService = UserServiceImpl.getInstance();

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
		if (req.getAttribute("user") == null && userService.userHasSession()) {
			HttpSession session = ((HttpServletRequest) req).getSession();
			/*
			 * FIXME: not compiling User user =
			 * userService.getUserById((Integer) session .getAttribute("id"));
			 * req.setAttribute("user", user);
			 */
		}
		chain.doFilter(req, rsp);
	}

}
