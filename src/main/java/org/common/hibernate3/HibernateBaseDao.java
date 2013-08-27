package org.common.hibernate3;

import static org.hibernate.EntityMode.POJO;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.MyBeanUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;


/**
 * hibernate DAO.
 *
 * @param <T> entity class
 * @param <ID> entity id
 * @author reddream
 */
public abstract class HibernateBaseDao<T, ID extends Serializable> extends
		HibernateSimpleDao  {
	
	/* (non-Javadoc)
	 * @see org.common.hibernate3.HibernateDao#get(ID)
	 */
	public T get(ID id) {
		return get(id, false);
	}

	/* (non-Javadoc)
	 * @see org.common.hibernate3.HibernateDao#get(ID, boolean)
	 */
	@SuppressWarnings("unchecked")
	public T get(ID id, boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().get(getEntityClass(), id,
					LockMode.UPGRADE);
		} else {
			entity = (T) getSession().get(getEntityClass(), id);
		}
		return entity;
	}

	/**
	 * Find by property.
	 *
	 * @param property the property
	 * @param value the value
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String property, Object value) {
		Assert.hasText(property);
		return createCriteria(Restrictions.eq(property, value)).list();
	}

	/**
	 * Find unique by property.
	 *
	 * @param property the property
	 * @param value the value
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return (T) createCriteria(Restrictions.eq(property, value))
				.uniqueResult();
	}

	/**
	 * Count by property.
	 *
	 * @param property the property
	 * @param value the value
	 * @return the int
	 */
	public int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value))
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	/**
	 * Find by criteria.
	 *
	 * @param criterion the criterion
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List findByCriteria(Criterion... criterion) {
		return createCriteria(criterion).list();
	}

	/**
	 * Update by updater.
	 *
	 * @param updater the updater
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public T updateByUpdater(Updater<T> updater) {
		ClassMetadata cm = sessionFactory.getClassMetadata(getEntityClass());
		T bean = updater.getBean();
		T po = (T) getSession().get(getEntityClass(),
				cm.getIdentifier(bean, POJO));
		updaterCopyToPersistentObject(updater, po, cm);
		return po;
	}

	/**
	 * Updater copy to persistent object.
	 *
	 * @param updater the updater
	 * @param po the po
	 * @param cm the cm
	 */
	private void updaterCopyToPersistentObject(Updater<T> updater, T po,
			ClassMetadata cm) {
		String[] propNames = cm.getPropertyNames();
		String identifierName = cm.getIdentifierPropertyName();
		T bean = updater.getBean();
		Object value;
		for (String propName : propNames) {
			if (propName.equals(identifierName)) {
				continue;
			}
			try {
				value = MyBeanUtils.getSimpleProperty(bean, propName);
				if (!updater.isUpdate(propName, value)) {
					continue;
				}
				cm.setPropertyValue(po, propName, value, POJO);
			} catch (Exception e) {
				throw new RuntimeException(
						"copy property to persistent object failed: '"
								+ propName + "'", e);
			}
		}
	}

	/**
	 * Creates the criteria.
	 *
	 * @param criterions the criterions
	 * @return the criteria
	 */
	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * Gets the entity class.
	 *
	 * @return the entity class
	 */
	abstract protected Class<T> getEntityClass();
	
//	protected Integer getSequence(){
//		String name=getEntityClass().getSimpleName();
//		String hql="from Sequence where name=:name";
//		Finder f= Finder.create(hql);
//		f.setParam("name", name);
//		List<Sequence> sequences = find(f);
//		if(sequences.size()==0){
//			Sequence s = new Sequence();
//			s.setCurrentId(100);
//			s.setStep(1);
//			s.setName(name);
//			getSession().save(s);
//			return s.getCurrentId();
//		}else{
//			Sequence s = sequences.get(0);
//			s.setCurrentId(s.getCurrentId()+s.getStep());
//			getSession().save(s);
//			return s.getCurrentId();
//		}
//	}
	
	protected String convertIdsToString(Integer[] ids) {
		String idsStr=StringUtils.EMPTY;
		for(Integer id:ids){
			idsStr += id.toString()+",";
		}
		if(StringUtils.isNotBlank(idsStr)){
			idsStr = idsStr.substring(0,idsStr.length()-1);
		}
		return idsStr;
	}
//	
	
}
