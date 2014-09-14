package ar.edu.itba.paw.g4.persist;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;

public interface CommentDAO extends GenericDAO<Comment> {
	public static enum Attributes {
		ID, MOVIE, USER
	}
}