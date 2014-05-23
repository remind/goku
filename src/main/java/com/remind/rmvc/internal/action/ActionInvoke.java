package com.remind.rmvc.internal.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.remind.rmvc.interceptor.ActionInterceptor;

/**
 * 执行action
 * @author remind
 *
 */
public class ActionInvoke {

	/**
	 * 执行action以及其拦截器
	 * @param action
	 * @param variable
	 * @return
	 */
	public ActionResult invoke(ActionInfo action, Map<String, String> variable) {
		try {
			ActionInterceptor[] interceptors = (ActionInterceptor[]) ArrayUtils.addAll(action.getController().getInterceptor(), action.getInterceptor());
			int len = interceptors.length;
			for (int i = 0; i < len; i++) {
				if (!interceptors[i].PreHandle(action)) {
					return null;
				}
			}
			
			ActionResult result = (ActionResult) action.getMethod().invoke(
					action.getController().getInstance(),
					variable.values().toArray());
			
			for (int i = 1; i <= len; i++) {
				result = interceptors[len - i].PostHandle(result);
			}
			return result;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
