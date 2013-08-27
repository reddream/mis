package org.models;

public class PageInfo extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4296821379568080071L;
	private int pageIndex;
	private int pageSize;
	private int totalItemCount;
	private int pageCount;

	public PageInfo(){
		
	}
	
	
	public PageInfo(int pageIndex, int pageSize, int totalItemCount,
			int pageCount) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalItemCount = totalItemCount;
		this.pageCount = pageCount;
	}
	public int getTotalItemCount() {
		return totalItemCount;
	}
	public void setTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}	
}
