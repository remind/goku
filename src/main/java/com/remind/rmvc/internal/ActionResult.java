package com.remind.rmvc.internal;

import com.remind.rmvc.GlobalFactory;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.model.DataModel;

/**
 * 用于包装从controller中直接返回的结果
 * @author remind
 *
 */
public class ActionResult {

	private HttpContext actionContext;
	private DataModel model = GlobalFactory.getActionDataModel();
	
	public HttpContext getActionContext() {
		return actionContext;
	}
	public void setClientContext(HttpContext actionContext) {
		this.actionContext = actionContext;
	}
	public DataModel getModelMap() {
		return model;
	}
	public void setModelMap(DataModel modelMap) {
		this.model = modelMap;
	}
}
