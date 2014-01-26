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

import com.remind.rmvc.context.ActionContext;

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

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Bootstrap.init();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ActionContext actionContext = new ActionContext((HttpServletRequest)request, (HttpServletResponse)response);
		GlobalFactory.getRoute().route(actionContext);
	}

	@Override
	public void destroy() {
		Bootstrap.destroy();
	}

}
