package com.remind.rmvc.internal;

import com.remind.rmvc.GlobalFactory;
import com.remind.rmvc.context.ClientContext;
import com.remind.rmvc.model.DataModel;

/**
 * 用于包装从controller中直接返回的结果
 * @author remind
 *
 */
public class ActionResult {

	private ClientContext clientContext;
	private DataModel model = GlobalFactory.getClientModel();
	
	public ClientContext getClientContext() {
		return clientContext;
	}
	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}
	public DataModel getModelMap() {
		return model;
	}
	public void setModelMap(DataModel modelMap) {
		this.model = modelMap;
	}
}
