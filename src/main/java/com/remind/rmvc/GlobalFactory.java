package com.remind.rmvc;

import com.remind.rmvc.internal.Router;
import com.remind.rmvc.internal.impl.DefaultRouter;

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
