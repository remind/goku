package com.remind.rmvc.internal;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.Sets;
import com.remind.rmvc.annotations.GET;
import com.remind.rmvc.annotations.POST;
import com.remind.rmvc.annotations.Path;
import com.remind.rmvc.model.ActionInfo;
import com.remind.rmvc.utils.ClassUtil;
import com.remind.rmvc.utils.ClassUtil.ClassFilter;

/**
 * 扫描所有Action
 * @author remind
 *
 */
public class ScanAction {
	
	private static Logger logger = Logger.getLogger(ScanAction.class);
	/**
	 * 扫描所有controller，并返回
	 * @param scanPackName	要扫描的包路径
	 * @return
	 */
	public Set<ActionInfo> getActionByPackage(String scanPackName) {
		Set<ActionInfo> actions = Sets.newHashSet();
		Set<Class<?>> classes = ClassUtil.getClasses(scanPackName, new ClassFilter() {
			@Override
			public boolean accept(Class<?> cls) {
				if (cls.getName().endsWith("Controller")) {
					return true;
				}
				return false;
			}
		});
		for (Class<?> cls : classes) {
			actions.addAll(getActionByClass(cls));
		}
		return actions;
	}
	
	/**
	 * 根据controller类返回其所有action
	 * @param cls
	 * @return
	 */
	public Set<ActionInfo> getActionByClass(Class<?> cls) {
		Set<ActionInfo> action = new HashSet<ActionInfo>();
		for(Method m : cls.getMethods()) {
			if (m.getAnnotation(Path.class) != null) {
				ActionInfo actionInfo = new ActionInfo();
				if (cls.getAnnotation(Path.class) != null) {
					actionInfo.setClassPathPattern(cls.getAnnotation(Path.class).value());
				} else {
					actionInfo.setClassPathPattern("");
				}
				try {
					actionInfo.setControllerClass(cls.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				actionInfo.setCls(cls);
				actionInfo.setMethod(m);
				actionInfo.setParam(ClassUtil.getMethodParam(cls, m));
				actionInfo.setMethodPathPattern(m.getAnnotation(Path.class).value());
				actionInfo.setPost(isPost(cls, m));
				actionInfo.setGet(isGet(cls, m));
				action.add(actionInfo);
				logger.info("controller - " + actionInfo.toString());
			}
		}
		return action;
	}
	
	/**
	 * 是否为post
	 * @param cls
	 * @param method
	 * @return
	 */
	private boolean isPost(Class<?> cls, Method method) {
		boolean result = true;
		if (cls.getAnnotation(POST.class) == null && cls.getAnnotation(GET.class) != null) {
			result = false;
		}
		if (method.getAnnotation(POST.class) != null) {
			result = true;
		} else if (method.getAnnotation(POST.class) == null && method.getAnnotation(GET.class) != null) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 是否为get
	 * @param cls
	 * @param method
	 * @return
	 */
	private boolean isGet(Class<?> cls, Method method) {
		boolean result = true;
		if (cls.getAnnotation(GET.class) == null && cls.getAnnotation(POST.class) != null) {
			result = false;
		}
		if (method.getAnnotation(GET.class) != null) {
			result = true;
		} else if (method.getAnnotation(GET.class) == null && method.getAnnotation(POST.class) != null) {
			result = false;
		}
		return result;
	}
}
