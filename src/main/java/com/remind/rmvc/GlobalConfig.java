package com.remind.rmvc;

import java.awt.FileDialog;
import java.io.File;

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
	
	public static View getView(String name) {
		if (name.equals("text")) {
			return new TextView();
		} else if(name.equals("velocity")) {
			return new VelocityView();
		}
		return null;
	}
}
