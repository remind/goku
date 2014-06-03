package org.remind.goku.db.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql参数对象，与sql中?的顺序一致
 * @author remind
 *
 */
public class SqlParam {

	private Map<Integer, Class<?>> types = new HashMap<Integer, Class<?>>();
	private List<Object> values = new ArrayList<Object>();
	private int index = 0;
	
	/**
	 * sql参数对象，与sql中?的顺序一致
	 * @param value
	 * @return
	 */
	public SqlParam set(int value) {
		types.put(index, Integer.class);
		values.add(value);
		index ++;
		return this;
	}
	
	public SqlParam set(String value) {
		types.put(index, String.class);
		values.add(value);
		index ++;
		return this;
	}
	
	public SqlParam set(java.util.Date value) {
		types.put(index, java.sql.Date.class);
		values.add(new java.sql.Date(value.getTime()));
		index ++;
		return this;
	}
	
	public SqlParam set(Byte value) {
		types.put(index, Byte.class);
		values.add(value);
		index ++;
		return this;
	}
	
	public SqlParam setObject(Object value) {
		types.put(index, Object.class);
		values.add(value);
		index ++;
		return this;
	}
	
	public Map<Integer, Class<?>> getTypes() {
		return this.types;
	}
	
	public List<Object> getValues() {
		return this.values;
	}
	
	public int size() {
		return values.size();
	}
	
	public Object get(int i) {
		return values.get(i);
	}
	
	public Class<?> getType(int i) {
		return types.get(i);
	}
	/**
	 * sql参数对象，与sql中?的顺序一致，生成PreparedStatement
	 * @param ps
	 * @param sqlParam
	 * @return
	 */
	public static PreparedStatement build(PreparedStatement ps, SqlParam sqlParam) {
		for (int i = 0; i < sqlParam.size(); i++) {
			Object value = sqlParam.get(i);
			Class<?> type = sqlParam.getType(i);
			try {
				if (type == Integer.class) {
					ps.setInt(i, (int) value);
				} else if (type == String.class) {
					ps.setString(i, (String)value);
				} else if (type == java.sql.Date.class) {
					ps.setDate(i, (java.sql.Date)value);
				} else if (type == Byte.class) {
					ps.setByte(i, (Byte)value);
				} else { 
					ps.setObject(i, value);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ps;
	}
}
