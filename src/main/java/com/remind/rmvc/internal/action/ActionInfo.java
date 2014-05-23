package com.remind.rmvc.internal.action;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;
import com.remind.rmvc.interceptor.ActionInterceptor;

/**
 * 保存action的信息
 * 每一个请求所对应的controller中的方法为一个action
 * @author remind
 *
 */
public class ActionInfo {
	
	/**
	 * 方法上面匹配url的字符串，对应为注解path中的值
	 */
	private String uriPattern;
	
	/**
	 * 是否为post
	 */
	private boolean post;
	
	/**
	 * 是否为Get
	 */
	private boolean get;
	
	/**
	 * 所对应的方法
	 */
	private Method method;
	
	/**
	 * 方法里面的参数名及其类型
	 */
	private Map<String, Class<?>> param = Maps.newHashMap();
	
	/**
	 * action对应的controller
	 */
	private ControllerInfo controller;
	
	/**
	 * 方法级别的拦截器
	 */
	private ActionInterceptor[] interceptor;
	
	public ActionInfo(ControllerInfo controller)  {
		this.controller = controller;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Map<String, Class<?>> getParam() {
		return param;
	}

	public void setParam(Map<String, Class<?>> param) {
		this.param = param;
	}
	
	/**
	 * 在方法上配置的路径
	 * @return
	 */
	public String getUriPattern() {
		return uriPattern;
	}

	public void setUriPattern(String uriPattern) {
		this.uriPattern = uriPattern;
	}

	public void setMethodPost(boolean methodPost) {
		this.post = methodPost;
	}

	public void setMethodGet(boolean methodGet) {
		this.get = methodGet;
	}

	/**
	 * action对应的controller
	 * @return
	 */
	public ControllerInfo getController() {
		return controller;
	}

	public void setController(ControllerInfo controller) {
		this.controller = controller;
	}
	
	/**
	 * 获取方法级别的拦截器
	 * @return
	 */
	public ActionInterceptor[] getInterceptor() {
		return interceptor;
	}
	
	/**
	 * 设置方法级别的拦截器
	 * @return
	 */
	public void setInterceptor(ActionInterceptor[] interceptor) {
		this.interceptor = interceptor;
	}

	/**
	 * action中方法级别是否接收get
	 * @return
	 */
	public boolean isGet() {
		return get;
	}
	
	/**
	 * action中方法级别是否接收post
	 * @return
	 */
	public boolean isPost() {
		return post;
	}

	public boolean equals(ActionInfo target) {
		return this.hashCode() == target.hashCode();
	}
	
	@Override
	public String toString() {
		return controller.getUriPattern() + uriPattern +  ":" + controller.getClazz().getName() + "." + method.getName();
	}
}
