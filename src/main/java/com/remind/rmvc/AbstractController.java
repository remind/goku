package com.remind.rmvc;

import com.remind.rmvc.context.HttpContext;

/**
 * 顶级controller，其它controller都需要继承它
 * @author remind
 *
 */
public abstract class AbstractController {

	protected HttpContext actionContext;
	
	public void init() {
		
	}

	public HttpContext getActionContext() {
		return actionContext;
	}

	public void setActionContext(HttpContext actionContext) {
		this.actionContext = actionContext;
	}
	
}
