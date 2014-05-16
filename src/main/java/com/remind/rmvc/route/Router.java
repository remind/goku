package com.remind.rmvc.route;

import com.remind.rmvc.internal.ActionResult;

/**
 * 路由，根据url查找到指定的controller并执行
 * @author remind
 *
 */
public interface Router {

	public ActionResult route();
}
