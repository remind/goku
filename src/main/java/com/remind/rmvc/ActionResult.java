package com.remind.rmvc;

import com.remind.rmvc.context.WebLifeContext;
import com.remind.rmvc.internal.Model;

/**
 * 用于包装从controller中直接返回的结果
 * @author Administrator
 *
 */
public class ActionResult {

	private WebLifeContext webLifeContext;
	private Model model = GlobalFactory.getModel();
	
	public WebLifeContext getWebLifeContext() {
		return webLifeContext;
	}
	public void setWebLifeContext(WebLifeContext webLifeContext) {
		this.webLifeContext = webLifeContext;
	}
	public Model getModelMap() {
		return model;
	}
	public void setModelMap(Model modelMap) {
		this.model = modelMap;
	}
}
