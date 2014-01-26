package com.remind.rmvc;

import java.util.Set;

import com.google.common.collect.Sets;
import com.remind.rmvc.internal.ScanAction;
import com.remind.rmvc.model.ActionInfo;

/**
 * 主要用来进行启动时的一些初始化
 * @author remind
 *
 */
public class Application {

	private static Set<ActionInfo> allAction = Sets.newConcurrentHashSet();
	private static boolean isInit = false;
	private static Application application = new Application();
	
	public static void start() { 
		if (isInit) {
			return;
		}
		application.load();
		isInit = true;
	}
	
	public static void destroy() {
		
	}

	public static Set<ActionInfo> getAllAction() {
		return allAction;
	}
	
	private void load() {
		scanAction();
	}
	
	/**
	 * 扫描出所有的action
	 */
	private void scanAction() {
		ScanAction scanAction = new ScanAction();
		allAction.clear();
		allAction = scanAction.getActionByPackage(GlobalConfig.getControllerPattern());
	}
	
}
