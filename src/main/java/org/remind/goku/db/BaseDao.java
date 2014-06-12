package org.remind.goku.db;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.remind.goku.utils.EntityUtil;

import com.google.common.base.Joiner;

public abstract class BaseDao<Entity> {

	/**
	 * 保存set的参数
	 */
	private Map<String, Object> paramModel = new LinkedHashMap<String, Object>();
	
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
		paramModel.put(column, value);
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
	 * 根据id修改
	 * 要修改的列和值都从set中设置
	 * 在调用完成之后会清空
	 * @param id
	 * @return
	 */
	public int update(int id) {
		if (paramModel.size() < 1) {
			return -1;
		}
		Object[] params = new Object[paramModel.size() + 1];
		String sql = "update " + getTableName() + " set ";
		int i = 0;
		for (String column : paramModel.keySet()) {
			sql += column + "=?,";
			params[i] = paramModel.get(column);
		}
		sql = sql.substring(0, sql.length() - 1 ) + " where id = ?";
		params[paramModel.size()] = id;
		return DbUtilTemplate.update(sql, params);
	}
	
	/**
	 * 直接修改实体
	 * 以id为条件
	 * 如果没有id,就是修改全部
	 * @param entity
	 * @return
	 */
	public int update(Entity entity) {
		Map<String, Object> map = EntityUtil.getValidProperty(entity);
		if (map == null || map.size() < 1) {
			return -1;
		}
		String sql = "update " + getTableName() + " set ";
		Object[] params = new Object[map.size()];
		int i = 0;
		for (String column : map.keySet()) {
			if (column.equals("id")) {
				continue;
			}
			sql += column + "=?,";
			params[i] = map.get(column);
			i++;
		}
		sql = sql.substring(0, sql.length() - 1);
		if (map.keySet().contains("id"))  {
			sql += " where id = ?";
			params[map.size() - 1] = map.get("id");
		}
		return DbUtilTemplate.update(sql, params);
	}
	
	/**
	 * 查询 
	 * 在查询完成后会把set的参数清空
	 * @return
	 */
	public List<Entity> query() {
		String sql = "select * from " + getTableName();
		Object[] param = null;
		if (paramModel.size() > 0) {
			sql += " where ";
			param = new Object[paramModel.size()];
			int i = 0;
			for (String column : paramModel.keySet()) {
				sql += column + " = ? and";
				param[i] = paramModel.get(column);
				i ++;
			}
			paramModel.clear();
			sql = sql.substring(0, sql.length() - 3);
		}
		return DbUtilTemplate.find(getEntityClass(), sql, param);
	}
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return DbUtilTemplate.deleteByKey(getTableName(), id);
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

