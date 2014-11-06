package ar.edu.itba.paw.g4.model.comment;

import java.util.List;

import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.Orderings;

public interface CommentRepo {
	Comment findById(int id);

	void save(Comment comment);

	void remove(User admin, Comment comment);

	List<Comment> findReportedOrderedByReports(Orderings ordering);
}
