package ar.edu.itba.paw.g4.util.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorHelper {
	private static final String ERROR_ID = "error";

	public static void manageError(Exception e, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(ERROR_ID, e.getMessage());
		req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
	}
}
