package org.common.page;

import java.util.List;

/**
 * 
 * 
 * @author reddream
 * 
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
		Paginable {

	public Pagination() {
	}

	/**
	 * 
	 * 
	 * @param pageNo
	 *            
	 * @param pageSize
	 *          
	 * @param totalCount
	 *          
	 */
	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 
	 * 
	 * @param pageNo
	 *            
	 * @param pageSize
	 *          
	 * @param totalCount
	 *         
	 * @param list
	 *           
	 */
	public Pagination(int pageNo, int pageSize, int totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	/**
	 *
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 *
	 */
	private List<?> list;

	/**
	 * 
	 * 
	 * @return
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}
	
	
	
	
	
	
}
