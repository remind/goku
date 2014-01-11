package com.remind.rmvc.internal.impl;

import com.remind.rmvc.ActionResult;
import com.remind.rmvc.context.WebLifeContext;
import com.remind.rmvc.internal.Router;

/**
 * 默认路由
 * @author remind
 *
 */
public class DefaultRouter implements Router{
	
	public ActionResult route(WebLifeContext webLifeContext) {
		//TODO:根据url找到controller，并返回结果
		return new ActionResult();
	}
}
