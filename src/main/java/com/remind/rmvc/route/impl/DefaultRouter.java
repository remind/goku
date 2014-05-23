package com.remind.rmvc.route.impl;

import com.remind.rmvc.Application;
import com.remind.rmvc.internal.PathMatcher;
import com.remind.rmvc.internal.action.ActionInfo;
import com.remind.rmvc.internal.action.ControllerInfo;
import com.remind.rmvc.route.RouteInfo;
import com.remind.rmvc.route.RouteResult;
import com.remind.rmvc.route.Router;

/**
 * 默认路由
 * 
 * @author remind
 * 
 */
public class DefaultRouter implements Router {

	private PathMatcher matcher;
	private RouteInfo routeInfo;

	@Override
	public RouteResult route(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
		RouteResult result = new RouteResult();
		result.setSuccess(false);
		String path = routeInfo.getPath();
		for (ActionInfo ai : Application.getAllAction()) {
			ControllerInfo controller = ai.getController();
			String pattern = PathMatcher.combine(controller.getUriPattern(),
					ai.getUriPattern());
			matcher = new PathMatcher(pattern, path);
			if (matcher.doMatch() && compareMethod(ai)) {
				result.setSuccess(true);
				result.setAction(ai);
				result.setUriPatternVariable(matcher.getVariable());
				break;
			}
		}
		return result;
	}

	/**
	 * 比较请求方式
	 * 
	 * @param ai
	 * @return
	 */
	private boolean compareMethod(ActionInfo ai) {
		return (ai.isGet() && ai.getController().isGet() && routeInfo.isGet())
				|| (ai.isPost() && ai.getController().isPost() && routeInfo
						.isPost());
	}
}
