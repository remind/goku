package org.remind.goku.internal;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 每次请求的数据封装
 * 以及传递给view的数据
 * @author remind
 *
 */
public class ModelMap {

	private Map<String, Object> data = Maps.newConcurrentMap();
	
	public ModelMap add(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	public ModelMap addAll(Map<String, ?> map) {
		data.putAll(map);
		return this;
	}
	
	public Object get(String key) {
		return data.get(key);
	}
	
	public Map<String,Object> getAll() {
		return data;
	}
	
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
}