package org.remind.goku.view.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.config.XmlFactoryConfiguration;
import org.remind.goku.GlobalConfig;
import org.remind.goku.context.HttpContext;
import org.remind.goku.internal.action.ActionResult;
import org.remind.goku.view.View;

/**
 * velocity视图
 * @author remind
 *
 */
public class VelocityView implements View {

	private static RuntimeInstance rtInstance;
	private String path;
	private final String suffix = ".html";
	private static final String TOOL_BOX_CONF_FILE = "toolbox.xml";
	private static ToolContext toolContext;

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
		initToolContext();
	}

	private static void initToolContext() {
		if (toolContext == null) {
			XmlFactoryConfiguration factoryConfiguration = new XmlFactoryConfiguration("Default Tools");
			try {
				factoryConfiguration.read(new FileInputStream(new File(Thread.currentThread().getContextClassLoader().getResource("/").getFile(), TOOL_BOX_CONF_FILE)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			ToolboxFactory factory = factoryConfiguration.createFactory();
			factory.configure(factoryConfiguration);
			toolContext = new ToolContext();
			for (String scope : Scope.values()) {
				toolContext.addToolbox(factory.createToolbox(scope));
			}
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
		Context context = new VelocityContext(actionResult.getModel().getAll(), toolContext);
		VelocityWriter vw = null;
		try {
			vw = new VelocityWriter(response.getWriter());
			template.merge(context, vw);
			vw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (vw != null)
				vw.recycle(null);
		}
	}

}
