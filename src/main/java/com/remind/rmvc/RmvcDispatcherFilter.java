package com.remind.rmvc;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.remind.rmvc.context.HttpContext;

/**
 * 利用Filter来拦截
 * @author remind
 *
 */
@WebFilter(urlPatterns = {"/*"},
        dispatcherTypes = {DispatcherType.REQUEST},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")}
)
public class RmvcDispatcherFilter implements Filter {

	private static Logger logger = Logger.getLogger(RmvcDispatcherFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
		Application.start();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpContext httpContext = HttpContext.getCurrent();
		httpContext.setRequest((HttpServletRequest)request);
		httpContext.setResponse((HttpServletResponse)response);
		GlobalFactory.getRoute().route(httpContext);
	}

	@Override
	public void destroy() {
		Application.destroy();
	}

}
