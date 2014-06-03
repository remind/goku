package org.remind.goku;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.remind.goku.db.sql.SqlConnection;
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
		initDataSource();
	}
	
	/**
	 * 扫描出所有的action
	 */
	private void scanAction() {
		logger.info("扫描action");
		ScanAction scanAction = new ScanAction();
		allAction.clear();
		allAction = scanAction.getActionByPackage(GlobalConfig.getBasePackage());
	}
	
	/**
	 * 初始化数据源
	 */
	private void initDataSource() {
		Properties pro = new Properties();
		Properties configPro = GlobalConfig.getConfig();

		Enumeration<Object> enu = configPro.keys();
		String prefix = "datasource.";
		while (enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			if (key.startsWith(prefix)) {
				pro.setProperty(key.substring(prefix.length()), configPro.getProperty(key).trim());
			}
		}
		SqlConnection.initDataSource(pro);
		logger.info("初始化数据源成功");
	}
	
}
