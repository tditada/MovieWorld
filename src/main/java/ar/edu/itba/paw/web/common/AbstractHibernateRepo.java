package ar.edu.itba.paw.web.common;

import static ar.edu.itba.paw.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public abstract class AbstractHibernateRepo {
	private final SessionFactory sessionFactory;

	public AbstractHibernateRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type, Serializable id) {
		checkArgument(type, notNull());
		checkArgument(id, notNull());

		return (T) getSession().get(type, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Object... params) {
		checkArgument(hql, neitherNullNorEmpty());
		Session session = getSession();

		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		List<T> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findFirstN(int n, String hql, Object... params) {
		checkArgument(hql, neitherNullNorEmpty());
		Session session = getSession();

		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.setFirstResult(0);
		query.setMaxResults(n);

		List<T> list = query.list();
		return list;
	}

	protected org.hibernate.classic.Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Object o) {
		checkArgument(o, notNull());
		getSession().saveOrUpdate(o);
	}

	public void remove(Object o) {
		getSession().delete(o);
	}
}
