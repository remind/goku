package com.remind.rmvc.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理每次请求的上下文信息
 * @author remind
 *
 */
public class ActionContext {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ActionContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
