package com.remind.rmvc.route.impl;

import com.remind.rmvc.Application;
import com.remind.rmvc.internal.ActionInfo;
import com.remind.rmvc.internal.PathMatcher;
import com.remind.rmvc.route.RouteInfo;
import com.remind.rmvc.route.RouteResult;
import com.remind.rmvc.route.Router;

/**
 * 默认路由
 * @author remind
 *
 */
public class DefaultRouter implements Router{
	
	private PathMatcher matcher;
	private RouteInfo routeInfo;
	
	@Override
	public RouteResult route(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
		RouteResult result = new RouteResult();
		result.setSuccess(false);
		String path = routeInfo.getPath();
		for(ActionInfo ai : Application.getAllAction()) {
			String clsPattern = ai.getClassPathPattern();
			String methodPattern = ai.getMethodPathPattern();
			String pattern = PathMatcher.combine(clsPattern, methodPattern);
			matcher = new PathMatcher(pattern, path);
			if (matcher.doMatch() && compareMethod(ai)) {
				result.setSuccess(true);
				result.setAction(ai);
				result.setVariable(matcher.getVariable());
				break;
			}
		}
		return result;
	}
	
	/**
	 * 比较请求方式
	 * @param ai
	 * @return
	 */
	private boolean compareMethod(ActionInfo ai) {
		return (ai.isGet() && routeInfo.isGet()) || (ai.isPost() && routeInfo.isPost());
	}
}
