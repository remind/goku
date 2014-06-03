package org.remind.goku.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.remind.goku.db.sql.SqlParam;

/**
 * 数据表对象
 * @author remind
 *
 */
public class Table {

	private String name;
	private Map<String, Object> recordMap = new HashMap<String, Object>();
	
	private Table(String name, Map<String, Object> recordMap) {
		this.name = name;
		this.recordMap = recordMap;
	}
	
	public Table(String name) {
		this.name = name;
	}
	
	public Table set(String columnName, Object value) {
		recordMap.put(columnName, value);
		return this;
	}
	/**
	 * 插入，返回只提示是否成功
	 * @return
	 */
	public boolean insert() {
		SqlParam sqlParam = new SqlParam();
		StringBuilder sbSql = new StringBuilder();
		StringBuilder sbParam = new StringBuilder();
		sbSql.append("insert into ");
		sbSql.append(this.name);
		sbSql.append("(");
		StringBuilder tempSql = new StringBuilder();
		StringBuilder tempParam = new StringBuilder();
		for(String columnName : recordMap.keySet()) {
			tempSql.append(columnName + ",");
			tempParam.append("?,");
			sqlParam.setObject(recordMap.get(columnName));
		}
		sbSql.append(tempSql.substring(0, sbSql.length() - 1));
		sbParam.append(tempParam.substring(0, sbSql.length() - 1));
		sbSql.append(") values(");
		sbSql.append(sbParam);
		sbSql.append(")");
		return Db.insert(sbSql.toString(), sqlParam);
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Table find(int id) {
		SqlParam sqlParam = new SqlParam();
		StringBuilder sbSql = new StringBuilder("select * from ");
		sbSql.append(this.name);
		sbSql.append(" where id = ?");
		sqlParam.set(id);
		this.recordMap = Db.findSingle(sbSql.toString(), sqlParam);
		if (this.recordMap.size() < 1) {
			return null;
		}
		return this;
	}
	
	/**
	 * 指定列名查询单条
	 * @param columnName	列名
	 * @param value		条件值
	 * @return
	 */
	public Table find(String columnName, Object value) {
		SqlParam sqlParam = new SqlParam();
		StringBuilder sbSql = new StringBuilder("select * from ");
		sbSql.append(this.name);
		sbSql.append(" where ");
		sbSql.append(columnName).append(" = ?");
		sqlParam.setObject(value);
		this.recordMap = Db.findSingle(sbSql.toString(), sqlParam);
		return this;
	}
	
	/**
	 * 返回某个字段的值
	 * @param columnName
	 * @return
	 */
	public Object get(String columnName) {
		return this.recordMap.get(columnName);
	}
	
	public String getStr(String columnName) {
		return (String)this.recordMap.get(columnName);
	}
	
	public int getInt(String columnName) {
		return (Integer)this.recordMap.get(columnName);
	}
	
	public java.util.Date getDate(String columnName) {
		return (java.util.Date)this.recordMap.get(columnName);
	}

	/**
	 * 返回map形式的所有数据
	 * @return
	 */
	public Map<String, Object> getRecordMap() {
		return recordMap;
	}

	/**
	 * 根据条件查询返回多条map格式的
	 * @param where
	 * @return
	 */
	public List<Map<String, Object>> where(Map<String, Object> where) {
		SqlParam sqlParam = new SqlParam();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from ");
		sbSql.append(this.name);
		if (where != null && where.size() > 0) {
			StringBuilder temp = new StringBuilder();
			sbSql.append("where ");
			for(String columnName : recordMap.keySet()) {
				temp.append(columnName + "=? and");
				sqlParam.setObject(recordMap.get(columnName));
			}
			sbSql.append(temp.substring(0, sbSql.length() - 1));
			sbSql.substring(0, sbSql.length() - 3);
		}
		return Db.find(sbSql.toString(), sqlParam);
	}
	
	/**
	 * 将map格式的list包装成table
	 * @param list
	 * @return
	 */
	public List<Table> wapperAsTable(List<Map<String, Object>> list) {
		List<Table> data = new ArrayList<Table>();
		for (int i = 0; i < list.size(); i++) {
			data.add(new Table(this.name, list.get(i)));
		}
		return data;
	}
	/**
	 * 根据ID更新
	 * @param id
	 * @return
	 */
	public boolean update(int id) {
		SqlParam sqlParam = new SqlParam();
		StringBuilder sbSql = new StringBuilder("update ");
		sbSql.append(this.name);
		sbSql.append(" set ");
		StringBuilder temp = new StringBuilder();
		for(String columnName : recordMap.keySet()) {
			temp.append(columnName + "=?,");
			sqlParam.setObject(recordMap.get(columnName));
		}
		sbSql.append(temp.substring(0, sbSql.length() - 1));
		sbSql.append(" where id = ?");
		sqlParam.set(id);
		this.recordMap = Db.findSingle(sbSql.toString(), sqlParam);
		return true;
	}
}
