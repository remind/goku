package com.remind.rmvc.model;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 保存action的信息
 * 每一个请求所对应的controller中的方法为一个action
 * @author remind
 *
 */
public class ActionInfo {

	/**
	 * 类上面的匹配串
	 */
	private String classPathPattern;
	
	/**
	 * 方法上面匹配url的字符串，对应为注解path中的值
	 */
	private String methodPathPattern;
	
	/**
	 * 是否为post
	 */
	private boolean isPost;
	
	/**
	 * 是否为Get
	 */
	private boolean isGet;
	
	/**
	 * controller的class
	 */
	private Class<?> cls;
	
	/**
	 * 所对应的方法
	 */
	private Method method;
	
	/**
	 * 方法里面的参数名及其类型
	 */
	private Map<String, Class<?>> param = Maps.newHashMap();

	public String getClassPathPattern() {
		return classPathPattern;
	}

	public void setClassPathPattern(String classPathPattern) {
		this.classPathPattern = classPathPattern;
	}

	public String getMethodPathPattern() {
		return methodPathPattern;
	}

	public void setMethodPathPattern(String methodPathPattern) {
		this.methodPathPattern = methodPathPattern;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public boolean isGet() {
		return isGet;
	}

	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
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
	 * 计算出hashCode，防止重复，一个链接加上请求方式应该是唯一定位到一个action
	 * 当前算法并不可靠
	 */
	@Override
	public int hashCode() {
		int h = classPathPattern.hashCode() + methodPathPattern.hashCode();
		if (isPost) {
			h += 1;
		}
		if (isGet) {
			h += 2;
		}
		return h;
	}
	
	@Override
	public String toString() {
		String s = "\n";
		s += "ClassPathPattern:" + this.classPathPattern + '\n';
		s += "MethodPathPattern:" + this.methodPathPattern + '\n';
		s += "Controller:" + this.cls + '\n';
		s += "Method:" + this.method + '\n';
		s += "Method Param:" + this.param.toString() + '\n';
		s += "POST:" + this.isPost + '\n';
		s += "GET:" + this.isGet + '\n';
		return s;
	}
	
	public boolean equals(ActionInfo target) {
		return this.hashCode() == target.hashCode();
	}
	
}
