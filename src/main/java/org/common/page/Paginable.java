package org.common.page;

import java.util.List;

/**
 * The Interface Paginable.
 *
 * @author reddream
 */
public interface Paginable {
	
	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	public int getTotalCount();

	/**
	 * Gets the total page.
	 *
	 * @return the total page
	 */
	public int getTotalPage();

	/**
	 * Gets the page size.
	 *
	 * @return the page size
	 */
	public int getPageSize();

	/**
	 * Gets the page no.
	 *
	 * @return the page no
	 */
	public int getPageNo();

	/**
	 * Checks if is first page.
	 *
	 * @return true, if is first page
	 */
	public boolean isFirstPage();

	/**
	 * Checks if is last page.
	 *
	 * @return true, if is last page
	 */
	public boolean isLastPage();

	/**
	 * Gets the next page.
	 *
	 * @return the next page
	 */
	public int getNextPage();

	/**
	 * Gets the pre page.
	 *
	 * @return the pre page
	 */
	public int getPrePage();
	
	/**
	 * Gets the current page list.
	 *
	 * @return the current page list
	 */
	public List<Integer> getCurrentPageList();
	
	/**
	 * Checks if is first p page.
	 *
	 * @return true, if is first p page
	 */
	public boolean isFirstPPage();
	
	/**
	 * Checks if is last p page.
	 *
	 * @return true, if is last p page
	 */
	public boolean isLastPPage();
		
}
