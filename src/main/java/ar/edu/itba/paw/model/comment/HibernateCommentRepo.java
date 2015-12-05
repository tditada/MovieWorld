package ar.edu.itba.paw.model.comment;

import static ar.edu.itba.paw.util.persist.hibernate.HQLQueryHelpers.asHQLOrdering;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.model.AbstractHibernateRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.util.persist.Orderings;

@Repository
public class HibernateCommentRepo extends AbstractHibernateRepo implements
		CommentRepo {

	@Autowired
	public HibernateCommentRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Comment findById(int id) {
		return get(Comment.class, id);
	}

	@Override
	public void save(Comment comment) {
		super.save(comment);
	}

	@Override
	public void remove(User admin, Comment comment) {
		checkArgument(admin, notNull());
		checkArgument(admin.isAdmin());

		admin.removeComment(comment);
		super.remove(comment);
	}

	@Override
	public List<Comment> findReportedOrderedByReports(Orderings ordering) {
		checkArgument(ordering, notNull());
		return find("from Comment comment where size(reportingUsers) > 0 order by size(reportingUsers) "
				+ asHQLOrdering(ordering));
	}
}
