package com.remind.rmvc.internal;

import com.remind.rmvc.GlobalFactory;
import com.remind.rmvc.context.ActionContext;
import com.remind.rmvc.model.DataModel;

/**
 * 用于包装从controller中直接返回的结果
 * @author remind
 *
 */
public class ActionResult {

	private ActionContext actionContext;
	private DataModel model = GlobalFactory.getActionModel();
	
	public ActionContext getActionContext() {
		return actionContext;
	}
	public void setClientContext(ActionContext actionContext) {
		this.actionContext = actionContext;
	}
	public DataModel getModelMap() {
		return model;
	}
	public void setModelMap(DataModel modelMap) {
		this.model = modelMap;
	}
}
