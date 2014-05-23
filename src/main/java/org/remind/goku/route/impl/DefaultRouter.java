package org.remind.goku.route.impl;

import org.remind.goku.Goku;
import org.remind.goku.internal.PathMatcher;
import org.remind.goku.internal.action.ActionInfo;
import org.remind.goku.internal.action.ControllerInfo;
import org.remind.goku.route.RouteInfo;
import org.remind.goku.route.RouteResult;
import org.remind.goku.route.Router;

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
		for (ActionInfo ai : Goku.getAllAction()) {
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
