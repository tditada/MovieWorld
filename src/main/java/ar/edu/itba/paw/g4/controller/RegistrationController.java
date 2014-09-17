package ar.edu.itba.paw.g4.controller;

import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getDateTime;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getEmailAddress;
import static ar.edu.itba.paw.g4.util.persist.sql.PSQLQueryHelpers.getString;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

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
	private static String PASS_ID = "password";
	private static String PASS2_ID = "secondPassword";
	private static String BIRTHDAY_ID ="birthday";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
	}
	
	//TODO 2: VALIDO LAS COSAS (contraseñas iguales, etc) > req.getRequestDispatcher(/registration) <-- aca va a hacer un get
	// Le paso como attribute al request los campos (y cuales estan bien y cuales estan mal)
	// jsp -> movieController (movieDetails)
	//TODO 3: MANEJO DE ERRORS > TRY CATCH (A PAGINA DE ERROR O AL HOME CON UN PENDORCHITO)
	
	//TODO 4:LOG OUT? > Controller para logout con un doPost que use el servicio (getById)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = User.builder()
				.withFirstName(req.getParameter(NAME_ID))
				.withLastName(req.getParameter(LASTNAME_ID))
				.withPassword(req.getParameter(PASS_ID))
				.withEmail(EmailAddress.build(req.getParameter(EMAIL_ID)))
				.withBirthDate(new DateTime(req.getParameter(BIRTHDAY_ID))).build();
		
		userservice.register(user);
		req.getRequestDispatcher("login").forward(req, resp);
	}

}
