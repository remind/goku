package org.remind.goku.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.remind.goku.internal.PathMatcher;
import org.remind.goku.internal.action.ActionResult;

/**
 * 管理每次请求的上下文信息
 * 决定使用threadLocal变量来保存每次的上下文，在进入filter的时候初始化，每次需要使用的时候都通过调用getCurrent()来获取
 * 
 * @author remind
 * 
 */
public class HttpContext {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ActionResult actionResult;
	private Map<String, Object> contextMap = new HashMap<String, Object>();

	/**
	 * 返回当前线程中的http上下文
	 * 
	 * @return
	 */
	public static HttpContext getCurrent() {
		return (HttpContext) ThreadLocalContext.get().getSingleThreadLocalVar(HttpContext.class);
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
	
	public ActionResult getActionResult() {
		return actionResult;
	}

	public void setActionResult(ActionResult actionResult) {
		this.actionResult = actionResult;
	}

	/**
	 * 返回用于controller匹配的路径
	 * 
	 * @return
	 */
	public String getMatchPath() {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		path = PathMatcher.filter(path);
		return path;
	}
	
	public String getBasePath() {
		String basePath = request.getScheme()+"://"+request.getServerName();
		if (request.getServerPort() != 80) {
			basePath += ":" + request.getServerPort();
		}
		basePath += request.getContextPath();
		return basePath;
	}
	
	public Map<String, Object> getContextMap() {
		return contextMap;
	}
}
