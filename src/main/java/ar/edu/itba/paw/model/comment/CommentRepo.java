package ar.edu.itba.paw.model.comment;

import java.util.List;

import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.util.persist.Orderings;

public interface CommentRepo {
	Comment findById(int id);

	void save(Comment comment);

	void remove(User admin, Comment comment);

	List<Comment> findReportedOrderedByReports(Orderings ordering);
}
