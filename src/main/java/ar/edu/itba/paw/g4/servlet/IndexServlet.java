package ar.edu.itba.paw.g4.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class IndexServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		UserManager userManager = new CookieUserManager(request, response);
//		User user = userManager.getUser();
//		
//		Horoscope horoscope = horoscopeManager.getHoroscope(user.getSign());
		
//		if (horoscope == null) {
//			response.sendRedirect(response.encodeRedirectURL("errorPage") + "?name=" + user.getName() + "&sign=" + user.getSign());
//			return;
//		}
//		
//		userManager.setUser(user.getName(), user.getSign());
//		
//		request.setAttribute("horoscope", horoscope);
//		request.setAttribute("user", user);
		
		request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	}

}
