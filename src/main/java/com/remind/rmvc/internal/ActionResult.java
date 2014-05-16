package com.remind.rmvc.internal;

import com.remind.rmvc.view.View;

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
	 * 本次请求所带的参数 
	 */
	private ModelMap requestMap = new ModelMap();
	
	/**
	 * 最终要渲染的view
	 */
	private View view;
	
	public ModelMap getModel() {
		return model;
	}

	public void setModel(ModelMap model) {
		this.model = model;
	}
	
	public ModelMap getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(ModelMap requestMap) {
		this.requestMap = requestMap;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void render() {
		view.render(this);
	}
}
