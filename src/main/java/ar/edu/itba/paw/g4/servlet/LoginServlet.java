package ar.edu.itba.paw.g4.servlet;

import static ar.edu.itba.paw.g4.util.validation.Validations.isLoginValid;
import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.enums.LoginField;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.EmailAddress;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private UserService userService = UserServiceImpl.getInstance();
	// private static UserService instance;
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
	private static String BIRTHDAY_ID = "birthday";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	// TODO: Validaciones (que el jsp verifique)
	// ¿Manejo de errores?
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String emailParam = req.getParameter(EMAIL_ID);
			EmailAddress email = EmailAddress.build(emailParam);
			String password = req.getParameter(PASS_ID);
			List<Boolean> errors = new LinkedList<Boolean>();
			
			if(!isLoginValid(emailParam,password,errors)){
				for(int i=0;i<errors.size();i++){
					int fieldEnum = LoginField.values()[i].value;
					req.setAttribute("error"+fieldEnum, errors.get(i));
				}
				req.setAttribute(EMAIL_ID, emailParam);
				req.setAttribute(PASS_ID,password);
				doGet(req,resp);				
			}
			
			User user = userService.authenticate(email, password);
			createUserSession(user, req);
			if(req.getHeader("referer").equals("http://localhost:8081/MovieWorld/login")){ //XXX
				resp.sendRedirect("home");

			}else{
				resp.sendRedirect(req.getHeader("referer"));
			}
		} catch (ServiceException e) {
			manageError(e, req, resp);
		}
	}

	private void createUserSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getEmail());
		session.setAttribute(LASTNAME_ID, user.getLastName());
		session.setAttribute(PASS_ID, user.getPassword());
		session.setAttribute(BIRTHDAY_ID, user.getBirthDate());
	}

}
