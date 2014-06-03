package org.remind.goku.db.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.remind.goku.db.Db;

/**
 * 生成分页对象
 * @author remind
 *
 */
public class PageBuilder {

	/**
	 * 查询sql
	 */
	private String sql;
	
	/**
	 * 每页条数
	 */
	private int perPageNumber;
	
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
	private int totalRecordNumber;
	
	/**
	 * 当页结果集
	 */
	private List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
	
	private PageBuilder(String sql, int perPageNumber, int currentPageNumber) {
		this.sql = sql;
		this.perPageNumber = perPageNumber;
		this.currentPageNumber = currentPageNumber;
	}
	
	/**
	 * 生成一个分页对象
	 * @param sql	查询sql,	不带分页信息
	 * @param perPageNumber		每页条数
	 * @param currentPageNumber		当前页
	 * @return
	 */
	public static Page build(String sql, int perPageNumber, int currentPageNumber) {
		PageBuilder builder = new PageBuilder(sql, perPageNumber, currentPageNumber);
		builder.execute();
		Page page = new Page(builder.getData(), builder.currentPageNumber,builder.totalPageNumber,builder.totalRecordNumber);
		return page;
	}
	
	/**
	 * 执行sql，生成page对象
	 * 这里sql的执行暂定只支持mysql
	 */
	public void execute() {
		String totlaSql = "select count(*) as num from (" + sql + ") t";
		String querySql = "select * from (" + sql + ") t limit " + (currentPageNumber - 1) * perPageNumber + "," + perPageNumber;
		Map<String, Object> map = Db.findSingle(totlaSql);
		this.totalRecordNumber = Integer.valueOf(map.get("num").toString());
		this.totalPageNumber = (int)Math.ceil(Double.valueOf(totalRecordNumber) / Double.valueOf(perPageNumber));
		data = Db.find(querySql);
	}
	
	public List<Map<String, Object>> getData() {
		return data;
	}
	
	public int getTotalPage() {
		return this.totalPageNumber;
	}
}
