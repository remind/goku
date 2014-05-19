package com.remind.rmvc.view.impl;

import java.io.IOException;
import java.io.Writer;

import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.view.View;

/**
 * 直接输出文本到浏览器
 * @author remind
 *
 */
public class TextView implements View {

	private HttpContext httpContext = HttpContext.getCurrent();
	
	private String content;
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setHeader(String name, String value) {
		httpContext.getResponse().setHeader(name, value);
	}
	
	@Override
	public void render(ActionResult actionResult) {
		try {
			Writer writer = httpContext.getResponse().getWriter();
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
