package ar.edu.itba.paw.g4.persist;

import java.util.List;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;

public interface CommentDAO extends GenericDAO<Comment> {

	public List<Comment> getAllByMovie(Movie movie);

	public List<Comment> getAllByUser(User user);

	public List<Comment> getAllByMovieAndUser(Movie movie, User user);

}
