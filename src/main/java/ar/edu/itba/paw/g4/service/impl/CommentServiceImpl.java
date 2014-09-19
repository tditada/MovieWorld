package ar.edu.itba.paw.g4.service.impl;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLCommentDAO;
import ar.edu.itba.paw.g4.service.CommentService;

public class CommentServiceImpl implements CommentService {
	private static final CommentService instance = new CommentServiceImpl();

	public static CommentService getInstance() {
		return instance;
	}

	private CommentServiceImpl() {
	}

	private CommentDAO commentDAO = PSQLCommentDAO.getInstance();

	@Override
	public List<Comment> getCommentsOf(User user) {
		checkArgument(user, notNull());
			return commentDAO.getAllByUser(user);
	}

	@Override
	public void addComment(Comment comment) {
		checkArgument(comment, notNull());
			commentDAO.save(comment);
	
	}

}
