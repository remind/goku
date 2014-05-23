package org.remind.goku.internal.action;

import java.util.HashMap;
import java.util.Map;

import org.remind.goku.internal.ModelMap;
import org.remind.goku.view.View;

/**
 * 用于包装从controller中直接返回的结果
 * @author remind
 *
 */
public class ActionResult {

	/**
	 * contorller要交给view的数据
	 */
	private ModelMap model = new ModelMap();
	
	/**
	 * 最终要渲染的view
	 */
	private View view;
	
	/**
	 * header
	 */
	private Map<String, String> header = new HashMap<String, String>();
	
	/**
	 * 要传递给view的数据
	 * @return
	 */
	public ModelMap getModel() {
		return model;
	}

	/**
	 * 设置view
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * 添加header部分
	 * @param name	名称
	 * @param value	值
	 */
	public void addHeader(String name, String value) {
		header.put(name, value);
	}
	
	/**
	 * 返回header
	 * @return
	 */
	public Map<String, String> getHeader() {
		return this.header;
	}

	/**
	 * 渲染结果
	 */
	public void render() {
		view.render(this);
	}
}
