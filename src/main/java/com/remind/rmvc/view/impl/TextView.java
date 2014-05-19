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
	
	@Override
	public void render(ActionResult actionResult) {
		try {
			for (String name : actionResult.getHeader().keySet()) {
				httpContext.getResponse().setHeader(name, actionResult.getHeader().get(name));
			}
			Writer writer = httpContext.getResponse().getWriter();
			writer.write(actionResult.getModel().get("content").toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
