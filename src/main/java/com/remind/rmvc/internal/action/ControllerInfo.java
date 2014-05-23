package com.remind.rmvc.internal.action;

import com.remind.rmvc.interceptor.ActionInterceptor;

/**
 * controller的信息
 * @author remind
 *
 */
public class ControllerInfo {

	/**
	 * 路径匹配模式串
	 */
	private String uriPattern;
	
	/**
	 * 是否接收post
	 */
	private boolean post;
	
	/**
	 * 是否接收get
	 */
	private boolean get;
	
	/**
	 * controller对应的class
	 */
	private Class<?> clazz;
	
	/**
	 * controller类的实例
	 */
	private Object instance;
	
	/**
	 * 类级别的拦截器
	 */
	private ActionInterceptor[] interceptor;

	public String getUriPattern() {
		return uriPattern;
	}

	public void setUriPattern(String uriPattern) {
		this.uriPattern = uriPattern;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}

	public boolean isGet() {
		return get;
	}

	public void setGet(boolean get) {
		this.get = get;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 返回类级别的拦截器
	 * @return
	 */
	public ActionInterceptor[] getInterceptor() {
		return interceptor;
	}

	/**
	 * 设置类级别的拦截器
	 * @param interceptor
	 */
	public void setInterceptor(ActionInterceptor[] interceptor) {
		this.interceptor = interceptor;
	}
}
