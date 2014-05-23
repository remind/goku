package com.remind.rmvc;

import java.io.File;

import com.remind.rmvc.route.Router;
import com.remind.rmvc.route.impl.DefaultRouter;
import com.remind.rmvc.view.View;
import com.remind.rmvc.view.impl.TextView;
import com.remind.rmvc.view.impl.VelocityView;

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
		return "com.remind";
	}
	
	/**
	 * 返回模板路径
	 * @return
	 */
	public static String getTemplatePath() {
		return new File(Thread.currentThread().getContextClassLoader().getResource("/").getFile(), "views").getAbsolutePath();
	}
	
	/**
	 * 返回mvc url 路由
	 * @return
	 */
	public static Router getMvcRoute() {
		return new DefaultRouter();
	}
}
