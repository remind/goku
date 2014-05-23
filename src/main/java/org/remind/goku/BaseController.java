package org.remind.goku;

import java.io.IOException;

import javax.servlet.http.Cookie;

import org.remind.goku.context.HttpContext;
import org.remind.goku.context.ThreadLocalContext;
import org.remind.goku.internal.ModelMap;
import org.remind.goku.internal.action.ActionResult;
import org.remind.goku.view.View;
import org.remind.goku.view.impl.JsonView;
import org.remind.goku.view.impl.TextView;
import org.remind.goku.view.impl.VelocityView;

/**
 * 基本controller，其它所有controller都需要继承它
 * @author remind
 *
 */
public abstract class BaseController {
	
	protected HttpContext httpContext = HttpContext.getCurrent();
	
	/**
	 * 由于controller是单列的
	 * 所以要获取ActionResult都从这个方法获得
	 * @return
	 */
	private ActionResult getActionResult() {
		return (ActionResult) ThreadLocalContext.get().getSingleThreadLocalVar(ActionResult.class);
	}
	
	/**
	 * 获得model
	 * @return
	 */
	protected ModelMap getModel() {
		return getActionResult().getModel();
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
		getActionResult().setView(new TextView(content));
		return getActionResult();
	}
	
	/**
	 * 输出json串到页面
	 * @param o	object对象，会采用fastjson转换成json串
	 * @return
	 */
	protected ActionResult writeJson(Object o) {
		getActionResult().setView(new JsonView(o));
		return getActionResult();
	}
	
	/**
	 * velocity view
	 * @param path
	 * @return
	 */
	protected ActionResult view(String path) {
		VelocityView view = new VelocityView();
		view.setPath(path);
		getActionResult().setView(view);
		return getActionResult();
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
