package com.remind.rmvc.view.impl;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.runtime.RuntimeInstance;

import com.remind.rmvc.GlobalConfig;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.action.ActionResult;
import com.remind.rmvc.view.View;

public class VelocityView implements View {

	private static RuntimeInstance rtInstance;
	private String path;
	private final String suffix = ".vm";
	static {
		Properties ps = new Properties();
		ps.setProperty("resource.loader", "file");
		ps.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		ps.setProperty("file.resource.loader.path",
				GlobalConfig.getTemplatePath());
		ps.setProperty("file.resource.loader.cache", "false");
		ps.setProperty("file.resource.loader.modificationCheckInterval", "2");
		ps.setProperty("input.encoding", GlobalConfig.ENCODING);
		ps.setProperty("output.encoding", GlobalConfig.ENCODING);
		ps.setProperty("default.contentType", "text/html; charset="
				+ GlobalConfig.ENCODING);
		ps.setProperty("velocimarco.library.autoreload", "true");
		ps.setProperty("runtime.log.error.stacktrace", "false");
		ps.setProperty("runtime.log.warn.stacktrace", "false");
		ps.setProperty("runtime.log.info.stacktrace", "false");
		ps.setProperty("runtime.log.logsystem.class",
				"org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		ps.setProperty("runtime.log.logsystem.log4j.category", "velocity_log");

		rtInstance = new RuntimeInstance();

		try {
			rtInstance.init(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void render(ActionResult actionResult) {
		actionResult.getModel().add("httpContext", HttpContext.getCurrent());
		
		Template template = rtInstance.getTemplate(path + suffix);

		HttpServletResponse response = HttpContext.getCurrent().getResponse();
		response.setContentType("text/html;charset=\"" + GlobalConfig.ENCODING + "\"");
		response.setCharacterEncoding(GlobalConfig.ENCODING);
		Context context = new VelocityContext(actionResult.getModel().getAll());
		VelocityWriter vw = null;
		try {
			vw = new VelocityWriter(response.getWriter());
			template.merge(context, vw);
			vw.flush();
		} catch (IOException e) {

		} finally {
			if (vw != null)
				vw.recycle(null);
		}
	}

}
