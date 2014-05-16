package com.remind.rmvc;

import javax.servlet.http.Cookie;

import com.alibaba.fastjson.JSON;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.internal.ModelMap;
import com.remind.rmvc.view.impl.TextView;

/**
 * 顶级controller，其它controller都需要继承它
 * @author remind
 *
 */
public abstract class AbstractController {

	private ActionResult actionResult = new ActionResult();
	
	protected HttpContext httpContext = HttpContext.getCurrent();
	
	public void init() {
		
	}
	
	protected ModelMap getModel() {
		return actionResult.getModel();
	}
	
	/**
	 * 添加cookie
	 * @param name
	 * @param value
	 */
	public void addCookie(String name, String value) {
		httpContext.getResponse().addCookie(new Cookie(name, value));
	}
	
	/**
	 * 直接输出文本到页面
	 * @param content
	 * @return
	 */
	protected ActionResult write(String content) {
		TextView view = (TextView)ViewFactory.getTextView(content);
		view.setHeader("content-type", "text/plan; charset=" + httpContext.getRequest().getCharacterEncoding());
		actionResult.setView(view);
		return actionResult;
	}
	
	/**
	 * 输出json串到页面
	 * @param content
	 * @return
	 */
	protected ActionResult writeJson(String content) {
		TextView view = (TextView)ViewFactory.getTextView(content);
		view.setHeader("content-type", "text/json; charset=" + httpContext.getRequest().getCharacterEncoding());
		actionResult.setView(view);
		return actionResult;
	}
	
	/**
	 * 输出json串到页面
	 * @param o	object对象，会采用fastjson转换成json串
	 * @return
	 */
	protected ActionResult writeJson(Object o) {
		return writeJson(JSON.toJSONString(o));
	}
	
}
