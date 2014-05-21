package com.remind.rmvc.route;


/**
 * 路由，根据url查找到指定的action但不执行
 * @author remind
 *
 */
public interface Router {

	/**
	 * 根据url查找action
	 * @return
	 */
	public RouteResult route(RouteInfo routeInfo);
}
