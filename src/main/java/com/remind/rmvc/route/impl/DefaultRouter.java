package com.remind.rmvc.route.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.remind.rmvc.Application;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.ActionInfo;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.internal.PathMatcher;
import com.remind.rmvc.route.Router;

/**
 * 默认路由
 * @author remind
 *
 */
public class DefaultRouter implements Router{
	
	private PathMatcher matcher;
	private HttpContext httpContext = HttpContext.getCurrent();
	
	public ActionResult route() {
		ActionResult actionResult = null;
		String path = httpContext.getMatchPath();
		for(ActionInfo ai : Application.getAllAction()) {
			String clsPattern = ai.getClassPathPattern();
			String methodPattern = ai.getMethodPathPattern();
			String pattern = PathMatcher.combine(clsPattern, methodPattern);
			matcher = new PathMatcher(pattern, path);
			if (matcher.doMatch() && compareMethod(ai)) {
				actionResult = new ActionResult();
				Map<String, String> map = new HashMap<>();
				map = matcher.getVariable();
				Method method = ai.getMethod();
				try {
					if (ai.getParam().size() > 0) {
						Object[] args = new Object[ai.getParam().size()];
						int i = 0;
						for(String key : ai.getParam().keySet()) {
							args[i] = map.containsKey(key) ? map.get(key) : null;
						}
						actionResult = (ActionResult)method.invoke(ai.getControllerClass().getClass().newInstance(), args);
					} else {
						actionResult = (ActionResult)method.invoke(ai.getControllerClass().getClass().newInstance());
					}
					return actionResult;
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | InstantiationException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private boolean compareMethod(ActionInfo ai) {
		if (ai.isGet()) {
			return httpContext.getRequest().getMethod().equalsIgnoreCase("GET");
		}
		if (ai.isPost()) {
			return httpContext.getRequest().getMethod().equalsIgnoreCase("POST");
		}
		return false;
	}
}
