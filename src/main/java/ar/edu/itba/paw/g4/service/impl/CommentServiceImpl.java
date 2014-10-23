package ar.edu.itba.paw.g4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private MovieRepo movies;

	@Autowired
	CommentServiceImpl(MovieRepo movies) {
		this.movies = movies;
	}

	@Override
	public void addComment(Comment comment) {
//		checkArgument(comment, notNull());
//
//		Movie movie = movies.findById(comment.getMovie().getId());
//		if (movie == null) {
//			throw new ServiceException(
//					"Cannot comment on a movie not added to the system");
//		}
//
//		User user = comment.getUser();
//		if (!userCanCommentOnMovie(user, movie)) {
//			throw new ServiceException("User (id=" + user.getId()
//					+ ") has already commented on movie (id=" + movie.getId()
//					+ ")");
//		}
//
//		commentDAO.save(comment);
//		movie.addComment(comment);
//		movies.save(movie);
	}

	@Override
	public boolean userCanCommentOnMovie(User user, Movie movie) {
//		checkArgument(user, notNull());
//		checkArgument(movie, notNull());
//		if (!movie.()) {
//			return false;
//		}
//		List<Comment> comments = commentDAO.getAllByMovieAndUser(movie, user);
//		return comments.isEmpty();
		return true;
	}
}
