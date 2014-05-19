package com.remind.rmvc;

import java.io.IOException;

import javax.servlet.http.Cookie;

import com.alibaba.fastjson.JSON;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.context.ThreadLocalContext;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.internal.ModelMap;
import com.remind.rmvc.view.View;
import com.remind.rmvc.view.impl.VelocityView;

/**
 * 基本controller，其它所有controller都需要继承它
 * @author remind
 *
 */
public abstract class BaseController {

	private ActionResult actionResult = (ActionResult) ThreadLocalContext.get().getThreadLocalVar(ActionResult.class);
	
	protected HttpContext httpContext = HttpContext.getCurrent();
	
	/**
	 * 获得model
	 * @return
	 */
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
		View view = GlobalConfig.getView("text");
		getModel().add("content", content);
		actionResult.addHeader("content-type", "text/plan; charset=" + GlobalConfig.ENCODING);
		actionResult.setView(view);
		return actionResult;
	}
	
	/**
	 * 输出json串到页面
	 * @param content
	 * @return
	 */
	protected ActionResult writeJson(String content) {
		View view = GlobalConfig.getView("text");
		getModel().add("content", content);
		actionResult.addHeader("content-type", "text/json; charset=" + GlobalConfig.ENCODING);
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
	
	/**
	 * velocity view
	 * @param path
	 * @return
	 */
	protected ActionResult view(String path) {
		VelocityView view = (VelocityView)GlobalConfig.getView("velocity");
		view.setPath(path);
		actionResult.setView(view);
		return actionResult;
	}
	
	/**
	 * 重定向
	 * @param location
	 * @return
	 */
	protected ActionResult redirect(String location) {
		ActionResult ar = new ActionResult();
		ar.getModel().add("location", location);
		ar.setView(new View() {
			@Override
			public void render(ActionResult actionResult) {
				try {
					HttpContext.getCurrent().getResponse().sendRedirect(actionResult.getModel().get("location").toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return ar;
	}
	
}
