package ar.edu.itba.paw.g4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.CommentService;
import ar.edu.itba.paw.g4.service.impl.CommentServiceImpl;

@SuppressWarnings("serial")
public class UserCommentsController extends HttpServlet {
	private CommentService commentService = CommentServiceImpl.getInstance();
	private static String USER_ID = "user";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getAttribute(USER_ID);
		try{
			List<Comment> commentList=commentService.getCommentsOf(user);
			req.setAttribute("comments",commentList);
			req.getRequestDispatcher("/WEB-INF/jsp/usercomments.jsp").forward(req,resp);
		}catch(ServiceException e){
			manageError(e,req,resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
