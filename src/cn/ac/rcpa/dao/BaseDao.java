package cn.ac.rcpa.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDao<T> {
	List<T> findAll();

	void deleteAll();

	T find(Serializable id);

	void delete(Serializable id);

	void delete(T t);

	void create(T t);

	void create(final Collection<T> ts);

	void update(T t);

	void refresh(T t);

	List<T> find(String key, Object param);
}
