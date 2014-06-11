package org.remind.goku.view.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.remind.goku.GlobalConfig;
import org.remind.goku.context.HttpContext;
import org.remind.goku.exception.ExceptionBuilder;
import org.remind.goku.internal.action.ActionResult;
import org.remind.goku.view.View;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * json视图
 * @author remind
 *
 */
public class JsonView implements View{

	private HttpContext httpContext = HttpContext.getCurrent();
	private Map<String, String> headerMap = Maps.newConcurrentMap(); //默认部份，可以覆盖
	
	private Object object;
	
	public JsonView(Object object) {
		this.object = object;
		headerMap.put("content-type", "text/json; charset=" + GlobalConfig.ENCODING);
	}
	
	public Map<String, String> getHeaderMap() {
		return this.headerMap;
	}
	
	@Override
	public void render(ActionResult actionResult) {
		try {
			for (String name : this.headerMap.keySet()) {
				httpContext.getResponse().setHeader(name, this.headerMap.get(name));
			}
			httpContext.getResponse().setCharacterEncoding(GlobalConfig.ENCODING);
			Writer writer = httpContext.getResponse().getWriter();
			writer.write(JSON.toJSONString(object));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			ExceptionBuilder.build("输出json出错", e);
		}
	}

}
