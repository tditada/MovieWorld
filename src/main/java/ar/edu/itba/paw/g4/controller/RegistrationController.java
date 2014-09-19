package ar.edu.itba.paw.g4.controller;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static ar.edu.itba.paw.g4.util.validation.Validations.validateRegister;
import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;
import static org.joda.time.DateTime.now;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.EmailAddress;

@SuppressWarnings("serial")
public class RegistrationController extends HttpServlet {
	private UserService userservice = UserServiceImpl.getInstance();
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASSWORD_ID = "password";
	private static String SECONDPASSWORD_ID = "secondPassword";
	private static String BIRTHDAY_ID = "birthday";


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req,
				resp);
	}
	//TODO 1: jsp -> recibir las validaciones y mostrar acorde
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String name=req.getParameter(NAME_ID);
		String lastName=req.getParameter(LASTNAME_ID);
		String email=req.getParameter(EMAIL_ID);
		String password=req.getParameter(PASSWORD_ID);
		String secondPassword=req.getParameter(SECONDPASSWORD_ID);
		String birthday=req.getParameter(BIRTHDAY_ID);
		
		List<Boolean> errors = validateRegister(name, lastName, email, password,secondPassword, birthday);
		for(int i=0;i<=errors.size();i++){
			if(errors.get(i)){
				req.setAttribute("errors", errors);
				req.setAttribute(NAME_ID,name);
				req.setAttribute(LASTNAME_ID,lastName);
				req.setAttribute(EMAIL_ID, email);
				req.setAttribute(BIRTHDAY_ID, birthday);
				doGet(req,resp);
			}
		}
		
		
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("mm-dd-yyyy");
		DateTime birthDate = formatter.parseDateTime(req
				.getParameter(BIRTHDAY_ID));

		User user = User.builder().withFirstName(name)
				.withLastName(lastName)
				.withPassword(password)
				.withEmail(EmailAddress.build(email))
				.withBirthDate(birthDate).build();
		try{
			userservice.register(user);
			req.getRequestDispatcher("login").forward(req, resp);
		}catch(ServiceException e){
			manageError(e,req,resp);
		}
	}
}
