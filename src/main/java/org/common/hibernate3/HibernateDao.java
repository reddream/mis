package org.common.hibernate3;

import java.io.Serializable;
import java.util.List;

public interface HibernateDao<T, ID extends Serializable> {

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the t
	 * @see Session.get(Class,Serializable)
	 */
	public  T get(ID id);

	/**
	 * Gets the.
	 *
	 * @param id ID
	 * @param lock the lock
	 * @return the t
	 * @see Session.get(Class,Serializable,LockMode)
	 */
	public  T get(ID id, boolean lock);

	public  List<T> list();
	
	public T save(T obj) throws Exception;

}