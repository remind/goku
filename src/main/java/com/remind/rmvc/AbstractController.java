package com.remind.rmvc;

import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.internal.ModelMap;

/**
 * 顶级controller，其它controller都需要继承它
 * @author remind
 *
 */
public abstract class AbstractController {

	protected HttpContext actionContext;
	
	protected ActionResult actionResult = new ActionResult();
	
	public void init() {
		
	}
	
	protected ModelMap getModel() {
		return actionResult.getModel();
	}
	
	/**
	 * 直接输出到页面
	 * @param content
	 * @return
	 */
	protected ActionResult write(String content) {
		actionResult.setView(ViewFactory.getTextView(content));
		return actionResult;
	}
	
}
