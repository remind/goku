package com.remind.rmvc;

import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.Sets;
import com.remind.rmvc.internal.ScanAction;
import com.remind.rmvc.internal.action.ActionInfo;

/**
 * 主要用来进行启动时的一些初始化
 * @author remind
 *
 */
public class Application {

	private static Set<ActionInfo> allAction = Sets.newConcurrentHashSet();
	private static boolean isInit = false;
	private static Application application = new Application();
	private static Logger logger = Logger.getLogger(Application.class);
	
	/**
	 * 启动应用
	 * 作一些初始化
	 */
	public static void start() { 
		if (isInit) {
			return;
		}
		logger.info("application start");
		application.load();
		isInit = true;
	}

	/**
	 * 返回所有action
	 * @return
	 */
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
		logger.info("扫描action");
		ScanAction scanAction = new ScanAction();
		allAction.clear();
		allAction = scanAction.getActionByPackage(GlobalConfig.getControllerPattern());
	}
	
}
