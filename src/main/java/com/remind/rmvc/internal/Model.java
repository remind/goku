package com.remind.rmvc.internal;

import java.util.Map;

/**
 * 数据封装模型
 * @author remind
 *
 */
public interface Model {

	/**
	 * 添加一对值
	 * @param key
	 * @param value
	 * @return
	 */
	public Model add(String key, Object value);
	
	/**
	 * 批量增加
	 * @param map
	 * @return
	 */
	public Model addAll(Map<String, ?> map);
	
	/**
	 * 根据key返回value
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	
	/**
	 * 返回所有，直接返回的一个Map
	 * @return
	 */
	public Map<String,Object> getAll();
	
	/**
	 * 判断某个属性是否已经有了
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);
}
