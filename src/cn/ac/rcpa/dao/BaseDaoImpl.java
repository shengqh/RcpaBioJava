package cn.ac.rcpa.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.ac.rcpa.utils.RcpaObjectUtils;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	protected Class classT;

	public BaseDaoImpl(Class classT) {
		this.classT = classT;
	}

	public List<T> findAll() {
		final String hql = "From " + classT.getName();
		List result = getHibernateTemplate().find(hql);
		return RcpaObjectUtils.asList(result);
	}

	@SuppressWarnings("unchecked")
	public T find(Serializable id) {
		return (T) getHibernateTemplate().get(classT, id);
	}

	public void delete(T t) {
		if (null != t) {
			getHibernateTemplate().delete(t);
		}
	}

	public void delete(Serializable id) {
		delete(find(id));
	}

	public void create(T t) {
		getHibernateTemplate().save(t);
	}

	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	public void deleteAll() {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				arg0.createQuery("delete from " + classT.getName()).executeUpdate();
				return null;
			}
		});
	}

	public List<T> find(String key, Object param) {
		String hql = "From " + classT.getName() + " where " + key + "=?";
		return RcpaObjectUtils.asList(getHibernateTemplate().find(hql, param));
	}

	public void refresh(T t) {
		getHibernateTemplate().refresh(t);
	}

	public void create(final Collection<T> ts) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				for (T t : ts) {
					arg0.save(t);
				}
				arg0.flush();
				return null;
			}
		});
	}
}
