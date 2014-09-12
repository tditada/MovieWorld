package ar.edu.itba.paw.g4.filters;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.paw.g4.services.GenericUserService;
import ar.edu.itba.paw.g4.services.UserService;

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
		GenericUserService userService = new UserService((HttpServletRequest) req);
		if (userService.userHasSession() && req.getAttribute("user")==null) {
			// LE PIDO AL SERVICE QUE ME ARME EL USUARIO
			// EL USUARIO LO METO EN EL REQUEST
//			req.setAttribute("user",);
			return;
		}
		
	}


}