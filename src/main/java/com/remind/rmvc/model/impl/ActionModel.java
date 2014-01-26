package com.remind.rmvc.model.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.remind.rmvc.model.DataModel;

/**
 * 每次请求的数据封装
 * 以及传递给view的数据
 * @author remind
 *
 */
public class ActionModel implements DataModel {

	private Map<String, Object> data = Maps.newConcurrentMap();
	
	@Override
	public DataModel add(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	@Override
	public DataModel addAll(Map<String, ?> map) {
		data.putAll(map);
		return this;
	}
	
	@Override
	public Object get(String key) {
		return data.get(key);
	}
	
	@Override
	public Map<String,Object> getAll() {
		return data;
	}
	
	@Override
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
}