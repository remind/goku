package com.remind.rmvc;

import com.remind.rmvc.context.WebLifeContext;
import com.remind.rmvc.internal.ModelMap;

/**
 * 用于包装从controller中直接返回的结果
 * @author Administrator
 *
 */
public class ActionResult {

	private WebLifeContext webLifeContext;
	private ModelMap modelMap = new ModelMap();
	
	public WebLifeContext getWebLifeContext() {
		return webLifeContext;
	}
	public void setWebLifeContext(WebLifeContext webLifeContext) {
		this.webLifeContext = webLifeContext;
	}
	public ModelMap getModelMap() {
		return modelMap;
	}
	public void setModelMap(ModelMap modelMap) {
		this.modelMap = modelMap;
	}
}
