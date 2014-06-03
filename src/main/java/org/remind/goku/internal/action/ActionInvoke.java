package org.remind.goku.internal.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.remind.goku.interceptor.ActionInterceptor;
import org.remind.goku.utils.MethodParamInfo;
import org.remind.goku.utils.converter.ConverterFactory;

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
	public ActionResult invoke(ActionInfo action, Map<String, Object> variable) {
		try {
			ActionInterceptor[] interceptors = (ActionInterceptor[]) ArrayUtils.addAll(action.getController().getInterceptor(), action.getInterceptor());
			int len = interceptors.length;
			for (int i = 0; i < len; i++) {
				if (!interceptors[i].PreHandle(action)) {
					return null;
				}
			}
			Object[] param = new Object[action.getParamInfo().size()];
			String temp;
			for (int i = 0; i < action.getParamInfo().size(); i++) {
				MethodParamInfo paramInfo = action.getParamInfo().get(i);
				if (variable.containsKey(paramInfo.getName())) {
					temp = variable.get(paramInfo.getName()).toString();
				} else if (paramInfo.getDefaultValue() != null) {
					temp = paramInfo.getDefaultValue();
				} else {
					temp = null;
				}
				param[i] = ConverterFactory.getConverter(paramInfo.getType()).convert(temp);
			}
			ActionResult result = (ActionResult) action.getMethod().invoke(
					action.getController().getInstance(), param);
			
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
