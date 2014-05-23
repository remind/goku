package org.remind.goku;

import java.io.File;

import org.remind.goku.route.Router;
import org.remind.goku.route.impl.DefaultRouter;

/**
 * 全局配置信息
 * @author remind
 *
 */
public class GlobalConfig {
	
	public static final String ENCODING = "UTF-8";
	
	/**
	 * controller的包路径
	 * @return
	 */
	public static String getControllerPattern() {
		return "org.remind";
	}
	
	/**
	 * 返回模板路径
	 * @return
	 */
	public static String getTemplatePath() {
		return new File(Thread.currentThread().getContextClassLoader().getResource("/").getFile(), "views").getAbsolutePath();
	}
	
	/**
	 * 返回路由
	 * @return
	 */
	public static Router getMvcRoute() {
		return new DefaultRouter();
	}
}
