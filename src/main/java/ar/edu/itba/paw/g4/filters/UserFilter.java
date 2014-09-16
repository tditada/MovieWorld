package ar.edu.itba.paw.g4.filters;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;

public class UserFilter implements Filter{
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig fil) throws ServletException {
		filterConfig = fil;
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		//¿Está bien este casteo? HttpServletRequest extiende HttpServlet
		UserService userService = new UserServiceImpl((HttpServletRequest) req);
		if (req.getAttribute("user")==null && userService.userHasSession()) {
			// LE PIDO AL SERVICE QUE ME ARME EL USUARIO
			// EL USUARIO LO METO EN EL REQUEST
//			req.setAttribute("user",);
		}
		chain.doFilter(req, rsp);		
	}


}
