package com.remind.rmvc.route.impl;

import com.remind.rmvc.context.ActionContext;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.route.Router;

/**
 * 默认路由
 * @author remind
 *
 */
public class DefaultRouter implements Router{
	
	public ActionResult route(ActionContext actionContext) {
		//TODO:根据url找到controller，并返回结果
		return new ActionResult();
	}
}
