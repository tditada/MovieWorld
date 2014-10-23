package ar.edu.itba.paw.g4.service;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;

public interface CommentService {
	public void addComment(Comment comment);

	public boolean userCanCommentOnMovie(User user, Movie movie);
}