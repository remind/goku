package com.remind.rmvc.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remind.rmvc.internal.PathMatcher;

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
	private static ThreadLocal<HttpContext> httpContextHolder =  new ThreadLocal<HttpContext>();

	private HttpContext() {

	}

	/**
	 * 返回当前线程中的http上下文
	 * 
	 * @return
	 */
	public static HttpContext getCurrent() {
		HttpContext httpContext = httpContextHolder.get();
		if (httpContext == null) {
			httpContext = new HttpContext();
			httpContextHolder.set(httpContext);
		}
		return httpContextHolder.get();
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
	
	/**
	 * 返回用于controller匹配的路径
	 * 
	 * @return
	 */
	public String getMatchPath() {
		String path = request.getServletPath();
		path = PathMatcher.handle(path);
		return path;
	}

}
