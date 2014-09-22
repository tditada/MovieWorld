package ar.edu.itba.paw.g4.service.impl;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import ar.edu.itba.paw.g4.exception.ServiceException;
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

	private final CommentDAO commentDAO = PSQLCommentDAO.getInstance();
	private final MovieDAO movieDAO = PSQLMovieDAO.getInstance();

	public static CommentService getInstance() {
		return instance;
	}

	private CommentServiceImpl() {
	}

	@Override
	public List<Comment> getCommentsOf(User user) {
		checkArgument(user, notNull());
		return commentDAO.getAllByUser(user);
	}

	@Override
	public void addComment(Comment comment) {
		checkArgument(comment, notNull());

		Movie movie = movieDAO.getById(comment.getMovie().getId());
		if (movie == null) {
			throw new ServiceException(
					"Cannot comment on a movie not added to the system");
		}

		User user = comment.getUser();
		if (!userCanCommentOnMovie(user, movie)) {
			throw new ServiceException("User (id=" + user.getId()
					+ ") has already commented on movie (id=" + movie.getId()
					+ ")");
		}

		commentDAO.save(comment);
		movie.addComment(comment);
		movieDAO.save(movie);
	}

	@Override
	public List<Comment> getCommentsFor(Movie movie) {
		checkArgument(movie, notNull());
		return commentDAO.getAllByMovie(movie);
	}

	@Override
	public boolean userCanCommentOnMovie(User user, Movie movie) {
		checkArgument(user, notNull());
		checkArgument(movie, notNull());
		if (!movie.isCommentable()) {
			return false;
		}
		List<Comment> comments = commentDAO.getAllByMovieAndUser(movie, user);
		return comments.isEmpty();
	}
}
