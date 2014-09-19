package ar.edu.itba.paw.g4.service.impl;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLCommentDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;
import ar.edu.itba.paw.g4.service.CommentService;

public class CommentServiceImpl implements CommentService {
	private static final CommentService instance = new CommentServiceImpl();

	public static CommentService getInstance() {
		return instance;
	}

	private CommentServiceImpl() {
	}

	private final CommentDAO commentDAO = PSQLCommentDAO.getInstance();
	private final MovieDAO movieDAO = PSQLMovieDAO.getInstance();

	@Override
	public List<Comment> getCommentsOf(User user) {
		checkArgument(user, notNull());
		return commentDAO.getAllByUser(user);
	}

	@Override
	public void addComment(Comment comment) {
		checkArgument(comment, notNull());

		commentDAO.save(comment);
		Movie movie = comment.getMovie();
		movie.addComment(comment);
		movieDAO.save(movie);
	}

}
