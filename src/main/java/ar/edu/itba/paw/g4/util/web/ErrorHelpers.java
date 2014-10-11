package ar.edu.itba.paw.g4.util.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ErrorHelpers {
	private static final String ERROR_ID = "error";
	private static final String ERROR_VIEW_ID = "error.jsp";

	public static ModelAndView errorViewRedirect(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(ERROR_ID, e.getMessage());
		mav.setViewName(ERROR_VIEW_ID);
		return mav;
	}

	public static void manageError(Exception e, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(ERROR_ID, e.getMessage());
		req.getRequestDispatcher(ERROR_VIEW_ID).forward(req,
				resp);
	}
}
