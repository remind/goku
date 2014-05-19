package com.remind.rmvc.route;

import com.remind.rmvc.internal.ActionResult;

/**
 * 路由，根据url查找到指定的controller并执行
 * @author remind
 *
 */
public interface Router {

	/**
	 * 根据url匹配action,并返回结果
	 * @return
	 */
	public ActionResult route();
}
