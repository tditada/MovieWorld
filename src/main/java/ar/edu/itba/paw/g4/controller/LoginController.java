package ar.edu.itba.paw.g4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.EmailAddress;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {
	private UserService userservice = UserServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//TODO: Validaciones (paso booleans en el request y que el jsp verifique)
		userservice.login(EmailAddress.build(req.getParameter("email")), req.getParameter("password"));
		//TODO(2): Pasar lo de session al controller.
		super.doPost(req, resp);
	}
}
