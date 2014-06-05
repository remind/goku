package org.remind.goku.db.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.remind.goku.db.DbUtilTemplate;

/**
 * 生成分页对象
 * @author remind
 *
 */
public class PageBuilder<T> {

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
	 * 查询参数
	 */
	private Object[] param = null;
	
	/**
	 * 当页结果集
	 */
	private List<T> data = new ArrayList<T>();
	
	private Page page;
	
	private Class<?> entityClass;
	
	/**
	 * 生成一个分页对象
	 * @param sql	查询sql,	不带分页信息
	 * @param perPageNumber		每页条数
	 * @param currentPageNumber		当前页
	 * @return
	 */
	public PageBuilder(String sql, int perPageNumber, int currentPageNumber) {
		this.sql = sql;
		this.perPageNumber = perPageNumber;
		this.currentPageNumber = currentPageNumber;
	}
	
	/**
	 * 生成一个分页对象
	 * @param sql	查询sql,	不带分页信息
	 * @param param		查询参数
	 * @param perPageNumber		每页条数
	 * @param currentPageNumber		当前页
	 * @return
	 */
	public PageBuilder(String sql, Object[] param, int perPageNumber, int currentPageNumber) {
		this.sql = sql;
		this.param = param;
		this.perPageNumber = perPageNumber;
		this.currentPageNumber = currentPageNumber;
	}
	
	/**
	 * 执行sql，生成page对象
	 * 这里sql的执行暂定只支持mysql
	 */
	public void execute() {
		String totlaSql = "select count(*) as num from (" + sql + ") t";
		String querySql = "select * from (" + sql + ") t limit ?,?";
		long totalRecordNumber = DbUtilTemplate.count(totlaSql, param);
		int totalPageNumber = (int)Math.ceil(Double.valueOf(totalRecordNumber) / Double.valueOf(perPageNumber));
		if (entityClass == null) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data = DbUtilTemplate.find(querySql, ArrayUtils.addAll(param, new Object[] {(currentPageNumber - 1) * perPageNumber, perPageNumber}));
			page = new Page<Map<String, Object>>(data, currentPageNumber, totalPageNumber, totalRecordNumber);
		} else {
			List<T> data = new ArrayList<T>();
			data = DbUtilTemplate.find(entityClass, querySql, ArrayUtils.addAll(param, new Object[] {(currentPageNumber - 1) * perPageNumber, perPageNumber}));
			page = new Page<T>(data, currentPageNumber, totalPageNumber, totalRecordNumber);
		}
	}
	
	public Page<Map<String, Object>> getMapPage() {
		return (Page<Map<String, Object>>)this.page;
	}
	
	/**
	 * 获得page对象
	 * @return
	 */
	public Page<T> getBeanPage() {
		return (Page<T>)this.page;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
}
