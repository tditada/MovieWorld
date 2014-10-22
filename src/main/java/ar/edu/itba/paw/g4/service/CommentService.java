package ar.edu.itba.paw.g4.service;

import java.util.List;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;

public interface CommentService {
	public List<Comment> getCommentsOf(User user);

	public List<Comment> getCommentsFor(Movie movie);

	public void addComment(Comment comment);

	public boolean userCanCommentOnMovie(User user, Movie movie);
}