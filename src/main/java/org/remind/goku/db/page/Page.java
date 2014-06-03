package org.remind.goku.db.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * @author remind
 *
 */
public class Page {
	
	private List<Map<String, Object>> data = new  ArrayList<Map<String,Object>>();
	
	private PageBar pageBar = new PageBar();
	
	/**
	 * 
	 * @param data	当页数据
	 * @param currentPageNumber		当前页数
	 * @param totalPageNumber		总页数
	 * @param totalRecordNumber		总记录数
	 */
	public Page(List<Map<String, Object>> data, int currentPageNumber, int totalPageNumber, int totalRecordNumber) {
		this.data = data;
		pageBar.setCurrentPageNumber(currentPageNumber);
		pageBar.setTotalPageNumber(totalPageNumber);
		pageBar.setTotalRecordNumber(totalRecordNumber);
	}

	/**
	 * 当页数据
	 * @return
	 */
	public List<Map<String, Object>> getData() {
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
