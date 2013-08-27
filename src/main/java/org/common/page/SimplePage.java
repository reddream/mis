package org.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class SimplePage.
 *
 * @author reddream
 */
public class SimplePage implements Paginable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant DEF_COUNT. */
	public static final int DEF_COUNT = 20;

	/**
	 * Cpn.
	 *
	 * @param pageNo the page no
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	/**
	 * Instantiates a new simple page.
	 */
	public SimplePage() {
	}

	/**
	 * Instantiates a new simple page.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param totalCount the total count
	 */
	public SimplePage(int pageNo, int pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

	/**
	 * Adjust page no.
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getPageNo()
	 */
	public int getPageNo() {
		return pageNo;
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getPageSize()
	 */
	public int getPageSize() {
		return pageSize;
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getTotalCount()
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getTotalPage()
	 */
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#isFirstPage()
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#isLastPage()
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getNextPage()
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getPrePage()
	 */
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/** The total count. */
	protected int totalCount = 0;
	
	/** The page size. */
	protected int pageSize = 20;
	
	/** The page no. */
	protected int pageNo = 1;
	
	/** The pp size. */
	protected int ppSize = 4;

	/**
	 * if totalCount<0 then totalCount=0.
	 *
	 * @param totalCount the new total count
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * Gets the pp size.
	 *
	 * @return the pp size
	 */
	public int getPpSize() {
		return ppSize;
	}

	/**
	 * Sets the pp size.
	 *
	 * @param ppSize the new pp size
	 */
	public void setPpSize(int ppSize) {
		this.ppSize = ppSize;
	}

	/**
	 * if pageSize< 1 then pageSize=DEF_COUNT.
	 *
	 * @param pageSize the new page size
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * if pageNo < 1 then pageNo=1.
	 *
	 * @param pageNo the new page no
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}

	/** The pp list. */
	protected List<Integer> ppList;

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#getCurrentPageList()
	 */
	public List<Integer> getCurrentPageList() {
		ppList = new ArrayList<Integer>();
		int startPos = (this.getPageNo() / ppSize) * ppSize;
		if (startPos == 0)
			startPos++;
		if(startPos>1)
			startPos--;
		int totalPage = this.getTotalPage();
		for (int i = 1, j = startPos; i <= ppSize+2 && j <= totalPage; i++, j++) {
			ppList.add(j);
		}
		return ppList;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		SimplePage sp = new SimplePage();
		sp.setPageSize(5);
		sp.setTotalCount(1000);
		sp.setPageNo(1);
		sp.setPpSize(4);
		printpplist(sp);
		System.out.println(sp.isLastPPage()+","+sp.isFirstPPage());
		sp.setPageNo(8);
		printpplist(sp);
		sp.setPageNo(7);
		printpplist(sp);
		sp.setPageNo(199);
		printpplist(sp);
		System.out.println(sp.isLastPPage()+","+sp.isFirstPPage());
	}

	/**
	 * Printpplist.
	 *
	 * @param sp the sp
	 */
	private static void printpplist(SimplePage sp) {
		List<Integer> ppList = sp.getCurrentPageList();
		System.out.println("pplist below:");
		for (Integer i : ppList) {
			System.out.println("i:" + i);
		}
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#isFirstPPage()
	 */
	public boolean isFirstPPage() {
		for (Integer i : ppList) {
			if (i==1)
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see vehicle.common.page.Paginable#isLastPPage()
	 */
	public boolean isLastPPage() {
		int totalPage = this.getTotalPage();
		for (Integer i : ppList) {
			if (i== totalPage)
				return true;
		}
		return false;
	}

}
