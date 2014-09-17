package ar.edu.itba.paw.g4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.EmailAddress;

@SuppressWarnings("serial")
public class RegistrationController extends HttpServlet {
	private UserService userservice = UserServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//TODO 2: VALIDO LAS COSAS (contraseñas iguales, etc)
		
		//TODO 1: mando al servicio de usuario para que registre al tipo
		//User user=new User().builder().withFirstName(req.getParameter("name")).withEmail(EmailAddress.build(req.getParameter("email"))).build();
		//PREGUNTAR JP: ¿DATETIME? ¿CUANDO AGREGO EL ID(se agrega en el dao con eso de persist??
		//userservice.Register(user);
		
		//TODO 3: MANEJO DE ERRORS (JSP BOOLEANS EN EL REQ).
		super.doPost(req, resp);
	}

}
