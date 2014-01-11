package com.remind.rmvc.internal;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 1.在请求的时候将客户端的一些数据可以封装成ModelMap传给controller
 * 2.对controller传递给view的数据进行包装
 * @author remind
 *
 */
public class ModelMap {

	private Map<String, Object> data = Maps.newConcurrentMap();
	
	/**
	 * 添加一对值
	 * @param key
	 * @param value
	 * @return
	 */
	public ModelMap add(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	/**
	 * 批量增加
	 * @param map
	 * @return
	 */
	public ModelMap addAll(Map<String, ?> map) {
		data.putAll(map);
		return this;
	}
	/**
	 * 根据key返回value
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return data.get(key);
	}
	
	/**
	 * 返回所有，直接返回的一个Map
	 * @return
	 */
	public Map<String,Object> getAll() {
		return data;
	}
	
	/**
	 * 判断某个属性是否已经有了
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
}
