package com.remind.rmvc.route;

import java.util.Map;

import com.remind.rmvc.internal.ActionInfo;

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
	private Map<String, String> variable;

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

	public Map<String, String> getVariable() {
		return variable;
	}

	public void setVariable(Map<String, String> variable) {
		this.variable = variable;
	}
}
