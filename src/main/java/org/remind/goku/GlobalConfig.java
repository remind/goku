package org.remind.goku;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.remind.goku.route.Router;
import org.remind.goku.route.impl.DefaultRouter;

/**
 * 全局配置信息
 * @author remind
 *
 */
public class GlobalConfig {
	
	public static final String ENCODING = "UTF-8";
	private static final String PRO_FILE = "goku.properties";
	private static Properties configPro = new Properties();
	
	public static void init() {
		try {
			configPro.load(new FileInputStream(new File(Thread.currentThread().getContextClassLoader().getResource("/").getFile(), PRO_FILE)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据key返回配置
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		return configPro.getProperty(key).trim();
	}
	
	public static Properties getConfig() {
		return configPro;
	}
	
	/**
	 * controller的包路径
	 * @return
	 */
	public static String getBasePackage() {
		return configPro.getProperty("controller.base.package");
	}
	
	/**
	 * 返回模板路径
	 * @return
	 */
	public static String getTemplatePath() {
		return new File(Thread.currentThread().getContextClassLoader().getResource("/").getFile(), configPro.getProperty("views.path")).getAbsolutePath();
	}
	
	/**
	 * 返回路由
	 * @return
	 */
	public static Router getMvcRoute() {
		return new DefaultRouter();
	}
}
