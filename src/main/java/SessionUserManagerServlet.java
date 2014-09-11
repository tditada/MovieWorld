import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUserManagerServlet implements UserManagerServlet{
	// First name, last name, email & pass
	private static String NAME_ID = "name";
	private static String EMAIL_ID = "email";
	private HttpServletRequest request;
	
	public SessionUserManagerServlet(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public boolean existsUser() {
		HttpSession session = request.getSession();
		return ((session.getAttribute(NAME_ID) != null && session.getAttribute(EMAIL_ID) != null) ||
				(request.getParameter(NAME_ID) != null && request.getParameter(EMAIL_ID) != null));
	}
	
	@Override
	public String getName() {
		return getByID(NAME_ID);
	}
	
	
	@Override
	public String getEmail() {
		return getByID(EMAIL_ID);
	}
	
	@Override
	public void setUser(String name, String sign) {
		HttpSession session = request.getSession();
		
	//		session.setAttribute(NAME_ID, name);
	//		session.setAttribute(EMAIL_ID, sign);
	}
	
	@Override
	public void resetUser(String name) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);
	}
	
	private String getByID(String id) {
		HttpSession session = request.getSession();
		String value = (String)session.getAttribute(id);
		if (value != null) {
			return value;
		} else {
			return request.getParameter(id);
		}
	}

}