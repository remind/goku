package org.remind.goku.internal;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.remind.goku.BaseController;
import org.remind.goku.annotations.Get;
import org.remind.goku.annotations.Interceptor;
import org.remind.goku.annotations.Path;
import org.remind.goku.annotations.Post;
import org.remind.goku.exception.ExceptionBuilder;
import org.remind.goku.interceptor.ActionInterceptor;
import org.remind.goku.internal.action.ActionInfo;
import org.remind.goku.internal.action.ControllerInfo;
import org.remind.goku.utils.ClassUtil;
import org.remind.goku.utils.ClassUtil.ClassFilter;

import com.google.common.collect.Sets;

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
	 * @throws Exception 
	 */
	public Set<ActionInfo> getActionByPackage(String scanPackName) {
		Set<ActionInfo> actions = Sets.newHashSet();
		Set<Class<?>> classes = ClassUtil.getClasses(scanPackName, new ClassFilter() {
			@Override
			public boolean accept(Class<?> cls) {
				if (cls.getName().endsWith("Controller") && BaseController.class.isAssignableFrom(cls)
						&& !cls.getName().endsWith(BaseController.class.getName())) { 
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
	 * @throws Exception 
	 */
	public Set<ActionInfo> getActionByClass(Class<?> cls) {
		Set<ActionInfo> actions = Sets.newHashSet();
		ControllerInfo controller = getController(cls);
		
		for(Method m : cls.getMethods()) {
			if (m.getAnnotation(Path.class) != null) {
				ActionInfo actionInfo = getAction(controller, cls, m);
				actions.add(actionInfo);
				logger.info("controller - " + actionInfo.toString());
			}
		}
		return actions;
	}
	
	/**
	 * 获得action
	 * @param controller
	 * @param cls
	 * @param m
	 * @return
	 */
	private ActionInfo getAction(ControllerInfo controller, Class<?> cls, Method m) {
		ActionInfo actionInfo = new ActionInfo(controller);
		
		if (cls.getAnnotation(Path.class) != null) {
			actionInfo.setUriPattern(m.getAnnotation(Path.class).value());
		} else {
			actionInfo.setUriPattern("");
		}
		
		if (m.getAnnotation(Interceptor.class) != null) {
			Class<? extends ActionInterceptor>[] interceptorMethod = m.getAnnotation(Interceptor.class).value();
			ActionInterceptor[] interceptorInstance = new ActionInterceptor[interceptorMethod.length];
			for (int i = 0; i < interceptorMethod.length; i++) {
				try {
					interceptorInstance[i] = interceptorMethod[i].newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					interceptorInstance[i] = null;
				}
			}
			actionInfo.setInterceptor(interceptorInstance);
		} else {
			actionInfo.setInterceptor(new ActionInterceptor[0]);
		}
		
		actionInfo.setMethod(m);
		actionInfo.setParam(ClassUtil.getMethodParam(cls, m));
		actionInfo.setMethodPost(methodIsPost(m));
		actionInfo.setMethodGet(methodIsGet(m));
		return actionInfo;
	}
	
	/**
	 * 获得controller
	 * @param cls
	 * @return
	 */
	private ControllerInfo getController(Class<?> cls) {
		ControllerInfo controller = new ControllerInfo();
		if (cls.getAnnotation(Path.class) != null) {
			controller.setUriPattern(cls.getAnnotation(Path.class).value());
		} else {
			controller.setUriPattern("");
		}
		if (cls.getAnnotation(Interceptor.class) != null) {
			Class<? extends ActionInterceptor>[] interceptorClzz = cls.getAnnotation(Interceptor.class).value();
			ActionInterceptor[] interceptorInstance = new ActionInterceptor[interceptorClzz.length];
			for (int i = 0; i < interceptorClzz.length; i++) {
				try {
					interceptorInstance[i] = interceptorClzz[i].newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw ExceptionBuilder.build("获取contoller出错" + controller.getClass().getName() , e);
				}
			}
			controller.setInterceptor(interceptorInstance);
		} else {
			controller.setInterceptor(new ActionInterceptor[0]);
		}
		controller.setClazz(cls);
		try {
			controller.setInstance(cls.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw ExceptionBuilder.build("获取controller出错:" + controller.getClass().getName(), e);
		}
		controller.setPost(controllerIsPost(cls));
		controller.setGet(controllerIsGet(cls));
		return controller;
	}
	
	private boolean controllerIsPost(Class<?> cls) {
		return cls.getAnnotation(Post.class) != null || cls.getAnnotation(Get.class) == null;
	}
	
	private boolean controllerIsGet(Class<?> cls) {
		return cls.getAnnotation(Get.class) != null || cls.getAnnotation(Post.class) == null;
	}
	
	private boolean methodIsPost(Method method) {
		return method.getAnnotation(Post.class) != null || method.getAnnotation(Get.class) == null;
	}
	
	private boolean methodIsGet(Method method) {
		return method.getAnnotation(Get.class) != null || method.getAnnotation(Post.class) == null;
	}
}
