package com.remind.rmvc.internal;

import com.remind.rmvc.ActionResult;
import com.remind.rmvc.context.WebLifeContext;

/**
 * 路由，根据url查找到指定的controller并执行
 * @author remind
 *
 */
public interface Router {

	public ActionResult route(WebLifeContext webLifeContext);
}
