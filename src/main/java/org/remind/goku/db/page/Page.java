package org.remind.goku.db.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * @author remind
 *
 */
public class Page<T> {
	
	private List<T> data = new  ArrayList<T>();
	
	private PageBar pageBar = new PageBar();
	
	/**
	 * 
	 * @param data	当页数据
	 * @param currentPageNumber		当前页数
	 * @param totalPageNumber		总页数
	 * @param totalRecordNumber		总记录数
	 */
	public Page(List<T> data, int currentPageNumber, int totalPageNumber, long totalRecordNumber) {
		this.data = data;
		pageBar.setCurrentPageNumber(currentPageNumber);
		pageBar.setTotalPageNumber(totalPageNumber);
		pageBar.setTotalRecordNumber(totalRecordNumber);
	}

	/**
	 * 当页数据
	 * @return
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 换页按钮
	 * @return
	 */
	public PageBar getPageBar() {
		return pageBar;
	}
}
