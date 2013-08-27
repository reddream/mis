package org.common.hibernate3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * HQL.
 *
 * @author reddream
 */
public class Finder {
	
	/**
	 * Instantiates a new finder.
	 */
	protected Finder() {
		hqlBuilder = new StringBuilder();
	}

	/**
	 * Instantiates a new finder.
	 *
	 * @param hql the hql
	 */
	protected Finder(String hql) {
		hqlBuilder = new StringBuilder(hql);
	}

	/**
	 * Creates the.
	 *
	 * @return the finder
	 */
	public static Finder create() {
		return new Finder();
	}

	/**
	 * Creates the.
	 *
	 * @param hql the hql
	 * @return the finder
	 */
	public static Finder create(String hql) {
		return new Finder(hql);
	}

	/**
	 * Append.
	 *
	 * @param hql the hql
	 * @return the finder
	 */
	public Finder append(String hql) {
		hqlBuilder.append(hql);
		return this;
	}

	/**
	 * Gets the orig hql.
	 *
	 * @return the orig hql
	 */
	public String getOrigHql() {
		return hqlBuilder.toString();
	}

	/**
	 * Gets the row count hql.
	 *
	 * @return the row count hql
	 */
	public String getRowCountHql() {
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(ORDER_BY);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		return wrapProjection(projectionHql) + rowCountHql;
	}

	/**
	 * Gets the first result.
	 *
	 * @return the first result
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * Sets the first result.
	 *
	 * @param firstResult the new first result
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * Gets the max results.
	 *
	 * @return the max results
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * Sets the max results.
	 *
	 * @param maxResults the new max results
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * Checks if is cacheable.
	 *
	 * @return true, if is cacheable
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * Sets the cacheable.
	 *
	 * @param cacheable the new cacheable
	 * @see Query#setCacheable(boolean)
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	/**
	 * Sets the param.
	 *
	 * @param param the param
	 * @param value the value
	 * @return the finder
	 * @see Query#setParameter(String, Object)
	 */
	public Finder setParam(String param, Object value) {
		return setParam(param, value, null);
	}

	/**
	 * Sets the param.
	 *
	 * @param param the param
	 * @param value the value
	 * @param type the type
	 * @return the finder
	 * @see Query#setParameter(String, Object, Type)
	 */
	public Finder setParam(String param, Object value, Type type) {
		getParams().add(param);
		getValues().add(value);
		getTypes().add(type);
		return this;
	}

	/**
	 * Sets the params.
	 *
	 * @param paramMap the param map
	 * @return the finder
	 * @see Query#setProperties(Map)
	 */
	public Finder setParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			setParam(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * Sets the param list.
	 *
	 * @param name the name
	 * @param vals the vals
	 * @param type the type
	 * @return the finder
	 * @see Query#setParameterList(String, Collection, Type))
	 */
	public Finder setParamList(String name, Collection<Object> vals, Type type) {
		getParamsList().add(name);
		getValuesList().add(vals);
		getTypesList().add(type);
		return this;
	}

	/**
	 * Sets the param list.
	 *
	 * @param name the name
	 * @param vals the vals
	 * @return the finder
	 * @see Query#setParameterList(String, Collection)
	 */
	public Finder setParamList(String name, Collection<Object> vals) {
		return setParamList(name, vals, null);
	}

	/**
	 * Sets the param list.
	 *
	 * @param name the name
	 * @param vals the vals
	 * @param type the type
	 * @return the finder
	 * @see Query#setParameterList(String, Object[], Type)
	 */
	public Finder setParamList(String name, Object[] vals, Type type) {
		getParamsArray().add(name);
		getValuesArray().add(vals);
		getTypesArray().add(type);
		return this;
	}

	/**
	 * Sets the param list.
	 *
	 * @param name the name
	 * @param vals the vals
	 * @return the finder
	 * @see Query#setParameterList(String, Object[])
	 */
	public Finder setParamList(String name, Object[] vals) {
		return setParamList(name, vals, null);
	}

	/**
	 * Sets the params to query.
	 *
	 * @param query the query
	 * @return the query
	 */
	public Query setParamsToQuery(Query query) {
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				if (types.get(i) == null) {
					query.setParameter(params.get(i), values.get(i));
				} else {
					query.setParameter(params.get(i), values.get(i), types
							.get(i));
				}
			}
		}
		if (paramsList != null) {
			for (int i = 0; i < paramsList.size(); i++) {
				if (typesList.get(i) == null) {
					query
							.setParameterList(paramsList.get(i), valuesList
									.get(i));
				} else {
					query.setParameterList(paramsList.get(i),
							valuesList.get(i), typesList.get(i));
				}
			}
		}
		if (paramsArray != null) {
			for (int i = 0; i < paramsArray.size(); i++) {
				if (typesArray.get(i) == null) {
					query.setParameterList(paramsArray.get(i), valuesArray
							.get(i));
				} else {
					query.setParameterList(paramsArray.get(i), valuesArray
							.get(i), typesArray.get(i));
				}
			}
		}
		return query;
	}

	/**
	 * Creates the query.
	 *
	 * @param s the s
	 * @return the query
	 */
	public Query createQuery(Session s) {
		Query query = setParamsToQuery(s.createQuery(getOrigHql()));
		if (getFirstResult() > 0) {
			query.setFirstResult(getFirstResult());
		}
		if (getMaxResults() > 0) {
			query.setMaxResults(getMaxResults());
		}
		if (isCacheable()) {
			query.setCacheable(true);
		}
		return query;
	}

	/**
	 * Wrap projection.
	 *
	 * @param projection the projection
	 * @return the string
	 */
	private String wrapProjection(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("select", "select count(") + ") ";
		}
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	private List<String> getParams() {
		if (params == null) {
			params = new ArrayList<String>();
		}
		return params;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	private List<Object> getValues() {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	/**
	 * Gets the types.
	 *
	 * @return the types
	 */
	private List<Type> getTypes() {
		if (types == null) {
			types = new ArrayList<Type>();
		}
		return types;
	}

	/**
	 * Gets the params list.
	 *
	 * @return the params list
	 */
	private List<String> getParamsList() {
		if (paramsList == null) {
			paramsList = new ArrayList<String>();
		}
		return paramsList;
	}

	/**
	 * Gets the values list.
	 *
	 * @return the values list
	 */
	private List<Collection<Object>> getValuesList() {
		if (valuesList == null) {
			valuesList = new ArrayList<Collection<Object>>();
		}
		return valuesList;
	}

	/**
	 * Gets the types list.
	 *
	 * @return the types list
	 */
	private List<Type> getTypesList() {
		if (typesList == null) {
			typesList = new ArrayList<Type>();
		}
		return typesList;
	}

	/**
	 * Gets the params array.
	 *
	 * @return the params array
	 */
	private List<String> getParamsArray() {
		if (paramsArray == null) {
			paramsArray = new ArrayList<String>();
		}
		return paramsArray;
	}

	/**
	 * Gets the values array.
	 *
	 * @return the values array
	 */
	private List<Object[]> getValuesArray() {
		if (valuesArray == null) {
			valuesArray = new ArrayList<Object[]>();
		}
		return valuesArray;
	}

	/**
	 * Gets the types array.
	 *
	 * @return the types array
	 */
	private List<Type> getTypesArray() {
		if (typesArray == null) {
			typesArray = new ArrayList<Type>();
		}
		return typesArray;
	}

	/** The hql builder. */
	private StringBuilder hqlBuilder;

	/** The params. */
	private List<String> params;
	
	/** The values. */
	private List<Object> values;
	
	/** The types. */
	private List<Type> types;

	/** The params list. */
	private List<String> paramsList;
	
	/** The values list. */
	private List<Collection<Object>> valuesList;
	
	/** The types list. */
	private List<Type> typesList;

	/** The params array. */
	private List<String> paramsArray;
	
	/** The values array. */
	private List<Object[]> valuesArray;
	
	/** The types array. */
	private List<Type> typesArray;

	/** The first result. */
	private int firstResult = 0;

	/** The max results. */
	private int maxResults = 0;

	/** The cacheable. */
	private boolean cacheable = false;

	/** The Constant ROW_COUNT. */
	public static final String ROW_COUNT = "select count(*) ";
	
	/** The Constant FROM. */
	public static final String FROM = "from";
	
	/** The Constant DISTINCT. */
	public static final String DISTINCT = "distinct";
	
	/** The Constant HQL_FETCH. */
	public static final String HQL_FETCH = "fetch";
	
	/** The Constant ORDER_BY. */
	public static final String ORDER_BY = "order";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Finder find = Finder
				.create("select distinct p FROM BookType join fetch p");
		System.out.println(find.getRowCountHql());
		System.out.println(find.getOrigHql());
	}

}