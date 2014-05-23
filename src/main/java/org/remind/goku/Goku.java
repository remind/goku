package org.remind.goku;

import java.util.Set;

import org.apache.log4j.Logger;
import org.remind.goku.internal.ScanAction;
import org.remind.goku.internal.action.ActionInfo;

import com.google.common.collect.Sets;

/**
 * 主要用来进行启动时的一些初始化
 * @author remind
 *
 */
public class Goku {

	private static Set<ActionInfo> allAction = Sets.newConcurrentHashSet();
	private static boolean isInit = false;
	private static Goku application = new Goku();
	private static Logger logger = Logger.getLogger(Goku.class);
	
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
