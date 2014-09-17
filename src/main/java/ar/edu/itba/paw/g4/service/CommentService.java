package ar.edu.itba.paw.g4.service;

import java.util.List;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.User;

public interface CommentService {
	public List<Comment> getCommentsOf(User user);

	public void addComment(Comment comment);
}