package org.remind.goku;

import java.io.File;

import org.remind.goku.route.Router;
import org.remind.goku.route.impl.DefaultRouter;
import org.remind.goku.view.View;
import org.remind.goku.view.impl.TextView;
import org.remind.goku.view.impl.VelocityView;

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
