package com.remind.rmvc;

import com.remind.rmvc.internal.Model;
import com.remind.rmvc.internal.Router;
import com.remind.rmvc.internal.impl.DefaultModel;
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
	
	public static Model getModel() {
		return new DefaultModel();
	}
}
