package com.remind.rmvc;

import com.remind.rmvc.route.Router;
import com.remind.rmvc.route.impl.DefaultRouter;

/**
 * 全局工厂类
 * @author remind
 *
 */
public class GlobalFactory {

	/**
	 * 返回路由
	 * @return
	 */
	public static Router getRoute() {
		return new DefaultRouter();
	}
	
}
