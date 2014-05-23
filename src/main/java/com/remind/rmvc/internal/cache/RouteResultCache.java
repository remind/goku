package com.remind.rmvc.internal.cache;

import java.util.HashMap;
import java.util.Map;

import com.remind.rmvc.route.RouteResult;

/**
 * 对路由结果做缓存 
 * @author remind
 *
 */
public class RouteResultCache {

	private static Map<String, RouteResult> map = new HashMap<String, RouteResult>();
	
	public static void set(String uri, RouteResult routeResult) {
		map.put(uri, routeResult);
	}
	
	public static RouteResult get(String uri) {
		if (map.containsKey(uri)) {
			return map.get(uri);
		} else {
			return null;
		}
	}
	
	public static void clean() {
		map.clear();
	}
	
}
