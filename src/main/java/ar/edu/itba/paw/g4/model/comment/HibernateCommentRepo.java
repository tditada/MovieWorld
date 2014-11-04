package ar.edu.itba.paw.g4.model.comment;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.g4.model.AbstractHibernateRepo;

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
		// TODO: Revisar que el comment no exista ya en la BD
		super.save(comment);
	}

	@Override
	public void remove(Comment comment) {
		super.remove(comment);
	}
}
