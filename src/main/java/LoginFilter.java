
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserManagerServlet userManager = new SessionUserManagerServlet(req);
		if (!userManager.existsUser()) {
			resp.sendRedirect(resp.encodeRedirectURL("login"));
			//TODO: HACER /LOGIN
			return;
		}
		// TODO: Preguntar si sabiendo que ya está registrado el chabon, puedo mantener el objeto user 
		// para no hacer pedidos a la BD.
	}

}
