package org.remind.goku.route;

import java.util.Map;

import org.remind.goku.internal.action.ActionInfo;

/**
 * 路由结果
 * @author remind
 *
 */
public class RouteResult {

	/**
	 * 匹配结果，是否成功
	 */
	private boolean success;
	
	/**
	 * 所匹配上的action
	 */
	private ActionInfo action;
	
	/**
	 * 所匹配的参数变量
	 */
	private Map<String, String> uriPatternVariable;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ActionInfo getAction() {
		return action;
	}

	public void setAction(ActionInfo action) {
		this.action = action;
	}

	public Map<String, String> getUriPatternVariable() {
		return uriPatternVariable;
	}

	public void setUriPatternVariable(Map<String, String> uriPatternVariable) {
		this.uriPatternVariable = uriPatternVariable;
	}
}
