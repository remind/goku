package org.remind.goku.db.page;

/**
 * 换页按钮
 * @author remind
 *
 */
public class PageBar {
	
	/**
	 * 当前第几页
	 */
	private int currentPageNumber;
	
	/**
	 * 总页数
	 */
	private int totalPageNumber;
	
	/**
	 * 总记录数
	 */
	private long totalRecordNumber;
	
	/**
	 * 生成换页链接
	 */
	private PageUrl pageUrl;

	/**
	 * 当前页数
	 * @return
	 */
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	/**
	 * 当前页数
	 * @param currentPageNumber
	 */
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	/**
	 * 总页数
	 * @return
	 */
	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	/**
	 * 总页数
	 * @param totalPageNumber
	 */
	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	/**
	 * 获取记录总条数
	 * @return
	 */
	public long getTotalRecordNumber() {
		return totalRecordNumber;
	}

	/**
	 * 记录总条数
	 * @param totalRecordNumber
	 */
	public void setTotalRecordNumber(long totalRecordNumber) {
		this.totalRecordNumber = totalRecordNumber;
	}

	/**
	 * 根据页数，生成链接
	 * @param pageNumber
	 * @return
	 */
	public String getPageUrl(int pageNumber) {
		return pageUrl.get(pageNumber);
	}

	/**
	 * 设置换页按钮的链接
	 * @param pageUrl
	 */
	public void setPageUrl(PageUrl pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * 上一页
	 * @return
	 */
	public int getPrePageNumber() {
		return this.currentPageNumber - 1;
	}

	/**
	 * 下一页
	 * @return
	 */
	public int getNextPageNumber() {
		return this.currentPageNumber + 1;
	}
}
