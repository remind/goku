package com.remind.rmvc.internal.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.remind.rmvc.internal.Model;

/**
 * 默认model,以Map形式存放所有参数
 * 1.在请求的时候将客户端的一些数据可以封装成ModelMap传给controller
 * 2.对controller传递给view的数据进行包装
 * @author remind
 *
 */
public class DefaultModel implements Model {

	private Map<String, Object> data = Maps.newConcurrentMap();
	
	@Override
	public Model add(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	@Override
	public Model addAll(Map<String, ?> map) {
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
