package org.remind.goku.db;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.remind.goku.utils.EntityUtil;

import com.google.common.base.Joiner;

public abstract class BaseDao<Entity> {

	private Map<String, Object> queryParam = new LinkedHashMap<String, Object>();
	
	/**
	 * 根据主键查询返回一条
	 * 主键名称只能为id
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Entity find(int id) {
		String sql = "select * from " + getTableName() + " where id = ?";
		return (Entity) DbUtilTemplate.findFirst(getEntityClass(), sql, new Object[] { id });
		
	}
	
	/**
	 * 设置查询参数
	 * @param column
	 * @param value
	 * @return
	 */
	public BaseDao<Entity> set(String column, Object value) {
		queryParam.put(column, value);
		return this;
	}
	
	/**
	 * 插入数据库
	 * @param entity
	 * @return
	 */
	public int insert(Entity entity) {
		Map<String, Object> map = EntityUtil.getValidProperty(entity);
		if (map.size() < 1) {
			return -1;
		}
		String sql = "insert into " + getTableName() + "(";
		sql += Joiner.on(",").join(map.keySet()) + ") values(";
		Object[] params = new Object[map.size()];
		for (int i = 0; i < params.length; i++) {
			sql += "?,";
		}
		params = map.values().toArray();
		sql = sql.substring(0, sql.length() - 1) + ")";
		return DbUtilTemplate.insertForKeys(sql, params);
	}
	
	/**
	 * 查询 
	 * 在查询完成后会把set的参数清空
	 * @return
	 */
	public List<Entity> query() {
		String sql = "select * from " + getTableName();
		Object[] param = null;
		if (queryParam.size() > 0) {
			sql += " where ";
			param = new Object[queryParam.size()];
			int i = 0;
			for (String column : queryParam.keySet()) {
				sql += column + " = ? and";
				param[i] = queryParam.get(column);
				i ++;
			}
			queryParam.clear();
			sql = sql.substring(0, sql.length() - 3);
		}
		return DbUtilTemplate.find(getEntityClass(), sql, param);
	}
	
	/**
	 * 表名
	 * @return
	 */
	private String getTableName() {
		return EntityUtil.getTableName(getEntityClass());
	}
	
	/**
	 * 实体类型
	 * @return
	 */
	public abstract Class<?> getEntityClass();
	
}

