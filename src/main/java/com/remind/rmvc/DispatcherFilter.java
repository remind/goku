package com.remind.rmvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.ActionResult;

/**
 * 利用Filter来拦截
 * @author remind
 *
 */
public class DispatcherFilter implements Filter {

	private static Logger logger = Logger.getLogger(DispatcherFilter.class);
	
	private String encoding = "utf-8";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
		if (!Strings.isNullOrEmpty(filterConfig.getInitParameter("encoding"))) {
			encoding = filterConfig.getInitParameter("encoding");
		}
		Application.start();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpContext httpContext = HttpContext.getCurrent();
		httpContext.setRequest((HttpServletRequest)request);
		httpContext.setResponse((HttpServletResponse)response);
		httpContext.getRequest().setCharacterEncoding(encoding);
		ActionResult actionResult = GlobalFactory.getRoute().route();
		if (actionResult != null) {
			actionResult.render();
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		Application.destroy();
	}

}
